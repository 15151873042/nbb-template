package com.nbb.template.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.nbb.template.system.core.domain.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 胡鹏
 */
@RestController
public class TestController {

    @RequestMapping("/form-urlencoded-input-param")
    public CommonResult<TestPageDTO> formUrlencodedInputParam(TestPageDTO dto) {
        return CommonResult.success(dto);
    }


    @RequestMapping("/json-input-param")
    public CommonResult<TestPageDTO> jsonInputParam(@RequestBody TestPageDTO dto) {
        return CommonResult.success(dto);
    }


    @SaIgnore
    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] bodyBytes = ServletUtil.getBodyBytes(request);
        response.getWriter().write("abc");
    }

}
