package com.gifprojects.financity.datamap.trx;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionResponseDTO {
    Long id;
    BigDecimal amount;
    Date createdAt;
    String fromIban;
    String toIban;
}
