package com.gifprojects.financity.datamap.acc;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    Long id;
    String name;
    String accountType;
    String iban;
    BigDecimal balance;
}
