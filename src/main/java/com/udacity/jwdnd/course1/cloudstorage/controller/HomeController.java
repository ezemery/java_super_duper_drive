package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    public final CredentialService credentialService;
    public final EncryptionService encryptionService;



    @Autowired
    public HomeController(FileService fileService, UserService userService,NoteService noteService,CredentialService credentialService,EncryptionService encryptionService){
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String listHomePageContents(Authentication authentication, Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("files", fileService.getAllUserFiles(user.getUserId()));
        model.addAttribute("notes",noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials",credentialService.getAllCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/files/view/{filename}")
    @ResponseBody
    public ResponseEntity serveFile(@PathVariable("filename") String filename) {

        File file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @PostMapping
    public String postHome(){
        return "home";
    }
}
