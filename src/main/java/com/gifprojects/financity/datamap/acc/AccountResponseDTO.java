package com.gifprojects.financity.datamap.acc;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    Long id;
    String iban;
    BigDecimal balance;
}
