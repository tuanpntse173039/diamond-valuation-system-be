package com.letitbee.diamondvaluationsystem.entity;

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
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(1000)")
    private String token;
    @Column(columnDefinition = "datetime")
    private Date expiryDate;
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
