package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
