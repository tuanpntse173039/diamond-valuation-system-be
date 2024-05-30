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

public class DiamondPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "datetime", nullable = false)
    private String creationDate;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String origin;
    @Column(nullable = false)
    private float caratWeight;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String shape;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String color;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String clarity;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String cut;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String polish;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String symmetry;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String fluorescence;
    @Column(columnDefinition = "money", nullable = false)
    private double fairPrice;
    @Column(columnDefinition = "money", nullable = false)
    private double minPrice;
    @Column(columnDefinition = "money", nullable = false)
    private double maxPrice;
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String percentChange;

}
