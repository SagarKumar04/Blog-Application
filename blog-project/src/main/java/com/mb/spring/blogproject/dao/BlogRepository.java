package com.mb.spring.blogproject.dao;

import com.mb.spring.blogproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Post, Integer> {

}
