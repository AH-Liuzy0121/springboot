package com.liuzy.module.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
  * @className: HelloWorld
  * @package: com.liuzy.module.web
  * @describe: Spring Boot Demo(HelloWorld)
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 5:46
  */
@RestController
@EnableAutoConfiguration
public class HelloWorld {

    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @RequestMapping("/hello")
    public String home(){

        return "HelloWorld";
    }

    @RequestMapping("/hello/{name}")
    public String name(@PathVariable String name){
        logger.info("接收参数: " + name);
        return "Welcome " + name + " !!!";
    }
}
