package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.model.entitiy.user.User;
import com.codegym.cgzgearservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;


    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findUserByUsername(username);
        List<String> roles;
        if(user == null) {
            user = userRepo.findUserByUsername(username);
            roles= userRepo.findRolesNamesByUsername(username);
        } else {
            roles = userRepo.findRolesNamesByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + "was not found in database!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role: roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            grantedAuthorities.add(authority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}