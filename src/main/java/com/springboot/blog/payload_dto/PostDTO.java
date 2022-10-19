package com.springboot.blog.payload_dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private long postId;
    private String title;
    private String description;
    private String content;
    //has to be the same name as PostEntity
    private Set<CommentDTO> comments;

}
