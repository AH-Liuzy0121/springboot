package com.liuzy.module.base;

import java.io.Serializable;

/**
 * @className: ResponseMsg
 * @package: com.liuzy.module.base
 * @describe: 自定义返回报文格式
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 2:47
 */
public class ResponseMsg<T> implements Serializable{

    private T responseBody;

    private ResponseMsg<T>.Header header = new ResponseMsg.Header();

    public ResponseMsg() {
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public String getRetCode() {
        return this.header.retCode;
    }

    public void setRetCode(String retCode) {
        this.header.retCode = retCode;
    }

    public String getErrorDesc() {
        return this.header.errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.header.errorDesc = errorDesc;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Header [retCode=");
        builder.append(this.getRetCode());
        builder.append(", errorDesc=");
        builder.append(this.getErrorDesc());
        builder.append("]");
        builder.append("   Body [");
        if(this.responseBody != null) {
            builder.append(this.responseBody.toString());
        }

        builder.append("]");
        return builder.toString();
    }

    private final class Header {
        String retCode;
        String errorDesc;

        private Header(){
            retCode = "200";
            errorDesc = null;
        }
     }
}



