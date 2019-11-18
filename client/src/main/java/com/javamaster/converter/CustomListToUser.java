package com.javamaster.converter;

import com.javamaster.model.Role;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CustomListToUser {
    private final RoleRepo roleRepo;

    public CustomListToUser(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<User> convert(List customList) {
        List<User> users = new ArrayList<>();
        for (Object o : customList) {
            User user = convertToUser((LinkedHashMap) o);
            users.add(user);
        }
        return users;
    }

    private User convertToUser(LinkedHashMap linkedHashMap) {
        User user = new User();
        String id = linkedHashMap.get("id").toString();
        user.setId(Long.parseLong(id));
        user.setLogin((String) linkedHashMap.get("username"));
        user.setPassword((String) linkedHashMap.get("password"));
        List roleList = (List) linkedHashMap.get("roles");
        List<Role> roles = new ArrayList<>();

        for (Object o : roleList) {
            String name = findRoleName((LinkedHashMap) o);
            Role role = roleRepo.getRoleByRoleName(name);
            roles.add(role);
        }


        user.setRoles(roles);
        return user;
    }

    private String findRoleName(LinkedHashMap linkedHashMap) {
        return linkedHashMap.get("roleName").toString();
    }

}
