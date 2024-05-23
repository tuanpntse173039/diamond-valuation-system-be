package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestStatus;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ValuationRequest {

    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private long id;
    @Column(columnDefinition = "datetime")
    private String creationDate;
    @Column(columnDefinition = "datetime")
    private String receiptDate;
    @Column(columnDefinition = "datetime")
    private String returnDate;
    @Column(columnDefinition = "int", nullable = false)
    private int diamondAmount;
    @Column(columnDefinition = "money")
    private String totalPrice;
    @Column(columnDefinition = "varchar(1000)")
    private String receiptLink;
    @Column(columnDefinition = "varchar(1000)")
    private String returnLink;
    @Column(columnDefinition = "text")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    //Many To One
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "valuation_request_status_id", nullable = false)
    private ValuationRequestStatus valuationRequestStatus;

    @OneToMany(mappedBy = "valuationRequest")
    private Set<ValuationNote> valuationNotes = new HashSet<>();

    @OneToMany(mappedBy = "valuationRequest")
    private Set<Diamond> diamonds = new HashSet<>();

}
