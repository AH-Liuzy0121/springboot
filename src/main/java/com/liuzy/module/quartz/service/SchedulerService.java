package com.liuzy.module.quartz.service;

/**
 * @className: SchedulerService
 * @package: com.liuzy.module.quartz.service
 * @describe: TODO
 * @auther: liuzhiyong
 * @date: 2018/5/3
 * @time: 上午 11:20
 */
public interface SchedulerService {

    void pauseTrigger(String var1 , String var2);

    void resumeTrigger(String var1 , String var2);

    boolean removeTrigger(String var1 , String var2) throws Exception;

    String addNewJob(String var1 , String var2 , String var3 , String var4) throws Exception;

    boolean updateJob(String var1 , String var2 , String var3 , String var4)throws Exception;

    void addTrigger(String var1 , String var2 , String var3 , String var4 , String var5 , String var6 , String var7) throws Exception;
}
