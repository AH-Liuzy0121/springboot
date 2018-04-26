package com.liuzy.module.service;

import com.liuzy.module.bean.User;
import com.liuzy.module.dao.UserMapper;
import com.liuzy.module.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper dao;

    @Autowired
    private UserRepository repository;

    public User searchUser(String phone) throws Exception{

        return dao.searchUser(phone) == null?null:dao.searchUser(phone);
    }

    public User searchUserByPhone(String phone) throws Exception{

        return repository.searchUser(phone) == null?null:repository.searchUser(phone);
    }

    public List<HashMap<String,Object>> searchUserListByCertNo(Map<String,Object> searchParams)throws Exception{

        return dao.searchUserListByCertNo(searchParams);
    }
}
