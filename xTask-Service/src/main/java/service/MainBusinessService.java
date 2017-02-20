package service;

import bean.TaskItem;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhxy on 16/12/19.
 */

public class MainBusinessService {
    public static final String RootPath = "/Task";
    public static final String AppName_Pattern = "/%s";
    public static final String EnableTaskPath_Pattern = RootPath+AppName_Pattern+"/enable";
    public static final String DisableTaskPath_Pattern = RootPath+AppName_Pattern+"/disable";
    public static final String RuntimeTaskPath_Pattern = RootPath+AppName_Pattern+"/runtime";
    public static final String HistoryTaskPath_Pattern = RootPath+AppName_Pattern+"/history";

    @Autowired
    private ZkBasicService zkBasicService;

    public void addNewTaskItem(String appName,String className,String methodName,String description){
        TaskItem item = new TaskItem ();
        item.setClassName(className);
        item.setMethodName(methodName);
        item.setDescription(description);
        this.addNewTaskItem(appName,item);
    }

    public void addNewTaskItem(String appName, TaskItem item){

        String path = String.format(EnableTaskPath_Pattern,appName)+"/"+item.getClassName()+"/"+item.getMethodName();

        zkBasicService.createNode(path, JSON.toJSONString(item));
    }

}
