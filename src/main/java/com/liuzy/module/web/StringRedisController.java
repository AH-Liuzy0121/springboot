package com.liuzy.module.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
  * @className: StringRedisController
  * @package: com.liuzy.module.web
  * @describe: 引入redis测试(StringRedisTemplate)
  * @auther: liuzhiyong
  * @date: 2018/4/11
  * @time: 上午 10:01
  */
@RestController
public class StringRedisController {

    private static Logger logger = LoggerFactory.getLogger(StringRedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String,String> valueOperations;

    @RequestMapping("set")
    public String setKeyAndValue(String key,String value){
        logger.info("SET Key = " + key + " Value " + value);
        valueOperations.set(key,value);
        return "Set key and value OK";
    }

    @RequestMapping("get")
    public String getValueByKey(String key){
        String value = valueOperations.get(key);
        logger.info("Request Key = " + key + " Response = " + value);
        return value;
    }
 }
