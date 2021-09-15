package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping()
    public String postANote(Authentication authentication, @ModelAttribute Note note, Model model){
        String createNoteError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(createNoteError == null){
            int rowsAdded = noteService.createNote(note, user.getUserId());
            if(rowsAdded < 0){
                createNoteError = "Something went wrong in creating the note";
            }
        }

        if(createNoteError == null){
            model.addAttribute("success", true);
        }else{
            model.addAttribute("error", createNoteError);
        }
        return "result";
    }
}
