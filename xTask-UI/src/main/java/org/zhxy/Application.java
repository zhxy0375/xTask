package org.zhxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zhxy on 16/12/16.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }
}


//bug-1:
//2017-01-17 13:28:19.078  WARN 54350 --- [           main] ationConfigEmbeddedWebApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanDefinitionStoreException: Failed to read candidate component class: URL [jar:file:/Users/zhxy/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/1.3.5.RELEASE/spring-boot-autoconfigure-1.3.5.RELEASE.jar!/org/springframework/boot/autoconfigure/jdbc/DataSourceAutoConfiguration$JdbcTemplateConfiguration.class]; nested exception is java.lang.IllegalStateException: Could not evaluate condition on org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration$JdbcTemplateConfiguration due to org/springframework/dao/DataAccessException not found. Make sure your own configuration does not rely on that class. This can also happen if you are @ComponentScanning a springframework package (e.g. if you put a @ComponentScan in the default package by mistake)
//        2017-01-17 13:28:19.090 ERROR 54350 --- [           main] o.s.boot.SpringApplication               : Application startup failed
//
//fix-1：是因为application.java 文件不能直接放在main/java文件夹下，必须要建一个包把他放进去

