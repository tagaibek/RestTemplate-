package com.javamaster.converters;

import com.javamaster.model.Role;
import com.javamaster.model.User;
import com.javamaster.repos.RoleRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class MapToUserConverter {
    private final RoleRepo roleRepo;

    public MapToUserConverter(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public User convert(LinkedHashMap linkedHashMap) {
        User user = new User();
        if (linkedHashMap.get("id") != null){
            String id = linkedHashMap.get("id").toString();
            user.setId(Long.parseLong(id));
        }
        user.setLogin(linkedHashMap.get("username").toString());
        user.setPassword(linkedHashMap.get("password").toString());

        List<Role> roleList = new ArrayList<>();
        List list = (List) linkedHashMap.get("roles");
        for (Object o: list) {
            Role role = addRole((LinkedHashMap ) o);
            roleList.add(role);
        }
        user.setRoles(roleList);
        return user;
    }

    private Role addRole(LinkedHashMap o) {
        Role role = new Role();
        role.setRoleName(o.get("roleName").toString());
        if (role.getRoleName().equals("ROLE_ADMIN")){
            role.setId((long) 1);
        }else  role.setId((long) 2);
        return role;
    }
}
