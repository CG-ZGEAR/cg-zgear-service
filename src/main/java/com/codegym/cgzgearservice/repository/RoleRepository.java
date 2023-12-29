package com.codegym.cgzgearservice.repository;

import com.codegym.cgzgearservice.model.entitiy.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
