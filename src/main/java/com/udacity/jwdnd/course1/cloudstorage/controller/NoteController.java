package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService,UserService userService){
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){

        try{
            noteService.deleteNote(id);
            redirectAttributes.addAttribute("success", true);
        }catch(Exception e) {
            redirectAttributes.addAttribute("error", e);
        }
        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes){
        String editNoteError = null;

        if(editNoteError == null){
            int rowsAdded = noteService.updateNote(note);
            if(rowsAdded < 0){
                editNoteError = "Something went wrong in editing the note";
            }
        }

        if(editNoteError == null){
            redirectAttributes.addAttribute("success", true);
        }else{
            redirectAttributes.addAttribute("error", editNoteError);
        }

        return "redirect:/home";
    }

    @PostMapping()
    public String postANote(Authentication authentication, @ModelAttribute Note note, RedirectAttributes redirectAttributes){
        String createNoteError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(!noteService.isNoteAvailable(note.getNoteTitle())){
            createNoteError = "The note title already exists.";
        }

        if(createNoteError == null){
            int rowsAdded = noteService.createNote(note, user.getUserId());
            if(rowsAdded < 0){
                createNoteError = "Something went wrong in creating the note";
            }
        }

        if(createNoteError == null){
            redirectAttributes.addAttribute("success", true);
        }else{
            redirectAttributes.addAttribute("error", createNoteError);
        }
        return "redirect:/home";
    }
}
