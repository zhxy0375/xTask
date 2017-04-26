package org.xtask.controller;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xtask.service.MainBusinessService;
import org.xtask.service.ZkBasicService;
import org.xtask.spi.TaskManageSpi;

/**
 * Created by zhxy on 16/12/20.
 */
@RestController
public class TaskManageController implements TaskManageSpi {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${from}")
    private String from;

    @Autowired
    private ZkBasicService zkBasicService;
    @Autowired
    private MainBusinessService mainBusinessService;

    @Override
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        logger.info("a:{},b:{}",a,b);
        return a+b;
    }

    @Override
    public boolean createNode(@RequestParam(value = "nodeName") String nodeName, @RequestParam(value = "value") String value) {
        return zkBasicService.createNode(nodeName,value, CreateMode.PERSISTENT);
    }

    //http://localhost:8705/task?appName=fangyuan&className=org.xtask.service.TestTaskItemService&description=test&methodName=runTask&cronExpression=10%20*%20*%20?%20*%20*
    @Override
    public boolean addTask(@RequestParam(value = "appName") String appName,
                           @RequestParam(value = "className") String className,
                           @RequestParam(value = "methodName") String methodName,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        return mainBusinessService.addNewTaskItem(appName,className,methodName,description,cronExpression);
    }

    @Override
    public String test() {
        logger.info("from:{}",from);
        return from;
    }
}
