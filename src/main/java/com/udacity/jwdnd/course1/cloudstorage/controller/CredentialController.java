package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    @Autowired
    public CredentialController(UserService userService, CredentialService credentialService){
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){

        try{
            credentialService.deleteCredential(id);
            redirectAttributes.addAttribute("success", true);
        }catch(Exception e) {
            redirectAttributes.addAttribute("error", e);
        }
        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String editCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes){
        String editCredentialError = null;

        if(editCredentialError == null){
            int rowsAdded = credentialService.updateCredential(credential);
            if(rowsAdded < 0){
                editCredentialError = "Something went wrong in editing the credential";
            }
        }

        if(editCredentialError == null){
            redirectAttributes.addAttribute("success", true);
        }else{
            redirectAttributes.addAttribute("error", editCredentialError);
        }

        return "redirect:/home";
    }

    @PostMapping()
    public String postACredential(Authentication authentication, @ModelAttribute Credential credential, RedirectAttributes redirectAttributes){
        String createCredentialError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(!credentialService.isUrlAvailable(credential.getUrl())){
            createCredentialError = "The url already exists.";
        }

        if(createCredentialError == null){
            int rowsAdded = credentialService.createCredential(credential, user.getUserId());
            if(rowsAdded < 0){
                createCredentialError = "Something went wrong in creating the credential";
            }
        }

        if(createCredentialError == null){
            redirectAttributes.addAttribute("success", true);
        }else{
            redirectAttributes.addAttribute("error", createCredentialError);
        }

        return "redirect:/home";
    }
}
