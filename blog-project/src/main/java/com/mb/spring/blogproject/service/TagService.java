package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.TagRepository;
import com.mb.spring.blogproject.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public void addNewTag(Tags tags) {
        tagRepository.save(tags);
    }

    public Tags getTag(String tagName) {
        Tags tagsList;

        tagsList = tagRepository.findByName(tagName);

        return tagsList;
    }
}
