package com.acme.blogging.service;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.repository.TagRepository;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.exception.ResourceNotFOundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private TagRepository tagRepository;


    @Override
    public Page<Post> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getById(Long postId) {
        //TODO: FALTA ACA
        return postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFOundExeption("Post", "Id", postId));
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long postId, Post postRequest) {
        //TODO: coorigrnergi
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFOundExeption("Post", "Id", postId));
      return postRepository.save(
              post.setTitle(postRequest.getTitle()).setDescription(postRequest.getDescription()).setContent(postRequest.getContent()));
    }

    @Override
    public ResponseEntity<?> deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFOundExeption("Post", "Id", postId));
        postRepository.delete(post);
        return ResponseEntity.ok().build();

    }

    @Override
    public Post assingPostTag(long postId, long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new ResourceNotFOundExeption("tag", "tagId", tagId));
        return postRepository.findById(postId).map(
                post -> postRepository.save(post.tagWith(tag)))
                .orElseThrow(()-> new ResourceNotFOundExeption("post", "postId", postId));


    }

    @Override
    public Post unassignTag(Long postId, long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()-> new ResourceNotFOundExeption("tag", "tagId", tagId));
        return postRepository.findById(postId).map(
                post -> postRepository.save(post.untagWith(tag)))
                .orElseThrow(()-> new ResourceNotFOundExeption("post", "postId", postId));


    }

    @Override
    public Page<Post> getAllPostByTagId(long tagId, Pageable pageable) {
        return  tagRepository.findById(tagId).map( tag ->{
                    List<Post>posts = tag.getPosts();
                    int postsCount = posts.size();
                    return new PageImpl<>(posts, pageable, postsCount); })
                .orElseThrow(()-> new ResourceNotFOundExeption("tag", "tagId", tagId));

    }

    @Override
    public Post getPostByTittle(String title) {
        return (Post) postRepository.findByTittle(title)
                .orElseThrow(()-> new ResourceNotFOundExeption("post", "Title", title));


    }
}
