package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private long id;
    @Column(columnDefinition = "nvarchar(100)")
    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "nvarchar(1000)")
    private String description;
    @Column(columnDefinition = "nvarchar(500)")
    private String thumbnail;
    @Column(columnDefinition = "datetime")
    private String publishedDate;
    @Column(columnDefinition = "datetime")
    private String creationDate;
    @Column(columnDefinition = "nvarchar(100)")
    private String reference;
 }
