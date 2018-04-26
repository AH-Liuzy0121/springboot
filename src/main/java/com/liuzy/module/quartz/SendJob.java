package com.liuzy.module.quartz;

import com.liuzy.module.service.UserService;
import com.liuzy.module.utils.BatchUtil;
import com.liuzy.module.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @className: SendJob
 * @package: com.liuzy.module.quartz
 * @describe:
 * @auther: liuzhiyong
 * @date: 2018/4/26
 * @time: 下午 3:32
 */
public class SendJob{

    private static final Logger logger = LoggerFactory.getLogger(SendJob.class);

    @Autowired
    private UserService userService;

    public void execute()throws Exception{

        try {
           //多线程执行任务
            batchExecSend();
        }catch (Exception e){
           logger.error("-----执行异常: " + e.getMessage());
        }
    }

    public void batchExecSend() throws Exception {
        String birthNo = DateUtil.getDateToString("MMdd",new Date());//获取用户的生日
        Map<String,Object> searchParams = new HashMap<>();
        searchParams.put("birthNo",birthNo);
        List<HashMap<String, Object>> certNoList = userService.searchUserListByCertNo(searchParams);
        long startTime = System.currentTimeMillis();
        logger.info("SendJob执行开始时间: " + System.currentTimeMillis());
        try {
            if(!CollectionUtils.isEmpty(certNoList)){
                List<List<HashMap<String,Object>>> batchList = BatchUtil.batchList(certNoList);//将任务拆分成多个批次包

                certNoList.clear();
                doBatchWithSend(batchList);//多线程处理批量任务
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.info("异常信息: " + e.getMessage());
        }finally {
            long endTime = System.currentTimeMillis();
            logger.info("SendJob执行结束时间： " + endTime + "， 共耗时： " + (endTime - startTime)/1000/1000 + " 秒");
        }
    }

    /**
     * @methodName: doBatchWithSend
     * @param:
     * @describe: 多线程处理批量任务
     * @auther: liuzhiyong
     * @date: 2018/4/26
     * @time: 下午 5:44
     */
    public void doBatchWithSend(List<List<HashMap<String,Object>>> batchList)throws Exception{
        logger.info("----- 批量任务处理开始 -----");
        if(!CollectionUtils.isEmpty(batchList)){
            List<Callable<ResultDTO>> tasks = new ArrayList<>(batchList.size());
            for (int i = 0;i < batchList.size();i++){
                tasks.add(new SendTask(i + "",batchList.get(i),userService));
            }

            List<ResultDTO> resultList = TaskThreadPoolUtils.getInstance().doBatchWithTask(tasks, "定时-发短信任务");
            for (ResultDTO result:resultList){
                if(PublicConstant.RESULT_NO == result.getResult()){
                    logger.error(" -----短信发送失败----- ");
                }
            }
        }
        logger.info("----- 批量任务处理结束 -----");
    }
}
