package com.gifprojects.financity.util;

import com.gifprojects.financity.model.AccountType;

import java.math.BigDecimal;

public interface FeeCalculator {
    BigDecimal calculate(BigDecimal amount);
    boolean supports(AccountType type);
}
