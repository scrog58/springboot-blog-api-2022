package com.springboot.blog.payload_dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {


    private long commmentId;

    @NotEmpty(message = "Name can't be null or empty.")
    private String name;

    @NotEmpty(message = "Email can't be null or empty.")
    @Email(message = "Email format should be like this example: email@text.com")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "This should have at least 10 characters.")
    private String body;

}
