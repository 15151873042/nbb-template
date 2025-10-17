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
    public CommonResult<Void> add(@Validated @RequestBody DictDataAddDTO addDTO) {
        dictDataService.addDictData(addDTO);
        return CommonResult.success();
    }

    /**
     * 修改保存字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping
    public CommonResult<Void> edit(@Validated @RequestBody DictDataUpdateDTO updateDTO) {
//
//        SysDictDataDO byId = dictDataService.getById(updateDTO.getId());
//        dictDataService.cacheEvitDictDataByDicType(byId.getDictType());

        dictDataService.updateDictData(updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除字典类型
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/{dictDataIds}")
    public CommonResult<Void> remove(@PathVariable List<Long> dictDataIds) {
        dictDataService.deleteDictDataByIds(dictDataIds);
        return CommonResult.success();
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
