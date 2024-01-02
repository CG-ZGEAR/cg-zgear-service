package com.codegym.cgzgearservice.service.impl;


import com.codegym.cgzgearservice.dto.RoleDTO;
import com.codegym.cgzgearservice.model.entitiy.user.Role;
import com.codegym.cgzgearservice.repository.RoleRepository;
import com.codegym.cgzgearservice.service.RoleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@ComponentScan(basePackageClasses = ModelMapper.class)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Iterable<RoleDTO> findAll() {
        Iterable<Role> entities = roleRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDTO> findById(Long id) {
        Role entity = roleRepository.findById(id).orElse(null);
        return Optional.ofNullable(modelMapper.map(entity, RoleDTO.class));
    }

    @Override
    public void save(RoleDTO roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }
}
