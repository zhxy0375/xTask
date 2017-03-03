package org.xtask.jobs;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xtask.service.MainBusinessService;
import org.xtask.service.ZkBasicService;

import java.util.Date;

/**
 * Created by zhxy on 17/3/2.
 */
public class WaitRunJob implements Job {

    @Autowired
    private ZkBasicService zkBasicService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Date fireTime = context.getScheduledFireTime();
        String fireTimeStr = DateFormatUtils.format(fireTime,MainBusinessService.DATETIME_YYYYMMDDHHMMSS_FORMAT);
        String nodeName = context.getJobDetail().getKey().getName()+String.format("#%s",fireTimeStr);

        String path = String.format(MainBusinessService.WaitRunTaskPath_Pattern,context.getJobDetail().getKey().getGroup())
                +"/"+nodeName;
        zkBasicService.createNode(path,"");
    }
}
