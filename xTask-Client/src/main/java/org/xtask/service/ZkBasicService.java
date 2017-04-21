package org.xtask.service;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhxy on 16/12/16.
 */
@Service
@Order(1)
public class ZkBasicService {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CuratorFramework client;

    
    /**
     * 创建node
     *
     * @param nodeName
     * @param value
     * @return
     */
    public boolean createNode(String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = getClient().checkExists().forPath(nodeName);
            if (stat == null) {
                String opResult = null;
                if (Strings.isNullOrEmpty(value)) {
                    opResult = getClient().create().creatingParentsIfNeeded().forPath(nodeName);
                }
                else {
                    opResult = getClient().create().creatingParentsIfNeeded()
                                    .forPath(nodeName, value.getBytes(Charsets.UTF_8));
                }
                suc = Objects.equal(nodeName, opResult);
            }
        }
        catch (Exception e) {
            logger.error("nodeName:{},value:{},error:{}",nodeName,value,e.getMessage());
            logger.error("",e);
        }
        return suc;
    }

    /**
     * 更新节点
     *
     * @param nodeName
     * @param value
     * @return
     */
    public boolean updateNode(String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = getClient().checkExists().forPath(nodeName);
            if (stat != null) {
                Stat opResult = getClient().setData().forPath(nodeName, value.getBytes(Charsets.UTF_8));
                suc = opResult != null;
            }
        }
        catch (Exception e) {
            logger.error(String.format("nodeName:%s,value:%s"),nodeName,value,e);
        }
        return suc;
    }


    /**
     * 删除节点
     *
     * @param nodeName
     */
    public void deleteNode(String nodeName) {
        try {
            getClient().delete().deletingChildrenIfNeeded().forPath(nodeName);
        }
        catch (Exception e) {
            logger.error(String.format("nodeName:%s"),nodeName,e);
        }
    }


    /**
     * 找到指定节点下所有子节点的名称与值
     *
     * @param node
     * @return
     */
    public Map<String, String> listChildrenDetail(String node) {
        Map<String, String> map = Maps.newHashMap();
        try {
            GetChildrenBuilder childrenBuilder = getClient().getChildren();
            List<String> children = childrenBuilder.forPath(node);
            GetDataBuilder dataBuilder = getClient().getData();
            if (children != null) {
                for (String child : children) {
                    String propPath = ZKPaths.makePath(node, child);
                    map.put(child, new String(dataBuilder.forPath(propPath), Charsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            logger.error(String.format("node:%s"),node,e);
        }
        return map;
    }


    /**
     * 列出子节点的名称
     *
     * @param node
     * @return
     */
    public List<String> listChildren(String node) {
        List<String> children = Lists.newArrayList();
        try {
            GetChildrenBuilder childrenBuilder = getClient().getChildren();
            children = childrenBuilder.forPath(node);
        }
        catch (Exception e) {
            logger.error(String.format("node:%s"),node,e);
        }
        return children;
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf) throws Exception {
        if (isSelf) {
            getClient().getData().watched().forPath(node);
        }
        else {
            getClient().getChildren().watched().forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, Watcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, CuratorWatcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }

    /**
     * 可重入锁
     * @param lockPath
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    public InterProcessMutex addSharedReentrantLock(String lockPath, long time, TimeUnit unit) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        if(!lock.acquire(time,unit)){
            throw new IllegalStateException(String.format("could not get the path:%s reentrant lock",lockPath));
        }
        return lock;
    }

    public InterProcessSemaphoreMutex addSharedLock(String lockPath, long time, TimeUnit unit) throws Exception {
        InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, lockPath);
        if(!lock.acquire(time,unit)){
            throw new IllegalStateException(String.format("could not get the path:%s lock",lockPath));
        }
        return lock;
    }

    public void addPathChildrenCache(String path,boolean cacheData) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client,path,cacheData);

        cache.getListenable().addListener(
                new PathChildrenCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                        logger.info(event.getType().toString());
                        String nodeName = ZKPaths.getNodeFromPath(event.getData().getPath());
                        byte[] data = event.getData().getData();
                        String dataStr = new String(data);
                        switch (event.getType()) {
                            case CHILD_ADDED: {
                                logger.info("Node added: " + path + " data " + dataStr);
                                break;
                            }
                            case CHILD_UPDATED: {
                                logger.info("Node changed: " + path + " data " + dataStr);
                                break;
                            }
                            case CHILD_REMOVED: {
                                logger.info("Node removed: " + path + " data " + dataStr);
                                break;
                            }
                        }
                    }
                }
        );
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);//PathChildrenCache一般使用POST_INITILAIZED_MODE模式启动,RECONNECTED时会自动做rebuild操作
    }


    public void addPathChildrenCache(String path,boolean cacheData,PathChildrenCacheListener listener) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client,path,cacheData);

        cache.getListenable().addListener(listener);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);//PathChildrenCache一般使用POST_INITILAIZED_MODE模式启动,RECONNECTED时会自动做rebuild操作
    }

    /**
     * 销毁资源
     */
    public void destory() {
        if (client != null) {
            client.close();
        }
    }


    /**
     * 获取client
     *
     * @return
     */
    public CuratorFramework getClient() {
        return client;
    }


}
