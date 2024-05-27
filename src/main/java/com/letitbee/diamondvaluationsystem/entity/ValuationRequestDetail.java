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

public class ValuationRequestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isMode;

    @Column(columnDefinition = "money")
    private String resultPrice;

    @Column(columnDefinition = "varchar(1000)")
    private String sealingRecordLink;

    @Column(columnDefinition = "float", nullable = false)
    private float size;

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String status;

    @Column(columnDefinition = "bit default 1")
    private boolean isDiamond;

    @OneToOne
    @JoinColumn(name = "diamond_id", nullable = false)
    private DiamondValuationNote diamondValuationNote;

    @ManyToOne
    @JoinColumn(name = "valuation_request_id", nullable = false)
    private ValuationRequest valuationRequest;

    @OneToOne
    @JoinColumn(name = "diamond_valuation_id")
    private DiamondValuationAssign diamondValuationAssign;

    @OneToMany(mappedBy = "valuationNote")
    private Set<DiamondValuationAssign> diamondValuationAssigns = new HashSet<>();

}
