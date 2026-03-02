package com.gifprojects.financity.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // lazy loading for performance
    @JoinColumn(name = "user_id", nullable = false)
    private User owner; // name should be 'owner' as in User.java

    @Column(unique = true, nullable = false)
    private String iban;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    /*
     optimistic locking - jpa automatically checks the version of the row that changed
     between read and write and throws an error to prevent data corruption
    */
    private Integer version;
}
