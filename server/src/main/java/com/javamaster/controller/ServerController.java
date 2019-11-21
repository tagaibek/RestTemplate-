package com.javamaster.controller;

import com.javamaster.converters.MapToUserConverter;
import com.javamaster.model.Role;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;
import com.javamaster.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class ServerController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;


    @GetMapping("/users")
    public ResponseEntity<List<User>> users() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> roles() {
        return ResponseEntity.ok(roleRepo.findAll());
    }

    @PostMapping(path = "/user")
    public ResponseEntity<List<User>> create(@RequestBody Map<String, Object> map) {
        MapToUserConverter mapToUserConverter = new MapToUserConverter(roleRepo);
        LinkedHashMap hashMap = (LinkedHashMap) map.get("customUser");
        User user = mapToUserConverter.convert(hashMap);
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
    public ResponseEntity<?> update(@RequestBody Map<String, Object> map) {
        MapToUserConverter mapToUserConverter = new MapToUserConverter(roleRepo);
        LinkedHashMap hashMap = (LinkedHashMap) map.get("user");
        User user = mapToUserConverter.convert(hashMap);
        User userUpd = userRepo.save(user);
        if (userUpd == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(Collections.singletonMap("message", "User successful updated."));
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
    @PostMapping(path = "/user/id")
    public ResponseEntity<List<User>> user(@RequestBody Map<String, Object> map) {
        String id = map.get("id").toString();
        Optional<User> optionalUser = userRepo.findById(Long.parseLong(id));
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            List<User> list = new ArrayList<>();
            list.add(optionalUser.get());
            return ResponseEntity.ok(list);
        }
    }
}
