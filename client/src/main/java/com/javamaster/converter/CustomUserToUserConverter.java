package com.javamaster.converter;


import com.javamaster.model.CustomUser;
import com.javamaster.model.Role;
import com.javamaster.model.User;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserToUserConverter {

    public CustomUserToUserConverter() {
    }

    public User convert(CustomUser customUser, List<Role> roleArray) {
        User user = new User();

        if (customUser.getId()!= null && !customUser.getId().equals("0") && !customUser.getId().isEmpty()) {
            user.setId(Long.parseLong(customUser.getId()));
        }
        user.setLogin(customUser.getUsername());
        user.setPassword(customUser.getPassword());
        List<Role> roles = new ArrayList<>();
        for (String  roleName : customUser.getRoles()) {
            Role role1 = addRole(roleName,roleArray);
            if (role1 != null){
                roles.add(role1);
            }
        }
        if (!roles.isEmpty()){
            user.setRoles(roles);
        }
        return user;
    }
    private Role addRole(String roleName, List<Role> roleArray) {
        for (Role role : roleArray ) {
            if (roleName.equals(role.getRoleName())){
                return role;
            }
        }
        return null;
    }
}

