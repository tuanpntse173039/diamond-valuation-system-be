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

public class ValuationNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "diamond_id", nullable = false)
    private Diamond diamond;
    @ManyToOne
    @JoinColumn(name = "valuation_request_id", nullable = false)
    private ValuationRequest valuationRequest;
    @Column(columnDefinition = "money")
    private String resultPrice;

    @OneToOne
    @JoinColumn(name = "diamond_valuation_id")
    private DiamondValuation diamondValuation;

    private boolean isMode;

    @OneToMany(mappedBy = "valuationNote")
    private Set<SealingRecord> sealingRecords = new HashSet<>();

    @Column(columnDefinition = "varchar(1000)")
    private String sealingRecordLink;

    @OneToMany(mappedBy = "valuationNote")
    private Set<DiamondValuation> diamondValuations = new HashSet<>();

    @ManyToOne
    private ValuationNoteStatus valuationNoteStatus;
}
