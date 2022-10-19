package com.springboot.blog.service.impl;

import com.springboot.blog.entity.PostEntity;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload_dto.PostDTO;
import com.springboot.blog.payload_dto.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Spring packages Pageable
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO to entity
        PostEntity post = convertDTOtoEntity(postDTO);
        PostEntity newPost =  postRepository.save(post);

       //convert entity to DTO
        PostDTO postResponse = convertEntitytoDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts() {

        List<PostEntity> posts =  postRepository.findAll();

       //like a for loop get one post at a time to post but in one line
       return posts.stream().map(post -> convertEntitytoDTO(post)).collect(Collectors.toList());

    }

    @Override
    public PostResponse getAllPostsSorted(int pageNo, int pageSize, String sortBy, String sortDir) {

        //if statement where it sort is equal to ascending sort by ascending else sort by descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<PostEntity> postPageObjects = postRepository.findAll(pageable);

        //get content for page object
        List<PostEntity> listOfPosts = postPageObjects.getContent();

        //like a for loop get one post at a time to post but in one line
        List<PostDTO> content = listOfPosts.stream().map(post -> convertEntitytoDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postPageObjects.getNumber());
        postResponse.setPageSize(postPageObjects.getSize());
        postResponse.setTotalElements(postPageObjects.getTotalElements());
        postResponse.setTotalPages(postPageObjects.getTotalPages());
        postResponse.setLast(postPageObjects.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPost(long postId) {
        PostEntity postInfo = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        return convertEntitytoDTO(postInfo);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long postId) {
        //get post by Id
        PostEntity postByIdInfo = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        postByIdInfo.setTitle(postDTO.getTitle());
        postByIdInfo.setDescription(postDTO.getDescription());
        postByIdInfo.setContent(postDTO.getContent());

        PostEntity updatedPost = postRepository.save(postByIdInfo);

        //convert entity to DTO
        PostDTO postResponse = convertEntitytoDTO(updatedPost);


        return postResponse;
    }

    @Override
    public void deletePost(long postId) {
        //get post by Id
        PostEntity postByIdInfo = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        postRepository.delete(postByIdInfo);

    }


    public PostEntity convertDTOtoEntity(PostDTO postDTO) {
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);

        return postEntity;
    }

    public PostDTO convertEntitytoDTO(PostEntity postEntity) {
        PostDTO postDTO = modelMapper.map(postEntity, PostDTO.class);

        return postDTO;
    }



}
