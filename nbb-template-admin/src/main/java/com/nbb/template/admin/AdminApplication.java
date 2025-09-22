package com.nbb.template.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 胡鹏
 */
@MapperScan("com.nbb.**.mapper")
@SpringBootApplication(scanBasePackages= {"com.nbb.template"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
