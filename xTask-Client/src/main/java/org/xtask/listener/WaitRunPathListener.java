package org.xtask.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhxy on 17/4/21.
 */
public class WaitRunPathListener implements PathChildrenCacheListener {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        {
            logger.info(event.getType().toString());
            String nodeName = ZKPaths.getNodeFromPath(event.getData().getPath());
            byte[] data = event.getData().getData();
            String dataStr = new String(data);
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
