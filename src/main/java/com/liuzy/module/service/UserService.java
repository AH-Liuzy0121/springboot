package com.liuzy.module.service;

import com.liuzy.module.bean.User;
import com.liuzy.module.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
  * @className: UserService
  * @package: com.liuzy.module.service
  * @describe: 业务逻辑层
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 8:00
  */
@Service
public class UserService {

    @Autowired
    private UserMapper dao;

    public User searchUser(String phone) throws Exception{

        return dao.searchUser(phone) == null ? null:dao.searchUser(phone);
    }
}
