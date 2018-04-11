package com.liuzy.module.web;

import com.liuzy.module.bean.User;
import com.liuzy.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
  * @className: UserController
  * @package: com.liuzy.module.web
  * @describe: 表现层
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 7:24
  */
@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/api/{phone}")
    public User searchUserByPhone(@PathVariable String phone)throws Exception{
        if(phone == null) throw new Exception("手机号不能为空！");

        return userService.searchUser(phone);
    }

    @GetMapping("/api/save")
    public void saveUser(){
        User user = new User();
        user.setId(1);
        user.setUserName("");
        user.setPhoneNo("");
        user.setCertNo("");
        user.setPassword("");

        mongoTemplate.save(user);
    }

    @GetMapping("/api/search/{phone}")
    public List<User> searchUserList() throws Exception{

        return mongoTemplate.findAll(User.class);
    }
}
