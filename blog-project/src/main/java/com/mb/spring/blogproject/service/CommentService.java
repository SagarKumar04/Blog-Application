package com.mb.spring.blogproject.service;

import com.mb.spring.blogproject.dao.CommentRepository;
import com.mb.spring.blogproject.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment addNewComment(Comment comment) {
        Comment commentObject;

        commentObject = commentRepository.save(comment);

        return commentObject;
    }

    public List<Comment> findByPostId(int id) {
        List<Comment> commentList;

        commentList = commentRepository.findByPostId(id);

        return commentList;
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    public Comment findById(int id) {
        Optional<Comment> commentData;
        Comment comment;

        commentData = commentRepository.findById(id);
        comment = commentData.get();

        return comment;
    }
}
