package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.io.InputStream;
import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from files where userId = #{userId}")
    List<File> getAll(Integer userId);

    @Select("select * from files where filename = #{filename}")
    File get(String filename);

    @Insert("insert into files (fileName, contentType, fileSize, userId, fileData) values (#{fileName},#{contentType},#{fileSize},#{fileData},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId" )
    int insert(File file);

    @Update("update files set contentType = #{contentType}, fileSize = #{fileSize},fileData = #{fileData} where fileName = #{fileName}")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int update(String fileName, String contentType, long fileSize, InputStream fileData);

    @Delete("delete from files where fileName = #{fileName}")
    void delete(String fileName);
}
