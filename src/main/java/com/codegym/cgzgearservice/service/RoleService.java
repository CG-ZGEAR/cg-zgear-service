package com.codegym.cgzgearservice.service;

import com.codegym.cgzgearservice.dto.RoleDTO;

import java.util.Optional;

public interface RoleService {
    Iterable<RoleDTO> findAll();
    Optional<RoleDTO> findById(Long id);
    void save(RoleDTO roleDto);
    void remove(Long id);
}
