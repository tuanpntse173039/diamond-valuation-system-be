package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class PaymentMethod {

    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private long id;
    @Column(columnDefinition = "varchar(50)",nullable = false)
    private String paymentModeName;
    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}
