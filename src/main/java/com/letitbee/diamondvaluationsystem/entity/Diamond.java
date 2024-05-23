package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "valuation_request_id", nullable = false)
    private ValuationRequest valuationRequest;

    @Column(columnDefinition = "varchar(10)")
    private String certificateId;

    @Column(columnDefinition = "varchar(1000)")
    private String proportions;

    @Column(columnDefinition = "varchar(1000)")
    private String clarityCharacteristic;

    private String diamondOrigin;

    @Column(nullable = true)
    private float caratWeight;

    private String color;

    private String clarity;

    private String cut;

    private String polish;

    private String symmetry;

    private String shape;

    private String fluorescence;

    @Column(columnDefinition = "money")
    private String fairPrice;
    @Column(columnDefinition = "money")
    private String minPrice;
    @Column(columnDefinition = "money")
    private String maxPrice;
    @Column(nullable = true)
    private boolean isStatus;
    @OneToMany(mappedBy = "diamond")
    private Set<DiamondImage> diamondImage = new HashSet<>();
    @OneToOne(mappedBy = "diamond")
    private ValuationNote valuationNote;
}
