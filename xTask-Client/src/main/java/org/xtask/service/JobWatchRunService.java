package org.xtask.service;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.xtask.common.IConst;
import org.xtask.config.NeedConfig;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * Created by zhxy on 17/4/21.
 */
@Service
@Order(3)
public class JobWatchRunService {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZkBasicService zkBasicService;
    @Autowired
    private ReflectTaskItemService reflectTaskItemService;
    @Autowired
    private NeedConfig needConfig;

    @PostConstruct
    public void init() throws Exception {
        logger.info("--------------------------start--------------");
        String path ="/";
        Optional<List<String>> lstNode = Optional.of(zkBasicService.listChildren("/"));
        lstNode.ifPresent(node ->{
            logger.info("path:{},children:{}",path,node);
        });

        String serverPath = String.format(IConst.ServersTaskPath_Pattern, needConfig.getAppCode());
        logger.info("serverPath:{}",serverPath);
        zkBasicService.getClient().create().withMode(CreateMode.EPHEMERAL).forPath(serverPath);
        logger.info("-------------end------------------------------");

    }
}
