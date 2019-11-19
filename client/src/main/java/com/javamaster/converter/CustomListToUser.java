package com.javamaster.converter;

import com.javamaster.model.Role;
import com.javamaster.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CustomListToUser {
    public CustomListToUser() {
    }


    public List<User> convert(List customList) {
        List<User> users = new ArrayList<>();
        for (Object o : customList) {
            User user = convertToUser((LinkedHashMap) o);
            users.add(user);
        }
        return users;
    }

    public User convertToUser(LinkedHashMap users) {
        User user = new User();
        String id = users.get("id").toString();
        user.setId(Long.parseLong(id));
        user.setLogin((String) users.get("username"));
        user.setPassword((String) users.get("password"));
        List roleList = (List) users.get("roles");
        List<Role> roles = new ArrayList<>();

        for (Object o : roleList) {
            Role role = findRoleByName((LinkedHashMap) o);
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }

    private Role findRoleByName(LinkedHashMap roles) {
        Role role = new Role();
        role.setRoleName(roles.get("roleName").toString());
        String roleId = roles.get("id").toString();
        role.setId(Long.parseLong(roleId));
        return role;
    }

    public List<Role> allRoles(List listRoles) {
        List<Role> roles = new ArrayList<>();
        for (Object o : listRoles) {
            Role role = findRoleByName((LinkedHashMap) o);
            roles.add(role);
        }
        return roles;
    }
}
