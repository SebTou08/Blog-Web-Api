package com.acme.blogging.service;

import com.acme.blogging.domain.model.Comment;
import com.acme.blogging.domain.repository.CommentRepository;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.service.CommentService;
import com.acme.blogging.exception.ResourceNotFOundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository _commentRepository;
    @Autowired
    private PostRepository _postRepository;



    @Override
    public Page<Comment> getAllCommentsByPostId(Long postId, Pageable pageable) {
        return _commentRepository.findByPostId(postId, pageable);
    }

    @Override
    public Comment getCommentByIdAndPostId(Long postId, Long commentId) {
        return _commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(()-> new ResourceNotFOundExeption("comment not found with id: "+ commentId+" and post Id: "+postId));
    }

    @Override
    public Comment createComment(Long postId, Comment comment) {
        return _postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return _commentRepository.save(comment);
        }).orElseThrow(()-> new ResourceNotFOundExeption("Post", "id", postId));
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, Comment commentDetail) {
        //comprobar si extiste un post con ese postId
        if(!_postRepository.existsById(postId))
            throw new ResourceNotFOundExeption("Post", "id", postId);
        //si no existe el post me voy, no tiene sentido
        //en caso que si exista el post...
        return _commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentDetail.getText());
            return _commentRepository.save(comment);
        }) .orElseThrow(()-> new ResourceNotFOundExeption("Post", "id", postId));
}

    @Override
    public ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        if(!_postRepository.existsById(postId))
            throw new ResourceNotFOundExeption("Post", "id", postId);
        //si no existe el post me voy, no tiene sentido
        //en caso que si exista el post...
        return (ResponseEntity<?>) _commentRepository.findById(commentId).map(comment -> {
            _commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }) .orElseThrow(()-> new ResourceNotFOundExeption("Post", "id", postId));

    }
}
