package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from notes where userId = #{userId}")
    List<Note> getAll(Integer userId);

    @Select("select * from notes where noteTitle = #{noteTitle}")
    Note getTitle(String noteTitle);

    @Select("select * from notes where noteId = #{id}")
    Note getId(Integer id);

    @Insert("insert into notes (noteTitle, noteDescription, userId) values (#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "noteId", keyProperty = "noteId")
    int insert(Note note);

    @Update("update notes set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId = #{noteId}")
    @Options(useGeneratedKeys = true, keyColumn = "noteId")
    int update(Integer noteId, String noteTitle, String noteDescription);

    @Delete("delete from notes where noteId = #{id}")
    void delete(Integer id);
}
