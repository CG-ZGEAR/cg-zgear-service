package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * @author ADMIN
 */
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}


