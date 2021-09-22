package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public String getSignUp(){
        return "signup";
    }

    @PostMapping()
    public String signUpUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUserName())){
            signupError = "The username already exists.";
        }

        if(signupError == null){
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0){
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if(signupError == null){
            redirectAttributes.addAttribute("signupSuccess",true);
        }else{
            redirectAttributes.addAttribute("signupError", signupError);
            return "redirect:/signup";
        }

        return "redirect:/login";
    }
}
