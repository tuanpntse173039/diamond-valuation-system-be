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

    @Column(columnDefinition = "varchar(1000)")
    private String proportions;

    @Column(columnDefinition = "varchar(1000)")
    private String clarityCharacteristic;

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
    private String fairPrice;

    @Column(columnDefinition = "money")
    private String minPrice;

    @Column(columnDefinition = "money")
    private String maxPrice;

    @Column(nullable = true)
    private boolean isStatus;

    @OneToMany(mappedBy = "diamondValuationNote",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DiamondImage> diamondImage = new HashSet<>();

    @OneToOne(mappedBy = "diamondValuationNote")
    private ValuationRequestDetail valuationRequestDetail;
}
