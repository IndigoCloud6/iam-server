package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.CreateDictRequest;
import com.xudis.iam.dto.UpdateDictRequest;
import com.xudis.iam.entity.Dict;
import com.xudis.iam.mapper.DictMapper;
import com.xudis.iam.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 数据字典服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public Dict getByDictCode(String dictCode) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getDictCode, dictCode);
        return this.getOne(wrapper);
    }

    @Override
    public Page<Dict> pageDicts(Page<Dict> page, String dictCode, String dictName) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dictCode)) {
            wrapper.like(Dict::getDictCode, dictCode);
        }
        if (StringUtils.hasText(dictName)) {
            wrapper.like(Dict::getDictName, dictName);
        }
        wrapper.orderByDesc(Dict::getCreatedAt);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dict createDict(CreateDictRequest request) {
        // 检查字典编码是否已存在
        Dict existingDict = getByDictCode(request.getDictCode());
        if (existingDict != null) {
            throw new BusinessException("字典编码已存在: " + request.getDictCode());
        }

        // 创建字典实体
        Dict dict = new Dict();
        BeanUtil.copyProperties(request, dict);

        // 设置默认值
        if (dict.getDictType() == null) {
            dict.setDictType(2); // 业务字典
        }
        if (dict.getStatus() == null) {
            dict.setStatus(1); // 启用
        }

        // 保存字典
        boolean saved = this.save(dict);
        if (!saved) {
            throw new BusinessException("创建字典失败");
        }

        log.info("创建字典成功: dictCode={}, dictName={}", dict.getDictCode(), dict.getDictName());

        return dict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDict(UpdateDictRequest request) {
        // 检查字典是否存在
        Dict existingDict = this.getById(request.getId());
        if (existingDict == null) {
            throw new BusinessException("字典不存在: " + request.getId());
        }

        // 如果修改了字典编码，检查新编码是否已被使用
        if (StringUtils.hasText(request.getDictCode()) &&
            !request.getDictCode().equals(existingDict.getDictCode())) {
            Dict dictWithSameCode = getByDictCode(request.getDictCode());
            if (dictWithSameCode != null) {
                throw new BusinessException("字典编码已被使用: " + request.getDictCode());
            }
        }

        // 复制属性
        Dict dict = new Dict();
        BeanUtil.copyProperties(request, dict);

        // 更新字典
        boolean updated = this.updateById(dict);

        if (updated) {
            log.info("更新字典成功: dictCode={}", dict.getDictCode());
        }

        return updated;
    }

    @Override
    public List<Dict> listDictsWithItems() {
        // 只返回字典列表，字典项在 Controller 层通过 DictItemService 查询并组装
        return this.list();
    }
}
