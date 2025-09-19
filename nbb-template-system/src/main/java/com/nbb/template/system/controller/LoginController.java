package com.nbb.template.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 胡鹏
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
