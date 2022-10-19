package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CommentEntity;
import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload_dto.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        CommentEntity commentEntity = mapToEntity(commentDTO);

        //get post entity by postId else throw an error
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        commentEntity.setPost(post);

        //save comment entity to DB
        CommentEntity newComment = commentRepository.save(commentEntity);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(long postId) {

        //retrieve comments by postId
        List<CommentEntity> listAllCommentsByPostId = commentRepository.findByPostPostId(postId);

        //covert list of comments entities to list of comment dto's
        return listAllCommentsByPostId.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentByPostId(Long postId, Long commentId) {

        //get post entity by postId else throw an error
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));


        //retrive comment
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));


       if(!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }


        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateCommentByPostId(Long postId, long commentId, CommentDTO commentDTO) {

        //get post entity by postId else throw an error
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        //retrive comment
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        if(!commentEntity.getPost().getPostId().equals(postEntity.getPostId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to postID: " + postEntity.getPostId());
        }

        commentEntity.setName(commentDTO.getName());
        commentEntity.setEmail(commentDTO.getEmail());
        commentEntity.setBody(commentDTO.getBody());

        CommentEntity updateComment = commentRepository.save(commentEntity);


        return mapToDTO(updateComment);
    }

    @Override
    public void deleteCommentIdByPostId(Long postId, Long commentId) {

        //get post entity by postId else throw an error
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        //retrive comment
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        if(!commentEntity.getPost().getPostId().equals(postEntity.getPostId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to postID: " + postEntity.getPostId());
        }

        commentRepository.delete(commentEntity);

    }

    @Override
    public void deleteCommentAllByPostId(Long postId) {

        //retrieve comments by postId
        List<CommentEntity> listAllCommentsByPostId = commentRepository.findByPostPostId(postId);

        //delete all comments by postId
        commentRepository.deleteAll(listAllCommentsByPostId);

    }


    private CommentDTO mapToDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = modelMapper.map(commentEntity, CommentDTO.class);

//       CommentDTO commentDTO = new CommentDTO();
//
//       commentDTO.setCommmentId(commentEntity.getCommmentId());
//       commentDTO.setName(commentEntity.getName());
//       commentDTO.setEmail(commentEntity.getEmail());
//       commentDTO.setBody(commentEntity.getBody());

       return commentDTO;
    }


    private CommentEntity mapToEntity(CommentDTO commentDTO) {

        CommentEntity commentEntity = modelMapper.map(commentDTO, CommentEntity.class);

//        CommentEntity commentEntity = new CommentEntity();
//
//        commentEntity.setCommmentId(commentDTO.getCommmentId());
//        commentEntity.setName(commentDTO.getName());
//        commentEntity.setEmail(commentDTO.getEmail());
//        commentEntity.setBody(commentDTO.getBody());

        return commentEntity;
    }
}
