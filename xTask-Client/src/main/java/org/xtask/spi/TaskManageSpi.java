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

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
