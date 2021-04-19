package com.acme.blogging.service;

import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.repository.TagRepository;
import com.acme.blogging.domain.service.TagService;
import com.acme.blogging.exception.ResourceNotFOundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private PostRepository _postRepository;
    @Autowired
    private TagRepository _tagRepository;


    @Override
    public Page<Tag> getAllTags(Pageable pegable) {
        return _tagRepository.findAll(pegable);
    }

    @Override
    public Page<Tag> getAllTagsByPostId(Long postId, Pageable pageable) {
        return _postRepository.findById(postId).map(posy ->{
            List<Tag> tags = posy.getTags();
            int tagsCount = tags.size();
            return new PageImpl<>(tags, pageable, tagsCount);
        }). orElseThrow(()->new ResourceNotFOundExeption("Post","id",postId));
    }

    @Override
    public Tag getTagById(Long tagId) {
        return _tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFOundExeption("tag", "id", tagId));
    }

    @Override
    public Tag createTag(Tag tag) {
        //TODO: que pasa si ese tag ya tiene id que existe
        //TODO: implementar si tiene un tag con ese id y si ya existe,
        //TODO: que retorne ese tag. Caso contrario, que lo cree
        //TODO: revisar que no coinsida ese nombre tambien
        //TODO: se puede imlpementar en el repositorio -findByName-
        return _tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long TagId, Tag tagDetails) {
        return _tagRepository.findById(TagId).map(tag->{
            tag.setName(tagDetails.getName());
            return _tagRepository.save(tag);
        }).orElseThrow(()-> new ResourceNotFOundExeption("Tag", "Id", TagId));
    }

    @Override
    public ResponseEntity<?> deleteTag(Long tagId) {
        return _tagRepository.findById(tagId).map(tag->{
            _tagRepository.delete(tag);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ResourceNotFOundExeption("Tag", "Id", tagId));

    }
}
