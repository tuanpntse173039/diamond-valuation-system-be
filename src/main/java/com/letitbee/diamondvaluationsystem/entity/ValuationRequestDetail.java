package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.RequestDetailStatus;
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

    @Column(columnDefinition = "money")
    private String resultPrice;

    @Column(columnDefinition = "varchar(1000)")
    private String sealingRecordLink;

    @Column(columnDefinition = "float")
    private float size;

    @Enumerated(EnumType.STRING)
    private RequestDetailStatus status;

    @Column(columnDefinition = "bit default 1")
    private boolean isDiamond;

    @OneToOne
    @JoinColumn(name = "diamond_id", nullable = true)
    private DiamondValuationNote diamondValuationNote;

    @ManyToOne
    @JoinColumn(name = "valuation_request_id", nullable = false)
    private ValuationRequest valuationRequest;

    @OneToOne
    @JoinColumn(name = "diamond_valuation_id")
    private DiamondValuationAssign diamondValuationAssign;

    @OneToMany(mappedBy = "valuationRequestDetail",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DiamondValuationAssign> diamondValuationAssigns = new HashSet<>();

}
