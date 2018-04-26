package com.liuzy.module.utils;

import com.liuzy.module.quartz.PublicConstant;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @className: BatchUtil
 * @package: com.liuzy.module.utils
 * @describe: 工具类
 * @auther: liuzhiyong
 * @date: 2018/4/26
 * @time: 下午 4:57
 */
public class BatchUtil {

    public static List<List<HashMap<String, Object>>> batchList(List<?> tasks)throws Exception{
        List<List<HashMap<String, Object>>> resultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tasks)){
            int taskNum = tasks.size();
            int fromIndex = 0;
            int toIndex = PublicConstant.LIMIT_NUM;

            if (taskNum <= PublicConstant.LIMIT_NUM){
                resultList.add(new ArrayList(tasks));
            }else {
                while (toIndex <= taskNum){
                    resultList.add(new ArrayList(tasks.subList(fromIndex,toIndex)));

                    if(toIndex == taskNum){
                        break;
                    }

                    fromIndex = fromIndex + PublicConstant.LIMIT_NUM;
                    if(toIndex + PublicConstant.LIMIT_NUM > taskNum){
                        toIndex = taskNum;
                    }else {
                        toIndex = toIndex + PublicConstant.LIMIT_NUM;
                    }
                }
            }
        }

        return resultList;
    }
}
