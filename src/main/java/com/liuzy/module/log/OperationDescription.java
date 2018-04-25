package com.liuzy.module.log;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.lang.annotation.*;

/**
 * @className: OperationDescription
 * @package: com.liuzy.module.log
 * @describe: 自定义注解，用于描述接口性质
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 2:20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationDescription {

    /**
     * @methodName: description
     * @param: 方法描述
     * @describe: TODO
     * @auther: liuzhiyong
     * @date: 2018/4/25
     * @time: 下午 2:26
     */
    String description() default "no description";

    /**
     * @methodName: entryType
     * @param: 实体类类型
     * @describe: TODO
     * @auther: liuzhiyong
     * @date: 2018/4/25
     * @time: 下午 2:26
     */
    String entryType() default "";
}
