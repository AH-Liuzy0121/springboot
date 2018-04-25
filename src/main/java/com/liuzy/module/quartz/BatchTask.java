package com.liuzy.module.quartz;

import java.util.concurrent.Callable;

/**
 * @className: BatchTask
 * @package: com.liuzy.module.quartz
 * @describe: 多线程超类
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 3:22
 */
public class BatchTask implements Callable<ResultDTO>{
   private String taskId;

    public BatchTask(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public ResultDTO call() throws Exception {
        TaskThread thread = (TaskThread)Thread.currentThread();
        String threadId = thread.getName();//线程编号
        thread.setTaskId(this.taskId);
        int result = PublicConstant.RESULT_NO;
        try {
            System.out.println("-----业务执行:----- 任务编号: " + this.taskId + ", 线程编号: " + threadId);
            Thread.sleep(500);
            result = PublicConstant.RESULT_YES;//1-成功 0-失败

            TaskThreadPoolUtils.getInstance().getSuccessCount().incrementAndGet();
        }catch (Exception e){
            TaskThreadPoolUtils.getInstance().getFailCount().incrementAndGet();
        }finally {
            TaskThreadPoolUtils.getInstance().getCountDownLatch().countDown();
        }

        return new ResultDTO(this.taskId,threadId,result,"");
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
