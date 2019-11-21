package com.javamaster.controllers;


import com.javamaster.sevice.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = {"", "/users"}, method = RequestMethod.GET)
    public String listUsers(Model model ){
        model.addAttribute("roleList", apiService.roles());
        model.addAttribute("listUsers", apiService.users());
        return "browse2";
    }
}

