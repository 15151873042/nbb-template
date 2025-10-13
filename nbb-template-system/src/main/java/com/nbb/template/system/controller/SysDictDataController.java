package com.nbb.template.system.controller;

import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.service.SysDictDataService;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public CommonResult<List<SysDictDataDO>> dictType(@PathVariable String dictType) {
        List<SysDictDataDO> dictDataList = dictTypeService.selectDictDataByType(dictType);
        return CommonResult.success(dictDataList);
    }
}
