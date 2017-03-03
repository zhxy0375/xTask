package org.xtask.service;

import org.springframework.stereotype.Service;
import org.xtask.bean.TaskResult;
import org.xtask.tool.IPTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zhxy on 16/12/20.
 */
@Service
public class ReflectTaskItemService {


    public TaskResult invokeTargetMethod(String className,String methodName) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class<?> cl = Class.forName(className);
        Object bean = cl.newInstance();

        Method method = cl.getMethod(methodName,null);
        TaskResult taskResult = new TaskResult();
        taskResult.setStartTime(System.currentTimeMillis());
//        System.out.println("--------1111");
        try {
            method.invoke(bean,null);
            taskResult.setStatus(true);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            taskResult.setStatus(false);
            StackTraceElement st = t.getStackTrace()[0];
            taskResult.setErrorMessage(t.getMessage()+"#"+st.toString());
        }
        taskResult.setIp(Arrays.toString(IPTool.getLocalIp().toArray()));
        taskResult.setEndTime(System.currentTimeMillis());
//        System.out.println("--------2222");
        return taskResult ;
    }

    public void testFlect () throws Exception {
        System.out.println("--------start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("--------end");
        throw new Exception("-------");
    }

    public static void main(String[] args) {
        ReflectTaskItemService ob  = new ReflectTaskItemService();
        try {
            TaskResult result = ob.invokeTargetMethod(ReflectTaskItemService.class.getCanonicalName(), "testFlect");
            System.out.println(result.getErrorMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
