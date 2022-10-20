package com.springboot.blog.payload_dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {

    private long postId;

    @NotEmpty
    @Size(min = 2, message = "Post title must have at least 2 characters.")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description must have at least 10 characters.")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "Post description must have at least 2 characters.")
    private String content;
    //has to be the same name as PostEntity
    private Set<CommentDTO> comments;

}
