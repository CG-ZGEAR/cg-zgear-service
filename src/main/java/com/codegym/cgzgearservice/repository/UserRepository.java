package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findUserById(Long userId);
    User findUserByUsername(String username);
    User findUserByEmail(String email);

    @Query("SELECT ur.name FROM User u JOIN u.roles ur WHERE u.username = :username")
    List<String> findRolesNamesByUsername(@Param("username") String username);

    List<User> findByUsernameContainsIgnoreCase(String username);

    List<User> findByIsDeletedTrue();

    List<User> findByIsDeletedFalse();
}
