package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.List;

/**
 * @author ADMIN
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRoles_Name(String roleName);
=======
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
>>>>>>> 3c010e10aec9ab88510650abf87db59638fd1117
}
