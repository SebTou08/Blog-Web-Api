package com.acme.blogging.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name ="posts")
public class Post extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Post(@NotNull String title, @NotNull String description, @NotNull String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="post_tags", joinColumns = {@JoinColumn(name="post_id")},
    inverseJoinColumns = {@JoinColumn(name="tag_id")})
    private List<Tag>tags;


    public boolean isTaggedWith(Tag tag){
        return this.getTags().contains(tag);
    }

    public Post tagWith(Tag tag){
        if(!isTaggedWith(tag)){
            this.getTags().add(tag);
        }
        return this;
    }

    public Post untagWith(Tag tag){
        if(this.isTaggedWith(tag)){
            this.getTags().remove(tag);
        }
        return this;
    }

}
