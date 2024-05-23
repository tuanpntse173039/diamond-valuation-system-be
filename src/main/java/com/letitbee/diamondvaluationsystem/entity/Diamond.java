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
    @JoinColumn(name = "valuation_request_id")
    private ValuationRequest valuationRequest;
    @Column(columnDefinition = "varchar(10)")
    private String certificateId;
    @Column(columnDefinition = "varchar(1000)")
    private String proportions;
    @Column(columnDefinition = "varchar(1000)")
    private String clarityCharacteristic;
    @ManyToOne
    @JoinColumn(name = "diamond_origin_id")
    private DiamondOrigin diamondOrigin;
    private float caratWeight;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne
    @JoinColumn(name = "clarity_id")
    private Clarity clarity;
    @ManyToOne
    @JoinColumn(name = "cut_id")
    private Cut cut;
    @ManyToOne
    @JoinColumn(name = "polish_id")
    private Polish polish;
    @ManyToOne
    @JoinColumn(name = "symmetry_id")
    private Symmetry symmetry;
    @ManyToOne
    @JoinColumn(name = "shape_id")
    private Shape shape;
    @ManyToOne
    @JoinColumn(name = "fluorescence_id")
    private Fluorescence fluorescence;
    @Column(columnDefinition = "money")
    private String fairPrice;
    @Column(columnDefinition = "money")
    private String minPrice;
    @Column(columnDefinition = "money")
    private String maxPrice;
    private boolean isStatus;
    @OneToMany(mappedBy = "diamond")
    private Set<DiamondImage> diamondImage = new HashSet<>();
    @OneToOne(mappedBy = "diamond")
    private ValuationNote valuationNote;
}
