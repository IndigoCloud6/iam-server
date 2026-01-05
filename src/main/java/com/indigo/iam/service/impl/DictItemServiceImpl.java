package com.indigo.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.indigo.iam.common.BusinessException;
import com.indigo.iam.dto.CreateDictItemRequest;
import com.indigo.iam.dto.UpdateDictItemRequest;
import com.indigo.iam.entity.Dict;
import com.indigo.iam.entity.DictItem;
import com.indigo.iam.mapper.DictItemMapper;
import com.indigo.iam.mapper.DictMapper;
import com.indigo.iam.service.DictItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 字典项服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    private final DictMapper dictMapper;

    @Override
    public List<DictItem> getItemsByDictId(Long dictId) {
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictItem::getDictId, dictId)
                .orderByAsc(DictItem::getOrderNum)
                .orderByAsc(DictItem::getId);
        return this.list(wrapper);
    }

    @Override
    public List<DictItem> getItemsByDictCode(String dictCode) {
        // 通过 dictCode 查询 dict
        LambdaQueryWrapper<Dict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(Dict::getDictCode, dictCode);
        Dict dict = dictMapper.selectOne(dictWrapper);

        if (dict == null) {
            return List.of();
        }

        return getItemsByDictId(dict.getId());
    }

    @Override
    public Page<DictItem> pageDictItems(Page<DictItem> page, Long dictId, String itemCode, String itemName) {
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        if (dictId != null) {
            wrapper.eq(DictItem::getDictId, dictId);
        }
        if (StringUtils.hasText(itemCode)) {
            wrapper.like(DictItem::getItemCode, itemCode);
        }
        if (StringUtils.hasText(itemName)) {
            wrapper.like(DictItem::getItemName, itemName);
        }
        wrapper.orderByAsc(DictItem::getOrderNum)
                .orderByAsc(DictItem::getId);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictItem createDictItem(CreateDictItemRequest request) {
        // 检查字典是否存在
        Dict dict = dictMapper.selectById(request.getDictId());
        if (dict == null) {
            throw new BusinessException("字典不存在: " + request.getDictId());
        }

        // 检查字典项编码是否已存在（同一字典下）
        LambdaQueryWrapper<DictItem> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(DictItem::getDictId, request.getDictId())
                .eq(DictItem::getItemCode, request.getItemCode());
        DictItem existingItem = this.getOne(checkWrapper);
        if (existingItem != null) {
            throw new BusinessException("字典项编码已存在: " + request.getItemCode());
        }

        // 创建字典项实体
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(request, dictItem);

        // 设置默认值
        if (dictItem.getParentId() == null) {
            dictItem.setParentId(0L);
        }
        if (dictItem.getOrderNum() == null) {
            dictItem.setOrderNum(0);
        }
        if (dictItem.getStatus() == null) {
            dictItem.setStatus(1); // 启用
        }

        // 计算祖级列表
        calculateAncestors(dictItem);

        // 保存字典项
        boolean saved = this.save(dictItem);
        if (!saved) {
            throw new BusinessException("创建字典项失败");
        }

        log.info("创建字典项成功: dictCode={}, itemCode={}", dict.getDictCode(), dictItem.getItemCode());

        return dictItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictItem(UpdateDictItemRequest request) {
        // 检查字典项是否存在
        DictItem existingItem = this.getById(request.getId());
        if (existingItem == null) {
            throw new BusinessException("字典项不存在: " + request.getId());
        }

        // 如果修改了编码，检查新编码是否已被使用（同一字典下）
        if (StringUtils.hasText(request.getItemCode()) &&
            !request.getItemCode().equals(existingItem.getItemCode())) {
            LambdaQueryWrapper<DictItem> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(DictItem::getDictId, existingItem.getDictId())
                    .eq(DictItem::getItemCode, request.getItemCode())
                    .ne(DictItem::getId, request.getId());
            DictItem itemWithSameCode = this.getOne(checkWrapper);
            if (itemWithSameCode != null) {
                throw new BusinessException("字典项编码已被使用: " + request.getItemCode());
            }
        }

        // 复制属性
        DictItem dictItem = new DictItem();
        BeanUtil.copyProperties(request, dictItem);

        // 如果修改了父级，重新计算祖级列表
        if (request.getParentId() != null && !request.getParentId().equals(existingItem.getParentId())) {
            calculateAncestors(dictItem);
        }

        // 更新字典项
        boolean updated = this.updateById(dictItem);

        if (updated) {
            log.info("更新字典项成功: itemCode={}", dictItem.getItemCode());
        }

        return updated;
    }

    /**
     * 计算祖级列表
     */
    private void calculateAncestors(DictItem dictItem) {
        Long parentId = dictItem.getParentId();
        if (parentId == null || parentId == 0) {
            // 根节点
            dictItem.setAncestors("0");
        } else {
            // 子节点
            DictItem parent = this.getById(parentId);
            if (parent == null) {
                throw new BusinessException("父字典项不存在: " + parentId);
            }

            // 计算祖级列表
            String ancestors = parent.getAncestors() + "," + parent.getId();
            dictItem.setAncestors(ancestors);
        }
    }
}
