package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.NewUserRepository;
import com.mb.spring.blogproject.model.NewUser;
import com.mb.spring.blogproject.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlogUserDetailsService implements UserDetailsService {
    @Autowired
    private NewUserRepository newUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NewUser newUser;
        UserPrincipal userPrincipal;

        newUser = newUserRepository.findByEmail(email);
        userPrincipal = new UserPrincipal(newUser);

        return userPrincipal;
    }
}
