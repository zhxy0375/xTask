package org.xtask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Override
    public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        logger.info("a:{},b:{}",a,b);
        return a+b;
    }

    @Override
    public boolean createNode(@RequestParam(value = "nodeName") String nodeName, @RequestParam(value = "value") String value) {
        return zkBasicService.createNode(nodeName,value);
    }

    @Override
    public String test() {
        logger.info("from:{}",from);
        return from;
    }
}
