package com.springboot.blog.controller;

import com.springboot.blog.payload_dto.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId,@Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAllCommentsByPostId(@PathVariable long postId) {

        return commentService.getAllCommentsByPostId(postId);

    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentByPostId(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getCommentByPostId(postId, commentId), HttpStatus.OK);

    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentByPostId(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId,@Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentService.updateCommentByPostId(postId, commentId, commentDTO), HttpStatus.OK);

    }

    //delete with comment id one
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentIdByPostId(@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteCommentIdByPostId(postId, commentId);
        return new ResponseEntity<>("Comment Id: " + commentId + " from Post Id: " + postId + " was remove successfully!", HttpStatus.OK);

    }

    //delete all comments in post by postId one post
    @DeleteMapping("/{postId}/comments")
    public ResponseEntity<String> deleteCommentAllByPostId(@PathVariable(name = "postId") Long postId) {
        commentService.deleteCommentAllByPostId(postId);
        return new ResponseEntity<>("All comments have been removed from  postId: " + postId + " was remove successfully!", HttpStatus.OK);

    }
}
