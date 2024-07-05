package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.BlogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Long id;
    @Column(columnDefinition = "nvarchar(100)")
    private String title;
    @Column(columnDefinition = "nvarchar(500)")
    private String description;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "nvarchar(500)")
    private String thumbnail;
    @Column(columnDefinition = "datetime")
    private Date lastModifiedDate;
    @Column(columnDefinition = "datetime")
    private Date creationDate;
    @Column(columnDefinition = "nvarchar(100)")
    private String reference;
    @Enumerated(EnumType.STRING)
    private BlogType status;
    @Column(columnDefinition = "nvarchar(100)")
    private String author;
 }
