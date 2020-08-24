package com.mb.spring.blogproject.controller;

import com.mb.spring.blogproject.model.*;
import com.mb.spring.blogproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {
    private String name;

    @Autowired
    NewUserService newUserService;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public ModelAndView getMainPage() {
        ModelAndView mv;

        mv = getOtherPage(1);

        return mv;
    }

    @GetMapping("/page")
    public ModelAndView getOtherPage(@ModelAttribute("pageNumber") int currentPage) {
        ModelAndView mv;
        Pageable pageable;
        Page<Post> allBlogs;
        List<Post> postList;
        int totalPages;
        long totalPosts;

        pageable = PageRequest.of(currentPage - 1, 3, Sort.Direction.DESC, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);

        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();


        mv = new ModelAndView();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/loginSignup")
    public ModelAndView getLoginOrSignUpPage() {
        ModelAndView mv = new ModelAndView();

        NewUser newUser = new NewUser();
        ExistingUser existingUser = new ExistingUser();

        mv.addObject("newUser", newUser);
        mv.addObject("existingUser", existingUser);
        mv.setViewName("login-signup");
        return mv;
    }

    @PostMapping("/validateLogin")
    public ModelAndView validateLogin(@ModelAttribute("existingUser") ExistingUser existingUser) {
        String email, passwordEnteredByUser, passwordStoredInDatabase;
        ModelAndView mv;
        NewUser newUser;

        email = existingUser.getEmail();
        passwordEnteredByUser = existingUser.getPassword();

        newUser = newUserService.getNameAndPasswordByEmail(email);

        name = newUser.getName();
        passwordStoredInDatabase = newUser.getPassword();

        mv = new ModelAndView();
        mv.addObject("existingUser", existingUser);

        if(passwordEnteredByUser.equals(passwordStoredInDatabase)) {
            Page<Post> allBlogs;
            List<Post> postList;
            Pageable pageable;
            int totalPages, currentPage;
            long totalPosts;

            currentPage = 1;

            pageable = PageRequest.of(currentPage - 1, 3, Sort.Direction.DESC, "createdAt");
            allBlogs = blogService.getAllBlogs(pageable);
            postList = allBlogs.getContent();

            totalPages = allBlogs.getTotalPages();
            totalPosts = allBlogs.getTotalElements();

            mv = new ModelAndView();

            mv.addObject("currentPage",currentPage);
            mv.addObject("totalPages", totalPages);
            mv.addObject("totalPosts", totalPosts);
            mv.addObject("postList", postList);

            mv.addObject("userName", name);
            mv.setViewName("home");
        }
        else {
            mv.addObject("newUser", newUser);
            mv.addObject("failureMessage", "Email or password wrong");
            mv.setViewName("login-signup");
        }
        return mv;
    }

    @GetMapping("/pageAfterLogin")
    public ModelAndView getOtherPageAfterLogin(@ModelAttribute("pageNumber") int currentPage) {
        ModelAndView mv;
        Pageable pageable;
        Page<Post> allBlogs;
        List<Post> postList;
        int totalPages;
        long totalPosts;

        pageable = PageRequest.of(currentPage - 1, 3, Sort.Direction.DESC, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);

        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();


        mv = new ModelAndView();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);
        mv.addObject("userName", name);

        mv.setViewName("home");
        return mv;
    }

    @PostMapping("/addNewUser")
    public ModelAndView addNewUser(@ModelAttribute("newUser") NewUser newUser) {
        ModelAndView mv;
        NewUser nu;

        mv = new ModelAndView("login-signup");
        nu = newUserService.addNewUser(newUser);

        mv.addObject("newUser", newUser);
        mv.addObject("existingUser", new ExistingUser());
        return mv;
    }

    @GetMapping("/newBlog")
    public ModelAndView getNewBlogView() {
        ModelAndView mv;
        Post post;

        mv = new ModelAndView();
        post = new Post( );

        if(name == null) {
            name = "Guest";
            post.setAuthor(name);

            mv.addObject("userName", name);
            mv.addObject("blog", post);
            mv.setViewName("new-blog");
        }
        else if(name.equals("Admin")) {
            mv.addObject("userName", name);
            mv.addObject("blog", post);
            mv.setViewName("new-blog-admin");
        }
        else {
            post.setAuthor(name);

            mv.addObject("userName", name);
            mv.addObject("blog", post);
            mv.setViewName("new-blog-logged-in");
        }

//        mv.addObject("userName", name);
//        mv.addObject("blog", post);
//        mv.setViewName("new-blog");

        return mv;
    }

    @PostMapping("/addBlog")
    public ModelAndView addNewBlog(@ModelAttribute("blog") Post post) {
        ModelAndView mv;
        Post b;
        Date date;
        String tag, eachTag, tagArray[];
        Tags tagObjectArray[];
        PostTags postTagsArray[];
        int length, i;

        mv = new ModelAndView();
        date = new java.util.Date();

        tag = post.getTags();
        tagArray = tag.split(",");
        length = tagArray.length;

        tagObjectArray = new Tags[length];
        postTagsArray = new PostTags[length];

        post.setPublishedAt(date);
        post.setIsPublished(true);
        post.setCreatedAt(date);
        post.setUpdatedAt(date);


        System.out.println("*************************");
        for(i = 0; i < length; i++) {
            eachTag = tagArray[i].trim();

            System.out.println("Tag: " + eachTag);
            tagObjectArray[i] = new Tags();

            tagObjectArray[i].setName(eachTag);
            tagObjectArray[i].setCreatedAt(date);
            tagObjectArray[i].setUpdatedAt(date);
        }
        System.out.println("*************************");

        for(i = 0; i < length; i++) {
            postTagsArray[i] = new PostTags(post, tagObjectArray[i], date, date);
        }

        for(i = 0; i < length; i++) {
            post.addPostTags(postTagsArray[i]);
        }

        b = blogService.addNewBlog(post);

        mv.addObject(post);
        mv.setViewName("new-blog");
        return mv;
    }

    @PostMapping("/updateBlog")
    public ModelAndView updateBlog(@ModelAttribute("blog") Post blog) {
        ModelAndView mv;
        Optional<Post> postBeforeUpdate;
        List<Post> postList;
        Post post;
        Date date;
        int id;
        Page<Post> allBlogs;
        Pageable pageable;
        int totalPages, currentPage;
        long totalPosts;

        mv = new ModelAndView();
        date = new java.util.Date();

        id = blog.getId();

        postBeforeUpdate = blogService.findById(id);
        post = postBeforeUpdate.get();

        blog.setPublishedAt(post.getPublishedAt());
        blog.setIsPublished(true);
        blog.setCreatedAt(post.getCreatedAt());
        blog.setUpdatedAt(date);

        post = blogService.addNewBlog(blog);

        postList = blogService.getAllBlogs();

        currentPage = 1;

        pageable = PageRequest.of(currentPage - 1, 3, Sort.Direction.DESC, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);
        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();

        mv = new ModelAndView();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);

        mv.addObject("userName", name);


        mv.addObject(blog);
        mv.addObject("postList", postList);

        mv.setViewName("home");
        return mv;
    }

    @PostMapping("/addComment")
    public ModelAndView addNewComment(@ModelAttribute("comments") Comment comments) {
        ModelAndView mv;
        List<Comment> commentList;
        Date date;
        Optional<Post> post;
        Post temp;
        String title, author, content;

        int id;

        mv = new ModelAndView();
        date = new java.util.Date();

        comments.setCreatedAt(date);
        comments.setUpdatedAt(date);

        commentService.addNewComment(comments);

        id = comments.getPostId();

        post = blogService.findById(id);
        commentList = commentService.findByPostId(id);

        temp = post.get();
        title = temp.getTitle();
        author = temp.getAuthor();
        content = temp.getContent();

        mv.addObject("title", title);
        mv.addObject("author", author);
        mv.addObject("content", content);


        mv.addObject("comments", comments);
        mv.addObject("commentList", commentList);
        mv.setViewName("show-blog");
        return mv;
    }

    @GetMapping("/deleteComment")
    public ModelAndView deleteComment(@ModelAttribute("commentId") int id) {
        ModelAndView mv;
        Comment comment;
        List<Comment> commentList;
        Date date;
        Optional<Post> post;
        Post temp;
        String title, author, content;
        int postID;

        mv = new ModelAndView();

        comment = commentService.findById(id);
        postID = comment.getPostId();

        commentService.deleteById(id);

        post = blogService.findById(postID);
        commentList = commentService.findByPostId(postID);

        temp = post.get();
        title = temp.getTitle();
        author = temp.getAuthor();
        content = temp.getContent();

        mv.addObject("title", title);
        mv.addObject("author", author);
        mv.addObject("content", content);
        mv.addObject("comments", new Comment());
        mv.addObject("commentList", commentList);

        mv.setViewName("show-blog");

        return mv;
    }

    @GetMapping("/editBlog")
    public ModelAndView getEditBlogView(@ModelAttribute("postId") int id) {
        ModelAndView mv;
        Optional<Post> blog;

        mv = new ModelAndView();
        blog = blogService.findById(id);

        mv.addObject("blog", blog);
        mv.setViewName("edit-blog");

        return mv;
    }

    @GetMapping("/viewBlog")
    public ModelAndView getBlogView(@ModelAttribute("postId") int id) {
        ModelAndView mv;
        Optional<Post> post;
        Post temp;
        String title, content, author;
        Comment comment;
        List<Comment> commentList;

        mv = new ModelAndView();
        post = blogService.findById(id);
        comment = new Comment();

        temp = post.get();
        title = temp.getTitle();
        author = temp.getAuthor();
        content = temp.getContent();

        comment.setPostId(id);

        commentList = commentService.findByPostId(id);

        mv.addObject("commentList", commentList);

        mv.addObject("username", name);

        mv.addObject("title", title);
        mv.addObject("author", author);
        mv.addObject("content", content);

        mv.addObject("postId", id);
        mv.addObject("post", post);
        mv.addObject("comments", comment);
        mv.setViewName("show-blog");

        return mv;
    }

    @GetMapping("/deleteBlog")
    public ModelAndView deleteBlog(@ModelAttribute("postId") int id) {
        ModelAndView mv;
        Page<Post> allBlogs;
        List<Post> postList;
        Pageable pageable;
        int totalPages, currentPage;
        long totalPosts;

        mv = new ModelAndView();
        blogService.deleteById(id);


        currentPage = 1;

        pageable = PageRequest.of(currentPage - 1, 3, Sort.Direction.DESC, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);
        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);

        mv.addObject("userName", name);
        mv.setViewName("home");

        return mv;
    }

    @GetMapping("/search")
    public ModelAndView searchBlog(@ModelAttribute("search") String searchString) {
        ModelAndView mv;
        List<Post> postList, postListBySearchKeyword;
        String title, content, author, tags;

        postList = blogService.getAllBlogs();
        postListBySearchKeyword = new ArrayList<Post>();

        System.out.println("*****************************");
        for(Post p : postList) {
            title = p.getTitle();
            content = p.getContent();
            author = p.getAuthor();
            //tags = p.getTags();

            //System.out.println("Tags: " + tags);
            if(title.contains(searchString) || content.contains(searchString) || author.contains(searchString)) {
                postListBySearchKeyword.add(p);
            }
        }
        System.out.println("*****************************");

        mv = new ModelAndView();

        mv.addObject("searchString", searchString);
        mv.addObject("postList", postListBySearchKeyword);

        mv.setViewName("search-result");
        return mv;
    }

    @GetMapping("/filter-by-author")
    public ModelAndView filterBlogByAuthor(@ModelAttribute("author-search") String filterString) {
        ModelAndView mv;
        List<Post> postList, postListByFilterKeyword;
        String author;

        postList = blogService.getAllBlogs();
        postListByFilterKeyword = new ArrayList<Post>();


        for(Post p : postList) {
            author = p.getAuthor();

            if(author.contains(filterString)) {
                postListByFilterKeyword.add(p);
            }
        }

        mv = new ModelAndView();

        mv.addObject("searchString", filterString);
        mv.addObject("postList", postListByFilterKeyword);

        mv.setViewName("search-result");
        return mv;
    }

    @GetMapping("/filter-by-date-time")
    public ModelAndView filterBlogByDateTime(@ModelAttribute("date-time-search") String filterString) {
        ModelAndView mv;
        List<Post> postList, postListByFilterKeyword;
        String date;

        postList = blogService.getAllBlogs();
        postListByFilterKeyword = new ArrayList<Post>();


        for(Post p : postList) {
            date = p.getUpdatedAt().toString();

            if(date.contains(filterString)) {
                postListByFilterKeyword.add(p);
            }
        }

        mv = new ModelAndView();

        mv.addObject("searchString", filterString);
        mv.addObject("postList", postListByFilterKeyword);

        mv.setViewName("search-result");
        return mv;
    }

    @GetMapping("/filter-by-tags")
    public ModelAndView filterBlogByTags(@ModelAttribute("tag-search") String filterString) {
        ModelAndView mv;
        List<Post> postList, postListByFilterKeyword;
        String tags;

        postList = blogService.getAllBlogs();
        postListByFilterKeyword = new ArrayList<Post>();


        for(Post p : postList) {
            tags = p.getTags();

            if(tags.contains(filterString)) {
                postListByFilterKeyword.add(p);
            }
        }

        mv = new ModelAndView();

        mv.addObject("searchString", filterString);
        mv.addObject("postList", postListByFilterKeyword);

        mv.setViewName("search-result");
        return mv;
    }

    @GetMapping("/sort")
    public ModelAndView sortResult(@ModelAttribute("date") String option) {
        ModelAndView mv;
        Page<Post> allBlogs;
        List<Post> postList;
        Pageable pageable;
        int totalPages, currentPage;
        long totalPosts;
        Sort.Direction direction;

        mv = new ModelAndView();

        currentPage = 1;
        direction = null;

        if(option.equals("newest-first")) {
            direction = Sort.Direction.DESC;
        }

        if(option.equals("oldest-first")) {
            direction = Sort.Direction.ASC;
        }

        pageable = PageRequest.of(currentPage - 1, 3, direction, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);
        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();

        mv = new ModelAndView();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);

        mv.addObject("userName", name);
        mv.setViewName("home");

        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/sort-non-logged-in")
    public ModelAndView sortResultNonLoggedIn(@ModelAttribute("date") String option) {
        ModelAndView mv;
        Page<Post> allBlogs;
        List<Post> postList;
        Pageable pageable;
        int totalPages, currentPage;
        long totalPosts;
        Sort.Direction direction;

        mv = new ModelAndView();

        currentPage = 1;
        direction = null;

        if(option.equals("newest-first")) {
            direction = Sort.Direction.DESC;
        }

        if(option.equals("oldest-first")) {
            direction = Sort.Direction.ASC;
        }

        pageable = PageRequest.of(currentPage - 1, 3, direction, "createdAt");
        allBlogs = blogService.getAllBlogs(pageable);
        postList = allBlogs.getContent();

        totalPages = allBlogs.getTotalPages();
        totalPosts = allBlogs.getTotalElements();

        mv = new ModelAndView();

        mv.addObject("currentPage",currentPage);
        mv.addObject("totalPages", totalPages);
        mv.addObject("totalPosts", totalPosts);
        mv.addObject("postList", postList);

        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/logout")
    public ModelAndView getIndexView() {
        ModelAndView mv = new ModelAndView("login-signup");

        NewUser newUser = new NewUser();
        ExistingUser existingUser = new ExistingUser();

        mv.addObject("newUser", newUser);
        mv.addObject("existingUser", existingUser);

        return mv;
    }
}