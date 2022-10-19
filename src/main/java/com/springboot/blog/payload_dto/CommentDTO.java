package com.springboot.blog.payload_dto;

import lombok.Data;

@Data
public class CommentDTO {


    private long commmentId;
    private String name;
    private String email;
    private String body;

}
