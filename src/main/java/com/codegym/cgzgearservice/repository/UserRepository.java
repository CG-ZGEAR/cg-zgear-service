package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    User findUserByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT ur.name FROM User u JOIN u.roles ur WHERE u.username = :username")
    List<String> findRolesNamesByUsername(@Param("username") String username);


    Page<User> findByIsDeletedTrue(Pageable pageable);

    Page<User> findByIsDeletedFalse(Pageable pageable);

    User findByEmail(String email);

    Optional<User> findByEmailAndOtpCodeAndOtpExpirationAfter(String email, String otp, LocalDateTime expirationTime);
    Page<User> findByUsernameContainingOrFullNameContainingOrEmailContaining(
            String username,
            String fullName,
            String email,
            Pageable pageable
    );
}