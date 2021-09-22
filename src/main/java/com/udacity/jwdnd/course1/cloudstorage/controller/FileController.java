package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }


    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){

        try{
            fileService.delete(id);
            redirectAttributes.addAttribute("success", "File was successfully deleted");
        }catch(Exception e) {
            redirectAttributes.addAttribute("error", e);
        }
        return "redirect:/home";
    }

    @PostMapping()
    public String handleFileUpload(Authentication authentication,@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) {
        String createFileError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(!fileService.isFileAvailable(file.getOriginalFilename())){
            createFileError = "The file already exists.";
        }

        if(createFileError == null){
            try{
                int rowsAdded = fileService.createFile(file, user.getUserId());
            }catch (Exception e){
                createFileError = "Something went wrong in creating the file. " + e ;
            }
        }

        if(createFileError == null){
            redirectAttributes.addAttribute("success", "File was successfully uploaded");
        }else{
            redirectAttributes.addAttribute("error", createFileError);
        }

        return "redirect:/home";
    }

}
