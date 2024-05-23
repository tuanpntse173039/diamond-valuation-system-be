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
public class ServicePriceList {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(columnDefinition = "float")
    private float minSize;
    @Column(columnDefinition = "float")
    private float maxSize;
    @Column(columnDefinition = "money")
    private String price;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

}
