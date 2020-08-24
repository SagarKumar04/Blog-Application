package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.BlogRepository;
import com.mb.spring.blogproject.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public Post addNewBlog(Post blog) {
        Post blogObject;

        blogObject = blogRepository.save(blog);

        return blogObject;
    }

    public List<Post> getAllBlogs() {
        List<Post> allBlogs;

        allBlogs = blogRepository.findAll();

        return allBlogs;
    }

    public Optional<Post> findById(int id) {
        Optional<Post> blog;

        blog = blogRepository.findById(id);

        return blog;
    }

    public void deleteById(int id) {
        blogRepository.deleteById(id);
    }

    public Page<Post> getAllBlogs(Pageable pageable) {
        Page<Post> allBlogs;

        allBlogs = blogRepository.findAll(pageable);

        return allBlogs;
    }
}
