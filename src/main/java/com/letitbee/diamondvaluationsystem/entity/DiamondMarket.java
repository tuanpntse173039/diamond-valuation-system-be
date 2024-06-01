package com.letitbee.diamondvaluationsystem.entity;

import com.letitbee.diamondvaluationsystem.enums.Clarity;
import com.letitbee.diamondvaluationsystem.enums.Color;
import com.letitbee.diamondvaluationsystem.enums.DiamondOrigin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class DiamondMarket {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String diamondImage;
    @Column(nullable = false)
    private float caratWeight;
    @Enumerated(EnumType.STRING)
    private DiamondOrigin diamondOrigin;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Clarity clarity;
    @Column(nullable = false)
    private double cutScore;
    @Column(columnDefinition = "money", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}