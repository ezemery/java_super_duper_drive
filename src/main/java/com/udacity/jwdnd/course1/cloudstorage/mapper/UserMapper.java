package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users")
    List<User> getAll();

    @Select("select * from users where userName = #{userName}")
    User get(String userName);

    @Insert("insert into users (userName,salt, passWord,firstName, lastName) values (#{userName},#{salt},#{passWord},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Update("update users set userName = #{userName}, passWord = #{passWord} where userId = #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int update(String username, String password, Integer userId);

    @Delete("delete from users where userId = #{userId}")
    void delete(Integer userId);
}
