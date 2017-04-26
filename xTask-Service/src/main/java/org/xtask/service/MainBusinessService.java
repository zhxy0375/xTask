package org.xtask.service;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.zookeeper.CreateMode;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xtask.tool.XTaskException;
import org.xtask.bean.TaskInfo;

import java.text.ParseException;
import java.util.Date;

import static org.xtask.common.IConst.DATETIME_YYYYMMDDHHMMSS_FORMAT;
import static org.xtask.common.IConst.EnableTaskPath_Pattern;

/**
 * Created by zhxy on 16/12/19.
 */
@Service
public class MainBusinessService {


    @Autowired
    private ZkBasicService zkBasicService;
    @Autowired
    private QuartzService quartzService;

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());


    public boolean addNewTaskItem(String appName,String className,String methodName,String description,String cronExpression) throws Exception {

        logger.info("appName:{}, className:{}, methodName:{}, description:{}, cronExpression:{}", appName, className, methodName, description, cronExpression);
        if(StringUtils.isEmpty(appName)
                || StringUtils.isEmpty(className)
                || StringUtils.isEmpty(methodName)
                || StringUtils.isEmpty(description)
                || StringUtils.isEmpty(cronExpression)){
            throw new XTaskException("param must not be empty");
        }

        try {
            CronExpression.validateExpression(cronExpression);
        }catch (Exception e){
            logger.error(String.format("cronExpression error:%s",cronExpression));
            throw new XTaskException(String.format("cronExpression error:%s",cronExpression),e);
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
        zkBasicService.createNode(path, JSON.toJSONString(item), CreateMode.PERSISTENT);
        quartzService.addJob(item);
        return true;
    }

    public static void main(String[] args) {
//        可是，在后面的例子中并没有明确说明这个问号到底起什么作用，于是我编程试验了一下，得出以下结论：
//        1、如官方文档解释的那样，问号(?)的作用是指明该字段‘没有特定的值’；
//        2、星号(*)和其它值，比如数字，都是给该字段指明特定的值，只不过用星号(*)代表所有可能值；
//        3、cronExpression对日期和星期字段的处理规则是它们必须互斥，即只能且必须有一个字段有特定的值，另一个字段必须是‘没有特定的值’；
//        4、问号(?)就是用来对日期和星期字段做互斥的。
        try {
//            CronExpression.validateExpression("0 45 11 * * *");
            CronExpression.validateExpression("0 45 11 ? * *");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

}
