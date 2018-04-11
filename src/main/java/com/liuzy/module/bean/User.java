package com.liuzy.module.bean;

import java.io.Serializable;

/**
  * @className: User
  * @package: com.liuzy.module.bean
  * @describe: 实体类Bean
  * @auther: liuzhiyong
  * @date: 2018/4/10
  * @time: 下午 7:16
  */
public class User implements Serializable{
    private Integer id;

    private java.lang.String userName;//姓名

    private java.lang.String phoneNo;//手机号

    private java.lang.String certNo;//身份证号

    private java.lang.String password;//密码

    public User() {
    }

    public User(Integer id, String userName, String phoneNo, String certNo, String password) {
       this.id = id;
       this.userName = userName;
       this.phoneNo = phoneNo;
       this.certNo = certNo;
       this.password = password;
    }

    public Integer getId() {
     return id;
    }

    public void setId(Integer id) {
     this.id = id;
    }

  public String getUserName() {
     return userName;
    }

    public void setUserName(String name) {
     this.userName = userName;
    }

    public String getPhoneNo() {
     return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
     this.phoneNo = phoneNo;
    }

    public String getCertNo() {
     return certNo;
    }

    public void setCertNo(String certNo) {
     this.certNo = certNo;
    }

    public String getPassword() {
     return password;
    }

    public void setPassword(String password) {
     this.password = password;
    }

    @Override
    public String toString() {
     return "id = " + id +
             ", userName = '" + userName + '\'' +
             ", phoneNo = '" + phoneNo + '\'' +
             ", certNo = '" + certNo + '\'' +
             ", password = '" + password + '\'' ;
    }
 }
