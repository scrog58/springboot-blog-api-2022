package com.springboot.blog.service;

import com.springboot.blog.payload_dto.PostDTO;
import com.springboot.blog.payload_dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDto);

    List<PostDTO> getAllPosts();

    PostResponse getAllPostsSorted(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPost(long postId);

    PostDTO updatePost(PostDTO postDTO,long postId);

    void deletePost(long postId);


}
