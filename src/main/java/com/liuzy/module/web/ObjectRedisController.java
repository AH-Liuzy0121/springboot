package com.liuzy.module.web;

import com.liuzy.module.bean.Person;
import com.liuzy.module.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
   * @className: ObjectRedisController
   * @package: com.liuzy.module.web
   * @describe: 公共对象Redis
   * @auther: liuzhiyong
   * @date: 2018/4/11
   * @time: 上午 10:33
   */
@RestController
public class ObjectRedisController {

    private static Logger logger = LoggerFactory.getLogger(ObjectRedisController.class);

    @Autowired
    PersonService personService;

    @RequestMapping("/api/search")
    public Person searchPerson(String id){
        logger.info("Request id = " + id);
        return personService.searchPerson(id);
    }

    @RequestMapping("/api/save")
    public void savePerson(String id,String lastName,Integer age){
        logger.info("Request id = " + id + ", lastName = " + lastName + ", age = " + age);
        Person person = new Person();
        person.setId(id);
        person.setLastName(lastName);
        person.setAge(age);
        personService.savePerson(person);
    }
}
