package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.RecordType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Record {
    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private Long id;

    @Column(columnDefinition = "text")
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
