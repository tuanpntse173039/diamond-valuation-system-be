package com.letitbee.diamondvaluationsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "Role"
)
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(name = "name",nullable = false, columnDefinition = "nvarchar(20)")
    private String name;
    @Column(name = "description",nullable = true)
    private String description;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private Set<Account> accounts = new HashSet<>();
}