package com.nbb.template.admin.controller;

import com.nbb.template.system.core.domain.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 胡鹏
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("")
    public CommonResult<Boolean> test() {
        boolean success = new CommonResult<>().isSuccess();
        boolean error = new CommonResult<>().isError();
        return CommonResult.success(true);
    }
}
