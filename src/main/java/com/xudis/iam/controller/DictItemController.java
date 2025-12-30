package com.xudis.iam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.CreateDictItemRequest;
import com.xudis.iam.dto.UpdateDictItemRequest;
import com.xudis.iam.entity.DictItem;
import com.xudis.iam.service.DictItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 字典项控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/dict-item")
@RequiredArgsConstructor
@Tag(name = "字典项管理", description = "字典项相关接口")
public class DictItemController {

    private final DictItemService dictItemService;

    /**
     * 分页查询字典项列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询字典项列表")
    @LogOperation(module = "字典管理", operationType = "QUERY", description = "分页查询字典项列表")
    public Result<Page<DictItem>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Long dictId,
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) String itemName) {
        Page<DictItem> page = new Page<>(current, size);
        Page<DictItem> result = dictItemService.pageDictItems(page, dictId, itemCode, itemName);
        return Result.success(result);
    }

    /**
     * 根据字典ID查询字典项
     */
    @GetMapping("/dict/{dictId}")
    @Operation(summary = "根据字典ID查询字典项")
    @LogOperation(module = "字典管理", operationType = "QUERY", description = "根据字典ID查询字典项")
    public Result<java.util.List<DictItem>> getItemsByDictId(@PathVariable Long dictId) {
        java.util.List<DictItem> items = dictItemService.getItemsByDictId(dictId);
        return Result.success(items);
    }

    /**
     * 新增字典项
     */
    @PostMapping
    @Operation(summary = "新增字典项")
    @LogOperation(module = "字典管理", operationType = "CREATE", description = "新增字典项")
    public Result<DictItem> save(@Valid @RequestBody CreateDictItemRequest request) {
        DictItem dictItem = dictItemService.createDictItem(request);
        return Result.success(dictItem);
    }

    /**
     * 更新字典项
     */
    @PutMapping
    @Operation(summary = "更新字典项")
    @LogOperation(module = "字典管理", operationType = "UPDATE", description = "更新字典项")
    public Result<Boolean> update(@Valid @RequestBody UpdateDictItemRequest request) {
        boolean result = dictItemService.updateDictItem(request);
        return result ? Result.success(result) : Result.error("更新字典项失败");
    }

    /**
     * 删除字典项
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除字典项")
    @LogOperation(module = "字典管理", operationType = "DELETE", description = "删除字典项")
    public Result<Boolean> delete(@PathVariable Long id) {
        // 检查字典项是否存在
        com.xudis.iam.entity.DictItem dictItem = dictItemService.getById(id);
        if (dictItem == null) {
            return Result.error("字典项不存在");
        }

        // 检查是否有子项
        LambdaQueryWrapper<DictItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictItem::getParentId, id);
        long childCount = dictItemService.count(wrapper);
        if (childCount > 0) {
            return Result.error("该字典项下还有子项，无法删除");
        }

        boolean result = dictItemService.removeById(id);
        return result ? Result.success(result) : Result.error("删除字典项失败");
    }
}
