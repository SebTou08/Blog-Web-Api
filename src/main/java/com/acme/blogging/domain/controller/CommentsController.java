package com.acme.blogging.domain.controller;

import com.acme.blogging.domain.model.Comment;
import com.acme.blogging.domain.service.CommentService;
import com.acme.blogging.resource.CommentResource;
import com.acme.blogging.resource.SaveCommentResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentsController {
    @Autowired
    private CommentService _commentService;
    @Autowired
    private ModelMapper mapper;

    private Comment convertToEntity(SaveCommentResource resource){
        return mapper.map(resource, Comment.class);
    }
    private CommentResource convertToResource (Comment entity){
        return mapper.map(entity, CommentResource.class);
    }

    //nested resource
    @GetMapping("/post/{postId}/comments")
    public Page<CommentResource> getAllCommentsByPostId(@PathVariable Long postId, Pageable pageable){
        Page<Comment> commentPage = _commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size()) ;
    }
}
