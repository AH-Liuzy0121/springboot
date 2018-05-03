package com.liuzy.module.quartz.service.impl;

import com.liuzy.module.quartz.service.SchedulerService;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * @className: SchedulerServiceImpl
 * @package: com.liuzy.module.quartz.service.impl
 * @describe: TODO
 * @auther: liuzhiyong
 * @date: 2018/5/3
 * @time: 上午 11:30
 */
public class SchedulerServiceImpl implements Serializable,SchedulerService {

    private static Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    public static final String APP_CTXT_KEY = "applicationContextKey";
    public static final String GROUP_NAME_DEFAULT = "DEFAULT";

    @Autowired
    @Qualifier("startQuartz")
    private Scheduler scheduler;

    @Override
    public void pauseTrigger(String triggerName, String group) {
        try{
            this.scheduler.pauseTrigger(new TriggerKey(triggerName));
        }catch (SchedulerException e){
            e.printStackTrace();
            logger.info("暂停定时任务异常: " + e.getMessage());
        }
    }

    @Override
    public void resumeTrigger(String triggerName, String group) {
        try {
            this.scheduler.resumeTrigger(new TriggerKey(triggerName));
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.info("恢复定时任务异常: " + e.getMessage());
        }
    }

    @Override
    public boolean removeTrigger(String triggerName, String group) throws Exception {
        logger.info("----- 移除定时任务 -----");
        this.scheduler.pauseTrigger(new TriggerKey(triggerName));

        return this.scheduler.unscheduleJob(new TriggerKey(triggerName));
    }

    /**
     * @methodName: addNewJob
     * @param: jobName 任务名称, jobGroupName 所属组, cronExpression 表达式, clz 任务类
     * @describe: 添加定时任务
     * @auther: liuzhiyong
     * @date: 2018/5/3
     * @time: 下午 3:19
     */
    @Override
    public String addNewJob(String jobName, String jobGroupName, String cronExpression, String clz) throws Exception {
       logger.info("----- 添加定时任务 开始 -----");
        SchedulerContext context = this.scheduler.getContext();
        ApplicationContext applicationContext = (ApplicationContext) context.get(APP_CTXT_KEY);
        logger.info("<<< " + applicationContext + "-----" + clz + " >>>");
        jobName = jobName + "_" + UUID.randomUUID().toString();

        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(jobName);
        jobDetail.setGroup(GROUP_NAME_DEFAULT);
        this.getClass();
        jobDetail.setJobClass((Class<? extends Job>) Class.forName(clz));
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(jobName);
        cronTrigger.setGroup(GROUP_NAME_DEFAULT);
        cronTrigger.setCronExpression(cronExpression);
        if(!this.scheduler.isShutdown()){
            this.scheduler.start();
        }
        logger.info("----- 添加定时任务 结束 -----");
        return "";
    }

    /**
     * @methodName: updateJob
     * @param: triggerName 触发名称, triggerGroupName 触发任务所属组, jobName 任务名称, cronExpression 表达式
     * @describe: 修改定时任务
     * @auther: liuzhiyong
     * @date: 2018/5/3
     * @time: 下午 3:21
     */
    @Override
    public boolean updateJob(String triggerName, String triggerGroupName, String jobName, String cronExpression) throws Exception {
        boolean isTrue = false;
        logger.info("----- 更新定时任务 开始 -----");
        logger.info("相应的参数: " + triggerName + ", " + triggerGroupName + ", " + jobName + ", " + cronExpression);
        if(StringUtils.isEmpty(triggerGroupName)){
            triggerGroupName = GROUP_NAME_DEFAULT;
        }
        Trigger trigger = this.scheduler.getTrigger(new TriggerKey(triggerName));
        if(trigger != null){
            JobDetail jobDetail = this.scheduler.getJobDetail(new JobKey(jobName));
            Class jobClass = jobDetail.getJobClass();
            this.pauseTrigger(triggerName,triggerGroupName);
            this.removeTrigger(triggerName,triggerGroupName);
            if(triggerName.indexOf("_") > 0){
                triggerName = triggerName.substring(0,triggerName.indexOf("_"));
                this.addNewJob(triggerName,triggerGroupName,cronExpression,jobClass.getName());
            }else {
                JobDataMap dataMap = jobDetail.getJobDataMap();
                SchedulerContext context = this.scheduler.getContext();
                ApplicationContext applicationContext = (ApplicationContext) context.get(APP_CTXT_KEY);
                Object targetObject = dataMap.get("targetObject");
                if(targetObject != null){
                    dataMap.put("targetObject",applicationContext.getBean(targetObject.getClass().getName()));
                }
                this.updateQuartzJob(jobName,triggerGroupName,cronExpression,jobDetail);
            }
            isTrue = true;
        }

        logger.info("----- 更新定时任务 结束 -----");
        return isTrue;
    }

    private void updateQuartzJob(String jobName,String triggerGroupName,String cronExpression,JobDetail jobDetail) throws Exception {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(jobName);
        cronTrigger.setGroup(APP_CTXT_KEY);
        cronTrigger.setCronExpression(cronExpression);
        this.scheduler.scheduleJob(jobDetail, cronTrigger);
        if(!this.scheduler.isShutdown()){
            this.scheduler.start();
        }
    }

    @Override
    public void addTrigger(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception {

    }
}
