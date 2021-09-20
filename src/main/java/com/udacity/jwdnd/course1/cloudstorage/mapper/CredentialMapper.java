package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("select * from credentials where userId = #{userId}")
    List<Credential> getAll(Integer userId);

    @Select("select * from credentials where url = #{url}")
    Credential getUrl(String url);

    @Select("select * from credentials where credentialId = #{id}")
    Credential getId(Integer id);

    @Insert("insert into credentials (userName, url, key,passWord, userId) values (#{userName},#{url},#{key},#{passWord},#{userId})")
    @Options(useGeneratedKeys = true,keyColumn = "credentialId", keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("update credentials set url = #{url}, userName = #{userName}, passWord = #{passWord} where credentialId = #{credentialId}")
    @Options(useGeneratedKeys = true,keyColumn = "credentialId")
    int update(Integer credentialId, String url,String userName, String passWord);

    @Delete("delete from credentials where credentialId = #{id}")
    void delete(Integer id);
}
