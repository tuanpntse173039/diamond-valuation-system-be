package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.enums.BlogType;
import com.letitbee.diamondvaluationsystem.payload.PostDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;

public interface PostService {

    PostDTO createPost(PostDTO postDto);

    Response<PostDTO> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir, BlogType status);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDto, long id);

    void deletePostById(long id);
}
