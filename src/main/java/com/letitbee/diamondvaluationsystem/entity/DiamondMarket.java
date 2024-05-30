package com.letitbee.diamondvaluationsystem.entity;

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
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String origin;
    @Column(nullable = false)
    private float caratWeight;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String color;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String clarity;
    @Column(nullable = false)
    private double cutScore;
    @Column(columnDefinition = "money", nullable = false)
    private double price;

    @OneToMany(mappedBy = "diamondMarket"
               , cascade = CascadeType.ALL
            , orphanRemoval = true)
    private Set<Supplier> supplier = new HashSet<>();
}
