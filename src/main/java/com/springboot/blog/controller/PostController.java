package com.springboot.blog.controller;

import com.springboot.blog.payload_dto.PostDTO;
import com.springboot.blog.payload_dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    //create blog post
    @PostMapping("/post")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //get all posts added pagination and sorting
    @GetMapping("/all-posts-sorted")
    @ResponseStatus(code = HttpStatus.OK)
    public PostResponse getAllPostsSorted(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {

        return postService.getAllPostsSorted(pageNo, pageSize, sortBy, sortDir);
    }

    //get all posts added pagniation and sorting
    @GetMapping("/all-posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {

        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    //get post by postId
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    //update post by postId
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,@PathVariable long postId) {

        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);
    }

    //delete post by postId
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(String.format("Post entity id: '%s' was removed successfully.", postId), HttpStatus.OK);
    }


}
