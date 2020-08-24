package com.mb.spring.blogproject.dao;

import com.mb.spring.blogproject.model.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewUserRepository extends JpaRepository<NewUser, Integer> {
    NewUser findByEmail(String email);
}