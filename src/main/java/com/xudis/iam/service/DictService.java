package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.CreateDictRequest;
import com.xudis.iam.dto.UpdateDictRequest;
import com.xudis.iam.entity.Dict;

import java.util.List;

/**
 * 数据字典服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据字典编码查询字典
     *
     * @param dictCode 字典编码
     * @return 字典信息
     */
    Dict getByDictCode(String dictCode);

    /**
     * 分页查询字典
     *
     * @param page     分页对象
     * @param dictCode 字典编码
     * @param dictName 字典名称
     * @return 分页结果
     */
    Page<Dict> pageDicts(Page<Dict> page, String dictCode, String dictName);

    /**
     * 创建字典
     *
     * @param request 创建字典请求
     * @return 创建的字典信息
     */
    Dict createDict(CreateDictRequest request);

    /**
     * 更新字典
     *
     * @param request 更新字典请求
     * @return 是否更新成功
     */
    boolean updateDict(UpdateDictRequest request);

    /**
     * 查询所有字典列表（包含字典项）
     *
     * @return 字典列表
     */
    List<Dict> listDictsWithItems();
}
