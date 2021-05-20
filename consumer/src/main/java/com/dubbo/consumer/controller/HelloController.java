package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @GetMapping(value = "/sayHello")
    public String sayHello(){
        String msg = helloService.sayHello();
        return msg;
    }

    @PostMapping(value = "/login")
    public String login(String userName){
        return helloService.login(userName);
    }

}
