package com.letitbee.diamondvaluationsystem.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class PostDTO {
    private long id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    private String thumbnail;
    private Date publishedDate;
    private Date creationDate;
    @NotEmpty(message = "Reference cannot be empty")
    private String reference;
}
