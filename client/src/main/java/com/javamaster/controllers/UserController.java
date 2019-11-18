package com.javamaster.controllers;

import com.javamaster.model.User;
import com.javamaster.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @RequestMapping
    public String getUserByName(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loadUser = userRepo.findByUsername(authentication.getName());
        if (loadUser != null) {
            model.addAttribute("loadUser", loadUser);
        }
        return "user";
    }
}