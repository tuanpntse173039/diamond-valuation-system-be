package com.letitbee.diamondvaluationsystem.entity;


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
@Table
@Entity
public class Account {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(nullable = false,columnDefinition = "nvarchar(50)",unique = true)
    private String name;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String password;
    @Column(columnDefinition = "bit default 1")
    private Boolean is_active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne
    private Staff staff;

    @OneToOne
    private Customer customer;



}
