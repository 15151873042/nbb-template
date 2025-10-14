package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictTypeAddDTO;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.dto.DictTypeUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController {

    @Resource
    private SysDictTypeService dictTypeService;

    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysDictTypeDO>> list(DictTypePageDTO queryDTO) {
        PageResult<SysDictTypeDO> pageResult = dictTypeService.listPageDictType(queryDTO);
        return CommonResult.success(pageResult);
    }

    /**
     * 查询字典类型详细
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{id}")
    public CommonResult<SysDictTypeDO> getInfo(@PathVariable Long id) {
        return CommonResult.success(dictTypeService.selectDictTypeById(id));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @PostMapping
    public CommonResult<Integer> add(@Validated @RequestBody DictTypeAddDTO addDTO) {
        return CommonResult.success(dictTypeService.addDictType(addDTO));
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @PutMapping
    public CommonResult<Integer> edit(@Validated @RequestBody DictTypeUpdateDTO updateDTO) {
        return CommonResult.success(dictTypeService.updateDictType(updateDTO));
    }


}
