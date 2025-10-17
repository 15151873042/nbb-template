package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictDataAddDTO;
import com.nbb.template.system.domain.dto.DictDataPageDTO;
import com.nbb.template.system.domain.dto.DictDataUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.service.SysDictDataService;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    @Autowired
    private SysDictDataService dictDataService;
    @Autowired
    private SysDictTypeService dictTypeService;


    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysDictDataDO>> list(DictDataPageDTO dto) {
        PageResult<SysDictDataDO> result = dictDataService.listPage(dto);
        return CommonResult.success(result);
    }

    /**
     * 查询字典数据详细
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{id}")
    public CommonResult<SysDictDataDO> getInfo(@PathVariable Long id) {
        return CommonResult.success(dictDataService.getById(id));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping
    public CommonResult<Integer> add(@Validated @RequestBody DictDataAddDTO addDTO) {
        return CommonResult.success(dictDataService.addDictData(addDTO));
    }

    /**
     * 修改保存字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping
    public CommonResult<Integer> edit(@Validated @RequestBody DictDataUpdateDTO updateDTO) {
        // 先根据id查询数据，并赋值字典类型，为了接下来更新数据是，可以缓存失效
        SysDictDataDO existDictDataDO = dictDataService.getById(updateDTO.getId());
        updateDTO.setDictType(existDictDataDO.getDictType());

        return CommonResult.success(dictDataService.updateDictData(updateDTO));
    }

    /**
     * 删除字典类型
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/{dictDataIds}")
    public CommonResult<Integer> remove(@PathVariable List<Long> dictDataIds) {
        // 只能批量删除同一字典类型下的数据，这边随机去一个，为了获取字典type，为了接下来更删除数据时，可以缓存失效
        Long randomDictDataId = dictDataIds.get(0);
        SysDictDataDO existDictDataDO = dictDataService.getById(randomDictDataId);

        return CommonResult.success(dictDataService.deleteDictDataByIds(dictDataIds, existDictDataDO.getDictType()));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public CommonResult<List<SysDictDataDO>> dictType(@PathVariable String dictType) {
        List<SysDictDataDO> dictDataList = dictDataService.listDictDataByDictType(dictType);
        return CommonResult.success(dictDataList);
    }
}
