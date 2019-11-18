package com.javamaster.repos;

import com.javamaster.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Role getRoleByRoleName(String name);
}
