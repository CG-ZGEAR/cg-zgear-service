package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.user.User;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    User findUserById(Long userId);
}
