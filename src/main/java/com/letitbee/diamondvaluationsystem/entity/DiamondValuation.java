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

public class DiamondValuation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "diamond_note_id")
    private ValuationNote valuationNote;
    @Column(columnDefinition = "datetime")
    private String creationDate;
    @Column(columnDefinition = "text")
    private String comment;
    @Column(columnDefinition = "money")
    private String valuationPrice;
    @OneToOne
    private ValuationNote valuationNoteId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private DiamondValuationStatus diamondValuationStatus;
}
