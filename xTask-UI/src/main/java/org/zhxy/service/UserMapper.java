package org.zhxy.service;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.zhxy.model.User;

@Mapper
@Component
@CacheConfig(cacheNames = "users")
public interface UserMapper {

    @Cacheable(key = "#p0")
    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @CachePut(key = "#p0")
    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @CachePut(key = "#p0.name")
    @Update("UPDATE USER SET AGE=#{age} WHERE NAME=#{name}")
    int save(User user);

    @CacheEvict(allEntries = true)
    @Delete("Delete from USER")
    int delete();
}