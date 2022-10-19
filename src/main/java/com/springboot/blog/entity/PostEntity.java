package com.springboot.blog.entity;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class PostEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    /*post if from the commentEntity field name post where you're linking the manyToOne. If parent is remove also remove it from here.
    Must have the same name as PostDTO comments
    mappedBy sould be the same name as CommentEntity private PostEntity post
    * */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentEntity> comments = new HashSet<>();

}
