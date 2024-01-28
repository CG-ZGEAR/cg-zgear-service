package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.RoleDTO;

import java.util.Optional;

public interface RoleService {
    Optional<RoleDTO> findById(Long id);
}
