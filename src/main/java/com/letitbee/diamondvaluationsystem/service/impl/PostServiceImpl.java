package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Post;
import com.letitbee.diamondvaluationsystem.enums.BlogType;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.PostDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.repository.PostRepository;
import com.letitbee.diamondvaluationsystem.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {
        if(postRepository.existsByTitle(postDto.getTitle())){
            throw new APIException(HttpStatus.BAD_REQUEST,"Title already exist");
        }
        postDto.setCreationDate(new Date());
        postDto.setLastModifiedDate(new Date());
        postDto.setStatus(BlogType.DRAFT);
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDto(newPost);
    }

    @Override
    public Response<PostDTO> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir, BlogType status) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAllByStatus(pageable, status);
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        Response<PostDTO> postResponse = new Response<>();

        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id + ""));
        return mapToDto(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id + ""));
        if(postDto.getTitle() != null && !postRepository.existsByTitleUpdate(postDto.getTitle(), id).isEmpty()){
            throw new APIException(HttpStatus.BAD_REQUEST,"Title already exist");
        }else if(postDto.getTitle() != null){
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getReference() != null){
            post.setReference(postDto.getReference());
        }
        if (postDto.getStatus() != null){
            post.setStatus(postDto.getStatus());
        }
        post.setLastModifiedDate(new Date());
        if (postDto.getThumbnail() != null){
            post.setThumbnail(postDto.getThumbnail());
        }
        if (postDto.getContent() != null){
            post.setContent(postDto.getContent());
        }
        if (postDto.getAuthor() != null){
            post.setAuthor(postDto.getAuthor());
        }
        if (postDto.getDescription() != null){
            post.setDescription(postDto.getDescription());
        }
        if (postDto.getPublishedDate() != null){
            post.setPublishedDate(postDto.getPublishedDate());
        }
        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id + ""));
        postRepository.delete(post);
    }

    private PostDTO mapToDto(Post post){
        return  mapper.map(post, PostDTO.class);
    }
    private Post mapToEntity(PostDTO postDto){
        return mapper.map(postDto, Post.class);
    }
}
