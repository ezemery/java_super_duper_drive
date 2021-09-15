package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping()
    public String postACredential(Authentication authentication, @ModelAttribute Credential credential, Model model){
        String createCredentialError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(createCredentialError == null){
            int rowsAdded = credentialService.createCredential(credential, user.getUserId());
            if(rowsAdded < 0){
                createCredentialError = "Something went wrong in creating the credential";
            }
        }

        if(createCredentialError == null){
            model.addAttribute("success", true);
        }else{
            model.addAttribute("error", createCredentialError);
        }

        return "result";
    }
}
