package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
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

    public Note getNote(Integer id ){
        return noteMapper.getId(id);
    }

    public int createNote(Note note, Integer userId){
        return noteMapper.insert( new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId));
    }

    public boolean isNoteAvailable(String noteTitle) {
        return noteMapper.getTitle(noteTitle) == null;
    }

    public int updateNote(Note note){
        return noteMapper.update(note.getNoteId(), note.getNoteTitle(),note.getNoteDescription());
    }

    public void deleteNote(Integer noteId){
         noteMapper.delete(noteId);
    }
}
