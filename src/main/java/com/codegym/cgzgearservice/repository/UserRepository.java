package com.codegym.cgzgearservice.repository;


import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    User findUserById(Long userId);

    List<User> findByIsDeletedTrue();

    List<User> findByIsDeletedFalse();
}
