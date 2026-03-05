package com.gifprojects.financity.util.FeeImplement;

import com.gifprojects.financity.model.AccountType;

import java.math.BigDecimal;

public class SavingsFeeCalculator {
    public BigDecimal calculate(BigDecimal amount){
        return BigDecimal.ZERO;
    }
    public boolean supports(AccountType type){
        return type == AccountType.SAVINGS;
    }
}
