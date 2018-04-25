package com.liuzy.module.log;

import com.liuzy.module.CommonConstant;
import com.liuzy.module.base.ResponseMsg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * @className: LoggerIntercept
 * @package: com.liuzy.module.log
 * @describe: 自定义拦截器，用于验证往来报文
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 2:29
 */
public class LoggerIntercept{

    private Logger logger = LoggerFactory.getLogger(LoggerIntercept.class);

    @Around("execution(* com.liuzy.module.web.*Controller*.*(..)) && @annotation(annotation)")
    public ResponseEntity<ResponseMsg<Object>> advice(ProceedingJoinPoint joinPoint, OperationDescription annotation)throws Throwable{
        String description = annotation.description();
        //若为查询接口，直接执行查询
        if(CommonConstant.REQUEST_QUERY.equalsIgnoreCase(description)){
            ResponseMsg<Object> responseMsg = new ResponseMsg<>();
            responseMsg.setRetCode(CommonConstant.RESPONSE_STATUS_OK);
            ResponseEntity<ResponseMsg<Object>> responseEntity = new ResponseEntity<ResponseMsg<Object>>(responseMsg,HttpStatus.OK);

        }
        return null;
    }
}
