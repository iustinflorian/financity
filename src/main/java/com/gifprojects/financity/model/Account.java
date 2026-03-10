package com.gifprojects.financity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private User owner; // name should be 'owner' as in User.java

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType = AccountType.CURRENT;

    @Column(unique = true, nullable = false)
    private String iban;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @Version
    /*
     optimistic locking - jpa automatically checks the version of the row that changed
     between read and write and throws an error to prevent data corruption
    */
    private Integer version;
}
