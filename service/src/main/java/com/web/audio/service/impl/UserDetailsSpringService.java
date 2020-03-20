package com.web.audio.service.impl;

import com.web.audio.dao.UserDao;
import com.web.audio.entity.User;
import com.web.audio.entity.field.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsSpringService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<User> optionalUser = userDao.findUserByLogin(login);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + login + "' not found");
        }
        User user = optionalUser.get();
        Role role = user.getRole();

        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(role.name()));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getLogin(),
                        user.getPassword(),
                        roles);

        return userDetails;
    }
}
