package com.mb.spring.blogproject.dao;

import com.mb.spring.blogproject.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tags, Integer> {
    Tags findByName(String tagName);
}
