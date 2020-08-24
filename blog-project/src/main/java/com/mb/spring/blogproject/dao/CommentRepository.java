package com.mb.spring.blogproject.dao;

import com.mb.spring.blogproject.model.Comment;
import com.mb.spring.blogproject.model.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(int postId);
}
