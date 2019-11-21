package com.javamaster.controllers;


import com.javamaster.model.User;
import com.javamaster.sevice.ApiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(apiService.users());
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody  User user) {
        User userNew = apiService.user(user);
        if (userNew == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userNew);
        }
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        User user = apiService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(user);
        }
    }

    @PutMapping(path = "/user/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update( @RequestBody User user) {
        User userUpd = apiService.update(user);
        if (userUpd == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userUpd);
        }
    }

    @DeleteMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            apiService.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "User successful deleted."));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}