package com.liuzy.module.quartz.bean;

import com.liuzy.module.quartz.bean.TaskThread;

import java.util.concurrent.ThreadFactory;

/**
 * @className: TaskThreadFactory
 * @package: com.liuzy.module.quartz
 * @describe: 创建线程工程
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 4:49
 */
public class TaskThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new TaskThread(r);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                TaskThread taskThread = (TaskThread) t;
                System.out.println("-----线程编号: " + taskThread.getTaskId() + ", 当前线程: " + taskThread.getCurrentTask());
            }
        });
        return thread;
    }
}
