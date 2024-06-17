package com.letitbee.diamondvaluationsystem;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.payload.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class BlogManagement {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @Rollback
    public void testCreateTitleBlank() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("");
        postDTO.setContent("testContent");
        postDTO.setCreationDate(new Date());
        postDTO.setDescription("testDescription");
        postDTO.setPublishedDate(new Date());
        postDTO.setReference("https://www.test.com");
        postDTO.setThumbnail("testThumbnail");
        String postJson = objectMapper.writeValueAsString(postDTO);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateDescriptionBlank() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("t");
        postDTO.setContent("testContent");
        postDTO.setCreationDate(new Date());
        postDTO.setDescription("");
        postDTO.setPublishedDate(new Date());
        postDTO.setReference("https://www.test.com");
        postDTO.setThumbnail("testThumbnail");
        String postJson = objectMapper.writeValueAsString(postDTO);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateReferenceBlank() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("testTitle");
        postDTO.setContent("testContent");
        postDTO.setCreationDate(new Date());
        postDTO.setDescription("testDescription");
        postDTO.setPublishedDate(new Date());
        postDTO.setReference("");
        postDTO.setThumbnail("testThumbnail");
        String postJson = objectMapper.writeValueAsString(postDTO);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateReferenceInvalid() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("testTitle");
        postDTO.setContent("testContent");
        postDTO.setCreationDate(new Date());
        postDTO.setDescription("testDescription");
        postDTO.setPublishedDate(new Date());
        postDTO.setReference("testReference");
        postDTO.setThumbnail("testThumbnail");
        String postJson = objectMapper.writeValueAsString(postDTO);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateContextBlank() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("testTitle");
        postDTO.setContent("null");
        postDTO.setCreationDate(new Date());
        postDTO.setDescription("testDescription");
        postDTO.setPublishedDate(new Date());
        postDTO.setReference("https://www.test.com");
        postDTO.setThumbnail("testThumbnail");
        String postJson = objectMapper.writeValueAsString(postDTO);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(postJson))
                .andExpect(status().isBadRequest());
    }

}
