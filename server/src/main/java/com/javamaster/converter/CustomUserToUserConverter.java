package com.javamaster.converter;


import com.javamaster.model.Role;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CustomUserToUserConverter {

    private final RoleRepo roleRepo;

    public CustomUserToUserConverter(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public User convert(LinkedHashMap linkedHashMap ) {
        User user = new User();

        if (linkedHashMap.get("id") != null) {
            String id = linkedHashMap.get("id").toString();
            user.setId(Long.parseLong(id));
        }
        user.setLogin((String) linkedHashMap.get("username"));
        user.setPassword((String) linkedHashMap.get("password"));
        List list = (List) linkedHashMap.get("roles");

        List<Role> roles = new ArrayList<>();
        for (Object o : list) {
            LinkedHashMap hashMap = (LinkedHashMap) o;
            Role role = roleRepo.getRoleByRoleName(hashMap.get("roleName").toString());
            roles.add(role);
        }
        user.setRoles(roles);

        return user;
    }
}
