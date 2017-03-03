package org.xtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by zhxy on 16/12/16.
 */
@EnableDiscoveryClient  //该注解能激活Eureka中的DiscoveryClient实现，才能实现Controller中对服务信息的输出
@SpringBootApplication
@EnableFeignClients

//注意：  application.java 文件不能直接放在main/java文件夹下，必须要建一个包把他放进去 ；otherwise: Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }
}
