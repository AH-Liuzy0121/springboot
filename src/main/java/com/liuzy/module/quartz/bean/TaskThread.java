package com.liuzy.module.quartz.bean;

import com.liuzy.module.quartz.bean.BatchTask;

/**
 * @className: TaskThread
 * @package: com.liuzy.module.quartz
 * @describe: 自定义线程类
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 4:11
 */
public class TaskThread extends Thread{
    private String taskId;

    private BatchTask currentTask;

    public TaskThread(Runnable target) {
        super(target);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public BatchTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(BatchTask currentTask) {
        this.currentTask = currentTask;
    }
}
