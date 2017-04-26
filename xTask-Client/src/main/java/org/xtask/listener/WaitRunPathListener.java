package org.xtask.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhxy on 17/4/21.
 */
@Component
public class WaitRunPathListener implements PathChildrenCacheListener {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        {
            logger.info(event.toString());

            String nodeName =null;
            String dataStr = null;
            if(event.getData() !=null){
                nodeName = ZKPaths.getNodeFromPath(event.getData().getPath());
                dataStr = new String(event.getData().getData());
            }

            switch (event.getType()) {
                case CHILD_ADDED: {
                    logger.info("Node added: " + nodeName + " data " + dataStr);
                    break;
                }
                case CHILD_UPDATED: {
                    logger.info("Node changed: " + nodeName + " data " + dataStr);
                    break;
                }
                case CHILD_REMOVED: {
                    logger.info("Node removed: " + nodeName + " data " + dataStr);
                    break;
                }
            }
        }
    }
}
