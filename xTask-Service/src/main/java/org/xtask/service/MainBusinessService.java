package org.xtask.service;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xtask.tool.XTaskException;
import org.xtask.bean.TaskInfo;

import java.util.Date;

/**
 * Created by zhxy on 16/12/19.
 */
@Service
public class MainBusinessService {
    public static final String RootPath = "/task";
    public static final String AppName_Pattern = "/%s";
    public static final String EnableTaskPath_Pattern = RootPath+AppName_Pattern+"/enable";
    public static final String DisableTaskPath_Pattern = RootPath+AppName_Pattern+"/disable";
    public static final String WaitRunTaskPath_Pattern = RootPath+AppName_Pattern+"/waitRun";
    public static final String RuntimeTaskPath_Pattern = RootPath+AppName_Pattern+"/runtime";
    public static final String HistoryTaskPath_Pattern = RootPath+AppName_Pattern+"/history";

    public static final String ServersTaskPath_Pattern = RootPath+AppName_Pattern+"/servers";

    public static final String DATETIME_YYYYMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";

    @Autowired
    private ZkBasicService zkBasicService;
    @Autowired
    private QuartzService quartzService;

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());


    public boolean addNewTaskItem(String appName,String className,String methodName,String description,String cronExpression) throws Exception {

        logger.debug("appName:{}, className:{}, methodName:{}, description:{}, cronExpression:{}", appName, className, methodName, description, cronExpression);
        if(StringUtils.isEmpty(appName)
                || StringUtils.isEmpty(className)
                || StringUtils.isEmpty(methodName)
                || StringUtils.isEmpty(description)
                || StringUtils.isEmpty(cronExpression)){
            throw new XTaskException("param must not be empty");
        }
        if(!CronExpression.isValidExpression(cronExpression)){
            logger.error(String.format("cronExpression error:%s",cronExpression));
            throw new XTaskException(String.format("cronExpression error:%s",cronExpression));
        }

        //TODO:检查 ServersTaskPath_Pattern 下 有没有至少一条server 在线

        TaskInfo info = new TaskInfo();
        info.setJobName(String.format("%s#%s",className,methodName));
        info.setJobGroup(appName);
        info.setJobDescription(description);
        info.setCronExpression(cronExpression);
        info.setCreateTime(DateFormatUtils.format(new Date(),DATETIME_YYYYMMDDHHMMSS_FORMAT));

        return this.addNewTaskItem(appName,info);
    }

    private boolean addNewTaskItem(String appName, TaskInfo item) throws Exception {
        String path = String.format(EnableTaskPath_Pattern,appName)+"/"+item.getJobName();
        zkBasicService.createNode(path, JSON.toJSONString(item));
        quartzService.addJob(item);
        return true;
    }

}
