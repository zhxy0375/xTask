package org.xtask.bean;

import java.io.Serializable;

/**
 * Created by zhxy on 17/3/2.
 */
//要是单例
public class ServerInfo implements Serializable {
    private final String appName ;
    private final String zkPath;

    private ServerInfo() {
        zkPath = null;
        appName = null;
    }

    private static volatile ServerInfo instance=null;

    public static ServerInfo getInstance(){
        synchronized(ServerInfo.class){
            if(instance==null){
                instance=new ServerInfo();
            }
        }
        return instance;
    }
}
