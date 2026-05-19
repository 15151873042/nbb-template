package com.nbb.template.admin.controller;

import cn.hutool.core.util.IdUtil;
import com.nbb.template.system.core.domain.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 胡鹏
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public CommonResult<TestPageDTO> test(TestPageDTO dto) {
        return CommonResult.success(dto);
    }


    @RequestMapping("/test2")
    public CommonResult<TestPageDTO> test2(@RequestBody TestPageDTO dto) {
        return CommonResult.success(dto);
    }

    public static void main(String[] args) {
        String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
        System.out.println(snowflakeNextIdStr);
    }

}
