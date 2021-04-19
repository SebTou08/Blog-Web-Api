package com.acme.blogging.domain.repository;

import com.acme.blogging.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByTitle(String title);

    Optional<Object> findByTittle(String title);
}
