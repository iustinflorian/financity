package com.gifprojects.financity.datamap.acc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferDTO {
    String toIban;
    BigDecimal amount;
}
