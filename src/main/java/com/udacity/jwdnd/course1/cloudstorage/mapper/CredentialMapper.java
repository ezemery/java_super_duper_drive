package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("select * from credentials where userId = #{userId}")
    List<Credential> getAll(Integer userId);

    @Select("select * from credentials where userName = #{userName}")
    Credential get(String userName);

    @Insert("insert into credentials (userName, url, key,passWord, userId) values (#{userName},#{url},#{key},#{passWord},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(Credential credential);

    @Update("update credentials set url = #{url}, key = #{key}, passWord = #{passWord} where userName = #{userName}")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int update(String url, String key, String passWord, Integer userId);

    @Delete("delete from credentials where userName = #{userName}")
    void delete(String userName);
}
