package com.codegym.cgzgearservice.service.impl;

import com.codegym.cgzgearservice.entitiy.user.User;
import com.codegym.cgzgearservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;


    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findUserByUsername(username);
        List<String> roles;
        if (user == null) {
            user = userRepo.findUserByUsername(username);
            roles = userRepo.findRolesNamesByUsername(username);
        } else {
            roles = userRepo.findRolesNamesByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + "was not found in database!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            grantedAuthorities.add(authority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}