package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.RecordType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Record {
    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private Long id;

    @Column(columnDefinition = "varchar(1000)")
    private String link;

    @Column(columnDefinition = "datetime", nullable = false)
    private Date creationDate;

    @Column(columnDefinition = "bit")
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private RecordType type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "valuation_id", nullable = false)
    private ValuationRequest valuationRequest;
}
