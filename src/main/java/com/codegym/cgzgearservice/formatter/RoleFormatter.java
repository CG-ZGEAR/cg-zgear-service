package com.codegym.cgzgearservice.formatter;

import com.codegym.cgzgearservice.dto.RoleDTO;
import com.codegym.cgzgearservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class RoleFormatter implements Formatter<RoleDTO> {

    private final RoleService roleService;

    @Autowired
    public RoleFormatter(RoleService roleService) {
        this.roleService = roleService;
    }
    @Override
    public RoleDTO parse(String text, Locale locale) throws ParseException {
        Optional<RoleDTO> roleDto = roleService.findById(Long.parseLong(text));
        return roleDto.orElse(null);
    }

    @Override
    public String print(RoleDTO object, Locale locale) {
        return "[" + object.getId() + ", "
                + object.getName() + ", "
                + "]";
    }
}
