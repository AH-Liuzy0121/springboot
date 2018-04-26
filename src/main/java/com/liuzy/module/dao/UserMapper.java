package com.liuzy.module.dao;

import com.liuzy.module.bean.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @className: UserMapper
  * @package: com.liuzy.module.dao
  * @describe: 表现层
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 7:31
  */
public interface UserMapper {

    List<User> searchUserList(Map<String,Object> searchParams);

    Long updateSelective(User user);

    User searchUser(String phone);

    Long insertSelective(Map<String,Object> searchParams);

    List<HashMap<String,Object>> searchUserListByCertNo(Map<String,Object> searchParams);

}
