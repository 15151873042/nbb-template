package com.nbb.template.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult<PageResult<SysDictTypeDO>> list(DictTypePageDTO dto) {
        PageResult<SysDictTypeDO> pageResult = dictTypeService.listDictTypePage(dto);
        return CommonResult.success(pageResult);
    }
}
