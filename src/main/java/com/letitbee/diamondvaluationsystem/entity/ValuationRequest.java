package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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
    @Column(columnDefinition = "datetime", nullable = false)
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

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staff_id", nullable = true)
    private Staff staff;

    //Many To One
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @OneToMany(mappedBy = "valuationRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Payment> payment = new HashSet<>();

    @OneToMany(mappedBy = "valuationRequest", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ValuationRequestDetail> valuationRequestDetails = new HashSet<>();

}
