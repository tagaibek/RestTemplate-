package com.javamaster.controllers;

import com.javamaster.converter.CustomListToUser;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RoleRepo roleRepo;

    private final RestTemplate restTemplate;

    private final String API_URL = "http://localhost:8080/api/";

    public AdminController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication("admin", "admin")
                .build();
    }

    @RequestMapping(value = {"", "/users"}, method = RequestMethod.GET)
    public String listUsers(Model model ) {
        List users = restTemplate.getForObject(API_URL + "users", List.class, new HashMap<>());
        CustomListToUser customListToUser = new CustomListToUser(roleRepo);
        List<User> listUsers = customListToUser.convert( users);
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("roleList", roleRepo.findAll());
        return "browse";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, @RequestParam List<String> searchValues) {
        Map<String, Object> map = new HashMap<>();
        List<Role> roles = new ArrayList<>();
        for (String s: searchValues) {
            Role role = roleRepo.getRoleByRoleName(s);
            roles.add(role);
        }
        user.setRoles(roles);
        map.put("customUser", user);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> request = new HttpEntity<>(map, headers);
        restTemplate.postForObject(API_URL + "user", request, List.class);
        return "redirect:/admin/users";
    }

    @RequestMapping("/users/remove/{id}")
    public String removeUser(@PathVariable("id") long id) {
        Map < String, Long > params = new HashMap < >();
        params.put("id", id);
        restTemplate.delete(API_URL + "user/{id}", params);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/edit/{id}")
    public String edit(@PathVariable("id") long id, @ModelAttribute User user, @RequestParam List<String> searchValues) {
        user.setId(id);
        for (String roleStr : searchValues) {
            Role role = roleRepo.getRoleByRoleName(roleStr);
            user.getRoles().add(role);
        }
        Map<String, Object> map = new HashMap<>();user.setId(id);
        map.put("user", user);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map> request = new HttpEntity<>(map, headers);

        restTemplate.put(API_URL + "user/update", request);
        return "redirect:/admin/users";
    }
}

