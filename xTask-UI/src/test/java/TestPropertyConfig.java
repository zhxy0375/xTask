import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhxy.Application;
import org.zhxy.config.Properties;

/**
 * Created by zhxy on 17/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestPropertyConfig {

    private static final Log log = LogFactory.getLog(TestPropertyConfig.class);
    @Autowired
    private Properties properties;

    @Test
    public void testProperty(){
        log.info("随机数测试输出：");
        log.info("随机字符串 : " + properties.getValue());
        log.info("随机int : " + properties.getNumber());
        log.info("随机long : " + properties.getBignumber());
        log.info("随机10以下 : " + properties.getTest1());
        log.info("随机10-20 : " + properties.getTest2());

        Assert.assertEquals("程序猿DD", properties.getName());
        Assert.assertEquals("Spring Boot教程", properties.getTitle());
        Assert.assertEquals("程序猿DD正在努力写《Spring Boot教程》", properties.getDesc());



    }
}
