package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.entitiy.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
