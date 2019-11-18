package com.javamaster.repos;

import com.javamaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByUsername(String username);
}
