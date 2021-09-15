package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from notes where userId = #{userId}")
    List<Note> getAll(Integer userId);

    @Select("select * from notes where noteTitle = #{noteTitle}")
    Note get(String noteTitle);

    @Insert("insert into notes (noteTitle, noteDescription, userId) values (#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(Note note);

    @Update("update notes set noteDescription = #{noteDescription} where noteTitle = #{noteTitle}")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int update(String noteTitle, String noteDescription);

    @Delete("delete from notes where noteTitle = #{noteTitle}")
    void delete(String userId);
}
