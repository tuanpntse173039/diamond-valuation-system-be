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

public class DiamondValuationAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "valuation_request_detail_id", nullable = false)
    private ValuationRequestDetail valuationRequestDetail;

    @Column(columnDefinition = "datetime")
    private String creationDate;

    @Column(columnDefinition = "text")
    private String comment;

    @Column(columnDefinition = "money")
    private String valuationPrice;

    @OneToOne(mappedBy = "diamondValuationAssign")
    private ValuationRequestDetail valuationRequestDetailId;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "valuation_staff_id", nullable = false)
    private Staff staff;
}
