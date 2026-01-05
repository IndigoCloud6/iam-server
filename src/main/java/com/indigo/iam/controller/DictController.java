package com.indigo.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indigo.iam.annotation.LogOperation;
import com.indigo.iam.common.Result;
import com.indigo.iam.dto.CreateDictRequest;
import com.indigo.iam.dto.UpdateDictRequest;
import com.indigo.iam.entity.Dict;
import com.indigo.iam.entity.DictItem;
import com.indigo.iam.service.DictItemService;
import com.indigo.iam.service.DictService;
import com.indigo.iam.vo.DictWithItemsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
@Tag(name = "数据字典管理", description = "数据字典相关接口")
public class DictController {

    private final DictService dictService;
    private final DictItemService dictItemService;

    /**
     * 分页查询字典列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询字典列表")
    @LogOperation(module = "字典管理", operationType = "QUERY", description = "分页查询字典列表")
    public Result<Page<Dict>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String dictCode,
            @RequestParam(required = false) String dictName) {
        Page<Dict> page = new Page<>(current, size);
        Page<Dict> result = dictService.pageDicts(page, dictCode, dictName);
        return Result.success(result);
    }

    /**
     * 查询所有字典（包含字典项）
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有字典（包含字典项）")
    @LogOperation(module = "字典管理", operationType = "QUERY", description = "查询所有字典及字典项")
    public Result<List<DictWithItemsVO>> listDictsWithItems() {
        List<Dict> dicts = dictService.list();

        List<DictWithItemsVO> result = dicts.stream()
                .map(dict -> {
                    DictWithItemsVO vo = new DictWithItemsVO();
                    vo.setId(dict.getId());
                    vo.setDictCode(dict.getDictCode());
                    vo.setDictName(dict.getDictName());
                    vo.setDictType(dict.getDictType());
                    vo.setDescription(dict.getDescription());
                    vo.setStatus(dict.getStatus());

                    // 查询字典项
                    List<DictItem> items = dictItemService.getItemsByDictId(dict.getId());
                    List<DictWithItemsVO.DictItemVO> itemVOs = items.stream()
                            .map(item -> {
                                DictWithItemsVO.DictItemVO itemVO = new DictWithItemsVO.DictItemVO();
                                itemVO.setId(item.getId());
                                itemVO.setItemCode(item.getItemCode());
                                itemVO.setItemName(item.getItemName());
                                itemVO.setItemValue(item.getItemValue());
                                itemVO.setParentId(item.getParentId());
                                itemVO.setOrderNum(item.getOrderNum());
                                itemVO.setStatus(item.getStatus());
                                return itemVO;
                            })
                            .collect(Collectors.toList());
                    vo.setItems(itemVOs);

                    return vo;
                })
                .collect(Collectors.toList());

        return Result.success(result);
    }

    /**
     * 根据字典编码查询字典项
     */
    @GetMapping("/items/{dictCode}")
    @Operation(summary = "根据字典编码查询字典项")
    public Result<List<DictItem>> getItemsByDictCode(@PathVariable String dictCode) {
        List<DictItem> items = dictItemService.getItemsByDictCode(dictCode);
        return Result.success(items);
    }

    /**
     * 新增字典
     */
    @PostMapping
    @Operation(summary = "新增字典")
    @LogOperation(module = "字典管理", operationType = "CREATE", description = "新增字典")
    public Result<Dict> save(@Valid @RequestBody CreateDictRequest request) {
        Dict dict = dictService.createDict(request);
        return Result.success(dict);
    }

    /**
     * 更新字典
     */
    @PutMapping
    @Operation(summary = "更新字典")
    @LogOperation(module = "字典管理", operationType = "UPDATE", description = "更新字典")
    public Result<Boolean> update(@Valid @RequestBody UpdateDictRequest request) {
        boolean result = dictService.updateDict(request);
        return result ? Result.success(result) : Result.error("更新字典失败");
    }

    /**
     * 删除字典
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除字典")
    @LogOperation(module = "字典管理", operationType = "DELETE", description = "删除字典")
    public Result<Boolean> delete(@PathVariable Long id) {
        // 检查是否有字典项
        List<DictItem> items = dictItemService.getItemsByDictId(id);
        if (!items.isEmpty()) {
            return Result.error("该字典下还有字典项，无法删除");
        }

        boolean result = dictService.removeById(id);
        return result ? Result.success(result) : Result.error("删除字典失败");
    }
}
