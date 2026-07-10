package com.nbb.template.system.controller;

import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.service.SysConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 参数配置
 *
 * @author 胡鹏
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController {

    @Resource
    private SysConfigService configService;

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public CommonResult<String> getConfigKey(@PathVariable String configKey) {
        return CommonResult.success(configService.selectConfigByKey(configKey));
    }
}
