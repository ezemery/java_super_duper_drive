package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userId ){
        return noteMapper.getAll(userId);
    }

    public Note getNote(String noteTitle ){
        return noteMapper.get(noteTitle);
    }

    public int createNote(Note note, Integer userId){
        SecureRandom random = new SecureRandom();
        int int_random = random.nextInt(Integer.MAX_VALUE);
        return noteMapper.insert( new Note(int_random, note.getNoteTitle(), note.getNoteDescription(), userId));
    }

    public int updateNote(Note note){
        return noteMapper.update(note.getNoteTitle(),note.getNoteDescription());
    }

    public void deleteNote(Note note){
         noteMapper.delete(note.getNoteTitle());
    }
}
