package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    public final CredentialService credentialService;



    @Autowired
    public HomeController(FileService fileService, UserService userService,NoteService noteService,CredentialService credentialService){
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String listHomePageContents(Authentication authentication, Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);
        //System.out.println("fileService.getAllFiles"+ Arrays.asList(fileService.getAllUserFiles(user.getUserId())));
        model.addAttribute("files", fileService.getAllUserFiles(user.getUserId()));
        model.addAttribute("notes",noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials",credentialService.getAllCredentials(user.getUserId()));
        return "home";
    }

    @GetMapping("/files/view/{filename}")
    @ResponseBody
    public ResponseEntity serveFile(@PathVariable("filename") String filename) {

        File file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @PostMapping
    public String postHome(){
        return "home";
    }
}
