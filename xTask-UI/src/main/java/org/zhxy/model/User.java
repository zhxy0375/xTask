package org.zhxy.model;


import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private Date createtime;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    //testCache  :
//    org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.executor.ExecutorException: No constructor found in org.zhxy.model.User matching [java.math.BigInteger, java.lang.String, java.sql.Timestamp, java.lang.Integer]
//    public User(Long id, String name,  Date createtime,Integer age) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.createtime = createtime;
//    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
