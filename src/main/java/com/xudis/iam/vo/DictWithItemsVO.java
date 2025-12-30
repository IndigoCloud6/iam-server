package com.xudis.iam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 字典及其字典项VO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "字典及其字典项")
public class DictWithItemsVO {

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型:1-系统字典,2-业务字典")
    private Integer dictType;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 状态
     */
    @Schema(description = "状态:1-启用,2-停用")
    private Integer status;

    /**
     * 字典项列表
     */
    @Schema(description = "字典项列表")
    private List<DictItemVO> items;

    /**
     * 字典项VO
     */
    @Data
    @Schema(description = "字典项")
    public static class DictItemVO {

        /**
         * 字典项ID
         */
        @Schema(description = "字典项ID")
        private Long id;

        /**
         * 字典项编码
         */
        @Schema(description = "字典项编码")
        private String itemCode;

        /**
         * 字典项名称
         */
        @Schema(description = "字典项名称")
        private String itemName;

        /**
         * 字典项值
         */
        @Schema(description = "字典项值")
        private String itemValue;

        /**
         * 父项ID
         */
        @Schema(description = "父项ID")
        private Long parentId;

        /**
         * 排序号
         */
        @Schema(description = "排序号")
        private Integer orderNum;

        /**
         * 状态
         */
        @Schema(description = "状态:1-启用,2-停用")
        private Integer status;
    }
}
