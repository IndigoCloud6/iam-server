package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.CreateDictItemRequest;
import com.xudis.iam.dto.UpdateDictItemRequest;
import com.xudis.iam.entity.DictItem;

import java.util.List;

/**
 * 字典项服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 根据字典ID查询字典项列表
     *
     * @param dictId 字典ID
     * @return 字典项列表
     */
    List<DictItem> getItemsByDictId(Long dictId);

    /**
     * 根据字典编码查询字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<DictItem> getItemsByDictCode(String dictCode);

    /**
     * 分页查询字典项
     *
     * @param page     分页对象
     * @param dictId   字典ID
     * @param itemCode 字典项编码
     * @param itemName 字典项名称
     * @return 分页结果
     */
    Page<DictItem> pageDictItems(Page<DictItem> page, Long dictId, String itemCode, String itemName);

    /**
     * 创建字典项
     *
     * @param request 创建字典项请求
     * @return 创建的字典项信息
     */
    DictItem createDictItem(CreateDictItemRequest request);

    /**
     * 更新字典项
     *
     * @param request 更新字典项请求
     * @return 是否更新成功
     */
    boolean updateDictItem(UpdateDictItemRequest request);
}
