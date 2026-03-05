package com.gifprojects.financity.util.FeeImplement;

import com.gifprojects.financity.model.AccountType;

import java.math.BigDecimal;

public class CurrentFeeCalculator {
    public BigDecimal calculate(BigDecimal amount){
        return amount.multiply(new BigDecimal("0.01"));
    }
    public boolean supports(AccountType type){
        return type == AccountType.CURRENT;
    }
}
