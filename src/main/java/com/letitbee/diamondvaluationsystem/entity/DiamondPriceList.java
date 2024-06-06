package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table

public class DiamondPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "datetime", nullable = false)
    private Date creationDate;
    @Column(nullable = false)
    private float caratWeight;
    @Enumerated(EnumType.STRING)
    private DiamondOrigin diamondOrigin;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Clarity clarity;

    @Enumerated(EnumType.STRING)
    private Cut cut;

    @Enumerated(EnumType.STRING)
    private Polish polish;

    @Enumerated(EnumType.STRING)
    private Symmetry symmetry;

    @Enumerated(EnumType.STRING)
    private Shape shape;

    @Enumerated(EnumType.STRING)
    private Fluorescence fluorescence;
    @Column(columnDefinition = "money", nullable = false)
    private Double fairPrice;
    @Column(columnDefinition = "money", nullable = false)
    private Double minPrice;
    @Column(columnDefinition = "money", nullable = false)
    private Double maxPrice;
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String percentChange;

}
