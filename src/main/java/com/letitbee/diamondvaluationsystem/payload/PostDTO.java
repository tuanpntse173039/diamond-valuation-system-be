package com.letitbee.diamondvaluationsystem.payload;

import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private String title;
    private String content;
    private String description;
    private String thumbnail;
    private String publishedDate;
    private String creationDate;
    private String reference;
}
