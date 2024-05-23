package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DiamondImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String image;
    @ManyToOne
    @JoinColumn(name = "diamond_id")
    private Diamond diamond;

}
