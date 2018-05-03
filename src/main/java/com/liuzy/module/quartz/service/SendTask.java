package com.liuzy.module.quartz.service;

import com.liuzy.module.quartz.bean.BatchTask;
import com.liuzy.module.quartz.bean.ResultDTO;
import com.liuzy.module.quartz.bean.TaskThread;
import com.liuzy.module.quartz.utils.PublicConstant;
import com.liuzy.module.quartz.utils.TaskThreadPoolUtils;
import com.liuzy.module.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @className: SendTask
 * @package: com.liuzy.module.quartz
 * @describe: 执行线程类，用于发送信息
 * @auther: liuzhiyong
 * @date: 2018/4/26
 * @time: 下午 2:50
 */
public class SendTask extends BatchTask {
    private static final Logger logger = LoggerFactory.getLogger(SendTask.class);

    //需要执行的Service服务
    private UserService userService;

    private List<HashMap<String,Object>> batchList;

    public SendTask(String taskId,List<HashMap<String,Object>> batchList,UserService userService) {
        super(taskId);
        this.batchList = batchList;
        this.userService = userService;
    }

    @Override
    public ResultDTO call()throws Exception{
        TaskThread thread = (TaskThread)Thread.currentThread();
        String threadId = thread.getName();
        thread.setTaskId(super.getTaskId());
        int result = PublicConstant.RESULT_NO;
        String errorMsg = null;
        try{
            logger.info("------ 任务执行开始: 任务编码: " + super.getTaskId() + ", 线程编码: " + threadId + " -----");

            //TODO 执行具体的业务 比如给用户发短信，祝福用户生日快乐

            result = PublicConstant.RESULT_YES;
            TaskThreadPoolUtils.getInstance().getSuccessCount().incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
            TaskThreadPoolUtils.getInstance().getFailCount().incrementAndGet();
            logger.info("异常信息: " + e.getMessage());
        }finally {
            TaskThreadPoolUtils.getInstance().getCountDownLatch().countDown();
        }

        return super.packageResult(result,threadId,errorMsg);
    }

}
