package com.springboot.blog.service;

import com.springboot.blog.payload_dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByPostId(long postId);

    CommentDTO getCommentByPostId(Long postId, Long commentId);

    CommentDTO updateCommentByPostId(Long postId, long commentId, CommentDTO commentDTO);

    void deleteCommentIdByPostId(Long postId, Long commentId);

    void deleteCommentAllByPostId(Long postId);
}
