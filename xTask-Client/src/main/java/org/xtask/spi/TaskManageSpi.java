package org.xtask.spi;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhxy on 17/2/20.
 */
@FeignClient("xtask-service")
public interface TaskManageSpi {
    @RequestMapping(method = RequestMethod.GET, value = "/add")
    Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

    /**
     * 创建Node
     * @param nodeName  Path must start with /
     * @param value
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/node")
    boolean createNode(@RequestParam(value = "nodeName") String nodeName, @RequestParam(value = "value") String value);

    /**
     *
     * @param appName
     * @param className
     * @param methodName
     * @param description
     * @param cronExpression
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/task")
    boolean addTask(@RequestParam(value = "appName") String appName,
                    @RequestParam(value = "className") String className,
                    @RequestParam(value = "methodName") String methodName,
                    @RequestParam(value = "description") String description,
                    @RequestParam(value = "cronExpression") String cronExpression) throws Exception;


    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
