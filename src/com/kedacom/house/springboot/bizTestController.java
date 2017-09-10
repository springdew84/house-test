package com.kedacom.house.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring-boot demo
 * @author Administrator
 * @since 2017-09-10 21:30:20
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/springboot")
public class bizTestController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(bizTestController.class, args);
    }
}