package com.acme.blogging.domain.service;

import com.acme.blogging.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {
    Page<Post> getAllPost(Pageable pageable);
    Post getById(Long postId);
    Post createPost(Post post);
    Post updatePost(Long postId, Post postRequest);
    ResponseEntity<?> deletePost(Long postId);
    Post assingPostTag(long postId, long tagId);
    Post unassignTag( Long postId, long tagId);
    Page<Post>getAllPostByTagId(long tagId, Pageable pageable);
    Post getPostByTittle(String title);
}
