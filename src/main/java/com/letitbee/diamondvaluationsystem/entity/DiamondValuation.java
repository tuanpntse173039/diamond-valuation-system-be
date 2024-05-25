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
    @JoinColumn(name = "valuation_note_id", nullable = false)
    private ValuationNote valuationNote;

    @Column(columnDefinition = "datetime")
    private String creationDate;

    @Column(columnDefinition = "text")
    private String comment;

    @Column(columnDefinition = "money")
    private String valuationPrice;

    @OneToOne(mappedBy = "diamondValuation")
    private ValuationNote valuationNoteId;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "valuation_staff_id", nullable = false)
    private Staff staff;
}
