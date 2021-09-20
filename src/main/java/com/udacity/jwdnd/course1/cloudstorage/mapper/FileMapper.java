package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from files where userId = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("select * from files where fileName = #{fileName}")
    File get(String fileName);

    @Insert("insert into files (fileName, contentType, fileSize, fileData, userId) values (#{fileName},#{contentType},#{fileSize},#{fileData},#{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "fileId", keyProperty = "fileId" )
    int insert(File file);

    @Update("update files set contentType = #{contentType}, fileSize = #{fileSize},fileData = #{fileData} where fileName = #{fileName}")
    int update(Integer fileId, String fileName, String contentType, long fileSize, byte[] fileData);

    @Delete("delete from files where fileId = #{id}")
    void delete(Integer id);
}
