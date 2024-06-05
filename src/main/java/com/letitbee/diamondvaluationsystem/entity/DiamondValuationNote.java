package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.*;
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
public class DiamondValuationNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(10)")
    private String certificateId;


    @Column(columnDefinition = "datetime")
    private String certificateDate;

    @Column(columnDefinition = "varchar(1000)")
    private String proportions;

    @Enumerated(EnumType.STRING)
    private ClarityCharacteristic clarityCharacteristic;

    @Enumerated(EnumType.STRING)
    private DiamondOrigin diamondOrigin;

    @Column(nullable = true)
    private float caratWeight;

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

    @Column(columnDefinition = "money")
    private double fairPrice;

    @Column(columnDefinition = "money")
    private double minPrice;

    @Column(columnDefinition = "money")
    private double maxPrice;

    @OneToMany(mappedBy = "diamondValuationNote",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DiamondImage> diamondImage = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "valuation_request_detail_id")
    private ValuationRequestDetail valuationRequestDetail;
}
