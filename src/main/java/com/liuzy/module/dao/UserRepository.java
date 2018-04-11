package com.liuzy.module.dao;

import com.liuzy.module.bean.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
   * @className: UserRepository
   * @package: com.liuzy.module.dao
   * @describe: 创建User的Repository
   * @auther: liuzhiyong
   * @date: 2018/4/11
   * @time: 上午 11:12
   */
public interface UserRepository extends MongoRepository<User,String> {

    User searchUser(String phone);

    Integer saveUser(User user);
}
