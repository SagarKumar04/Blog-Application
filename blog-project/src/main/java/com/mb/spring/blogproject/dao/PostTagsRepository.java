package com.mb.spring.blogproject.dao;

import com.mb.spring.blogproject.model.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostTagsRepository extends JpaRepository<PostTags, Integer> {
    //@Query("SELECT pt FROM PostTags pt WHERE pt.postId = ?1")
    List<PostTags> findByPostId(int id);
}
