package com.mb.spring.blogproject.security;

import com.mb.spring.blogproject.model.NewUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {
    private NewUser newUser;

    public UserPrincipal(NewUser newUser) {
        this.newUser = newUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities;

        authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("AUTHOR"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return authorities;
    }

    @Override
    public String getPassword() {
        String password;

        password = newUser.getPassword();

        return password;
    }

    @Override
    public String getUsername() {
        String username;

        username = newUser.getEmail();

        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
