package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.NewUserRepository;
import com.mb.spring.blogproject.model.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewUserService {
    @Autowired
    private NewUserRepository newUserRepository;
    NewUser newUserObject;

    public NewUser addNewUser(NewUser newUser) {
        newUserObject = newUserRepository.save(newUser);

        return newUserObject;
    }

    public NewUser getNameAndPasswordByEmail(String email) {
        newUserObject = newUserRepository.findByEmail(email);

        return newUserObject;
    }
}
