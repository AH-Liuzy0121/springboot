package com.liuzy.module.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: TaskThreadPoolUtils
 * @package: com.liuzy.module.quartz
 * @describe: 创建自定义线程池
 * @auther: liuzhiyong
 * @date: 2018/4/25
 * @time: 下午 4:34
 */
public class TaskThreadPoolUtils {

    private static final Logger logger = LoggerFactory.getLogger(TaskThreadPoolUtils.class);

    //线程池实例
    public static TaskThreadPoolUtils instance;

    /**
     * 线程锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * executor
     */
    private ExecutorService executor;

    /**
     * 执行Service
     */
    private ExecutorCompletionService<ResultDTO> service;

    /**
     * 成功笔数
     */
    private AtomicInteger successCount = new AtomicInteger(0);

    /**
     * 失败笔数
     */
    private AtomicInteger failCount = new AtomicInteger(0);

    /**
     * 信号标量
     */
    private CountDownLatch countDownLatch;

    //线程数量
    public static final int THREAD_COUNT = 20;

    /**
     * 私有化构造器
     */
    private TaskThreadPoolUtils(){
        TaskThreadFactory taskThreadFactory = new TaskThreadFactory();//线程工厂
        this.executor = Executors.newFixedThreadPool(THREAD_COUNT,taskThreadFactory);
        this.service = new ExecutorCompletionService<ResultDTO>(executor);
    }

    /**
     * 提供公共的对象实例方法
     * @return
     */
    public synchronized static TaskThreadPoolUtils getInstance(){
       if(instance == null){
           instance = new TaskThreadPoolUtils();
       }
       return instance;
    }

    /**
     * 开启线程池
     */
    public void openPool(){
        System.out.println("===== 开启线程池开始 =====");
        if(executor.isShutdown()){
            TaskThreadFactory taskThreadFactory = new TaskThreadFactory();
            this.executor = Executors.newFixedThreadPool(THREAD_COUNT,taskThreadFactory);
            this.service = new ExecutorCompletionService<ResultDTO>(executor);
        }
        System.out.println("===== 线程池已开启 =====");
    }

    public void closePool(){
        System.out.println("===== 关闭线程池开始 =====");
        System.out.println("-----成功数: " + this.successCount.get() + ", 失败数: " + this.failCount.get());
        this.successCount.set(0);
        this.failCount.set(0);
        this.countDownLatch = null;
        System.out.println("===== 线程池已关闭 =====");
    }

    public void closePool2(){
        System.out.println("===== 关闭线程池开始 =====");
        System.out.println("-----成功数: " + this.successCount.get() + ", 失败数: " + this.failCount.get());
        executor.shutdown();
        this.successCount.set(0);
        this.failCount.set(0);
        this.countDownLatch = null;
        System.out.println("===== 线程池已关闭 =====");
    }

    /**
     * @methodName: doBatchWithTask
     * @param: tasks 执行结果 title 任务名称
     * @describe: 业务执行
     * @auther: liuzhiyong
     * @date: 2018/4/25
     * @time: 下午 5:23
     */
    public List<ResultDTO> doBatchWithTask(List<Callable<ResultDTO>> tasks,String title){
        //任务个数验证
        if(tasks == null || tasks.size() == 0){
            return null;
        }

        lock.lock();
        //用于存放遍历结果
        List<ResultDTO> result = new ArrayList<>();
        boolean flag = true;
        try{
            int taskNum = tasks.size();
            List<Future<ResultDTO>> futures = new ArrayList<>(taskNum);
            //创建计数器
            this.countDownLatch = new CountDownLatch(taskNum);
            for(Callable<ResultDTO> dto:tasks){
                futures.add(this.service.submit(dto));
            }
            this.countDownLatch.await();//任务结束

            ResultDTO resultDTO;
            for(Future<ResultDTO> future:futures){
                resultDTO = future.get();
                if(PublicConstant.RESULT_NO == resultDTO.getResult()){
                    flag = false;
                }
                result.add(resultDTO);
            }
            return result;
        }catch (Exception e){
            //实际生产中，此处应该抛出自定义的异常类
            e.printStackTrace();
            System.out.println("异常信息: " + e.getMessage());
        }finally {
           openPool();

           if(!flag){
               printErrorInfo(result,title);
           }
           lock.unlock();
        }

        return null;
    }

    /***
     * @methodName: doBatchWithTask
     * @param: tasks, title, taskLatch, latch
     * @describe: 业务执行，并保证处于同一事物内
     * @auther: liuzhiyong
     * @date: 2018/4/25
     * @time: 下午 6:04
     */
    public List<ResultDTO> doBatchWithTask(List<Callable<ResultDTO>> tasks,String title,CountDownLatch taskLatch,CountDownLatch latch){
        //任务个数验证
        if(tasks == null || tasks.size() == 0){
            return null;
        }

        lock.lock();
        //用于存放遍历结果
        List<ResultDTO> result = new ArrayList<>();
        boolean flag = true;
        try{
            int taskNum = tasks.size();
            List<Future<ResultDTO>> futures = new ArrayList<>(taskNum);
            //创建计数器
            this.countDownLatch = new CountDownLatch(taskNum);
            for(Callable<ResultDTO> dto:tasks){
                futures.add(this.service.submit(dto));
            }
            taskLatch.await();
            latch.countDown();
            this.countDownLatch.await();//任务结束

            ResultDTO resultDTO;
            for(Future<ResultDTO> future:futures){
                resultDTO = future.get();
                if(PublicConstant.RESULT_NO == resultDTO.getResult()){
                    flag = false;
                }
                result.add(resultDTO);
            }
            return result;
        }catch (Exception e){
            //实际生产中，此处应该抛出自定义的异常类
            e.printStackTrace();
            System.out.println("异常信息: " + e.getMessage());
        }finally {
            openPool();

            if(!flag){
                printErrorInfo(result,title);
            }
            lock.unlock();
        }

        return null;
    }

    public void printErrorInfo(List<ResultDTO> result, String title){
        logger.error("===== " + title + " 异常信息打印开始 =====");
        for (ResultDTO dto:result){
            logger.error("异常信息： " + dto.getErrorMsg());
        }
        logger.error("===== " + title + " 异常信息打印结束 =====");
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ExecutorCompletionService<ResultDTO> getService() {
        return service;
    }

    public AtomicInteger getSuccessCount() {
        return successCount;
    }

    public AtomicInteger getFailCount() {
        return failCount;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
