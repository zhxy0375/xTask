package org.zhxy.controller;
/**
 * Created by zhxy on 17/1/13.
 */
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.zhxy.framework.MyException;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        return "Hello World";
    }

    @RequestMapping("/exception")
    public String hello() throws Exception {
        throw new Exception("发生错误");
    }

    @RequestMapping("/json")
    public String json() throws MyException {
        throw new MyException("发生错误2");
    }

//    Spring Boot建议使用这些模板引擎，避免使用JSP，若一定要使用JSP将无法实现Spring Boot的多种特性，具体可见后文：支持JSP的配置
//    可以看到Thymeleaf主要以属性的方式加入到html标签中，浏览器在解析html时，当检查到没有的属性时候会忽略，所以Thymeleaf的模板可以通过浏览器直接打开展现，这样非常有利于前后端的分离。
//    在Spring Boot中使用Thymeleaf，只需要引入下面依赖，并在默认的模板路径src/main/resources/templates下编写模板文件即可完成。

    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

}
