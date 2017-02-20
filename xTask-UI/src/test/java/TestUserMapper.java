import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhxy.Application;
import org.zhxy.config.Properties;
import org.zhxy.model.User;
import org.zhxy.service.UserMapper;

/**
 * Created by zhxy on 17/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TestUserMapper {

    private static final Log log = LogFactory.getLog(TestUserMapper.class);
    @Autowired
    private UserMapper userMapper;
    @Before
    public void setUp(){
        userMapper.delete();
    }

    @Test
    @Rollback
    public void findByName() throws Exception {
        userMapper.insert("AAA", 20);
        User u = userMapper.findByName("AAA");
        Assert.assertEquals(20, u.getAge().intValue());
    }

    @Test
    public void testCache() throws Exception {
        userMapper.insert("AAA", 10);
        log.info("第一次查询：start" );
        User u1 = userMapper.findByName("AAA");
        log.info("第一次查询：" + u1.getAge());

        log.info("第二次查询：start" );
        User u2 = userMapper.findByName("AAA");
        log.info("第二次查询：" + u2.getAge());

        u1.setAge(20);
        userMapper.save(u1);
        log.info("第三次查询：start" );
        User u3 = userMapper.findByName("AAA");
        log.info("第三次查询：" + u3.getAge());

    }
}
