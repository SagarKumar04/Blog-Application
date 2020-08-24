package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.PostTagsRepository;
import com.mb.spring.blogproject.model.PostTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagsService {
    @Autowired
    PostTagsRepository postTagsRepository;

    public List<PostTags> getPostTags() {
        List<PostTags> postTagsList;

        postTagsList = postTagsRepository.findAll();

        return postTagsList;
    }
}
