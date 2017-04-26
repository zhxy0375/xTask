package org.xtask.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhxy on 17/3/1.
 */
@Configuration
public class QuartzConfig {

//    1、scheduler是一个计划调度器容器（总部），容器里面可以盛放众多的JobDetail和trigger，当容器启动后，里面的每个JobDetail都会根据trigger按部就班自动去执行。
//    2、JobDetail是一个可执行的工作，它本身可能是有状态的。
//    3、Trigger代表一个调度参数的配置，什么时候去调。
//    4、当JobDetail和Trigger在scheduler容器上注册后，形成了装配好的作业（JobDetail和Trigger所组成的一对儿），就可以伴随容器启动而调度执行了。
//    5、scheduler是个容器，容器中有一个线程池，用来并行调度执行每个作业，这样可以提高容器效率
    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    //参考：https://github.com/quartz-scheduler/quartz/tree/master/quartz-core/src/test/java/org/quartz
    /**
     * 设置quartz属性
     * @throws IOException
     * 2016年10月8日下午2:39:05
     */
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");

//        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
//        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
//        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
//        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
//        prop.put("org.quartz.jobStore.isClustered", "true");

//        prop.put("org.quartz.dataSource.quartzDataSource.driver", "com.mysql.jdbc.Driver");
//        prop.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:mysql://localhost:3306/demo-schema");
//        prop.put("org.quartz.dataSource.quartzDataSource.user", "root");
//        prop.put("org.quartz.dataSource.quartzDataSource.password", "123456");
//        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
        return prop;
    }
}
