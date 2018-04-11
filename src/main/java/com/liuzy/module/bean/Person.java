package com.liuzy.module.bean;

import java.io.Serializable;

/**
  * @className: Person
  * @package: com.liuzy.module.bean
  * @describe: 实体Bean
  * @auther: liuzhiyong
  * @date: 2018/4/11
  * @time: 上午 10:07
  */
public class Person implements Serializable{

    private java.lang.String id;//工号

    private java.lang.String lastName;//名字

    private java.lang.Integer age;//年龄

    public Person() {
    }

    public Person(String id, String lastName, Integer age) {
        this.id = id;
        this.lastName = lastName;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id = '" + id + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", age = " + age ;
    }
}
