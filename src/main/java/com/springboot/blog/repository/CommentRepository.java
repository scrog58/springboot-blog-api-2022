package com.springboot.blog.repository;

import com.springboot.blog.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    //The reason for two Post is you calling post from the CommentEntity and the PostId form PostEntity
    List<CommentEntity> findByPostPostId(long postId);

}
