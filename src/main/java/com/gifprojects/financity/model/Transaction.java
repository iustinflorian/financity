package com.gifprojects.financity.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = true)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = true)
    private Account toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    private Date createdAt;
}
