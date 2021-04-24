package com.acme.blogging.domain.controller;


import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper mapper;


    private Post convertToEntity(SavePostResource resource){
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity){
        return mapper.map(entity,PostResource.class);
    }

    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(Pageable pageable){
        Page<Post> postPage = postService.getAllPost(pageable);
        List<PostResource> resources = postPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size()) ;
    }

    @PostMapping("/posts")
    public PostResource createPost(@Valid @RequestBody SavePostResource resource){
        Post post = convertToEntity(resource);
        return convertToResource(postService.createPost(post));
    }

    @PutMapping("/posts/{postId}}")
    public PostResource updatePost(@PathVariable Long postId, @Valid @RequestBody SavePostResource resource){
        Post post = convertToEntity(resource);
        return convertToResource(postService.updatePost(postId,post));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

}
