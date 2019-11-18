package com.javamaster.controller;

import com.javamaster.converter.CustomUserToUserConverter;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;
import com.javamaster.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;


    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(optionalUser.get());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @PostMapping(path = "/user")
    public ResponseEntity<List<User>> create(@RequestBody Map<String, Object> map) {
        CustomUserToUserConverter customUserToUserConverter = new CustomUserToUserConverter(roleRepo);

        LinkedHashMap hashMap = (LinkedHashMap) map.get("customUser");
        User user = customUserToUserConverter.convert(hashMap );

        User userNew = userRepo.save(user);
        if (userNew == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<User> list = new ArrayList<>();
            list.add(userNew);
            return ResponseEntity.ok(list);
        }
    }

    @PutMapping(path = "/user/update")
    public ResponseEntity<List<User>> update(@RequestBody Map<String, Object> map) {
        CustomUserToUserConverter customUserToUserConverter = new CustomUserToUserConverter(roleRepo);
        LinkedHashMap hashMap = (LinkedHashMap) map.get("user");
        User user = customUserToUserConverter.convert(hashMap );
        User userUpd = userRepo.save(user);
        if (userUpd == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<User> list = new ArrayList<>();
            list.add(userUpd);
            return ResponseEntity.ok(list);
        }
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        try {
            userRepo.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "User successful deleted."));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
