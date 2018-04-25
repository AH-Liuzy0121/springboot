package com.liuzy.module.quartz;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @className: ResultDTO
 * @package: com.liuzy.module.quartz
 * @describe: 多线程执行结果封装
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 3:22
 */
public class ResultDTO implements Serializable{
    private String taskId;

    private String threadId;

    private int result;

    private String errorMsg;

    public ResultDTO() {
        super();
    }

    public ResultDTO(String taskId, String threadId, int result, String errorMsg) {
        this.taskId = taskId;
        this.threadId = threadId;
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("---任务编号: ");
        buffer.append(this.taskId);
        buffer.append(", 执行线程编号: ");
        buffer.append(this.threadId);
        buffer.append(", 执行结果: ");
        if(this.getResult() == 1){
            buffer.append("成功 ");
        }else {
            buffer.append("失败 ");
        }
        buffer.append("错误日志： ");
        buffer.append(this.errorMsg);
        buffer.append(" ---");
        return buffer.toString();
    }
}
