package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestStatus;
import java.util.Date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    //Many To One
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "valuationrequeststatus_id", nullable = false)
    private ValuationRequestStatus status;

}
