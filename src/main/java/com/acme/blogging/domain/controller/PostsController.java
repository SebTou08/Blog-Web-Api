package com.acme.blogging.domain.controller;


import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable){
        Page<Post> postPage = postService.getAllPost(pageable);
        return postPage;
    }
}
