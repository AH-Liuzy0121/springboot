package com.liuzy.module.service;

import com.liuzy.module.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
   * @className: PersonService
   * @package: com.liuzy.module.service
   * @describe: 设置及获取Person对象
   * @auther: liuzhiyong
   * @date: 2018/4/11
   * @time: 上午 10:38
   */
@Service
public class PersonService {

    private static Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Resource(name = "redisTemplate")
    ValueOperations<Object,Object> valueOperations;

    public void savePerson(Person person){
        logger.info("Set Person " + person);
        valueOperations.set(person.getId(),person);
    }

    public Person searchPerson(String id){
        Person person = (Person)valueOperations.get(id);
        logger.info("Request id = " + id + ", Response = " + person);
        return person;
    }
}
