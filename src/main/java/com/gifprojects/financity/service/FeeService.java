package com.gifprojects.financity.service;

import com.gifprojects.financity.model.AccountType;
import com.gifprojects.financity.util.FeeCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class FeeService {
    private final List<FeeCalculator> calculators;

    public BigDecimal getFee(AccountType type, BigDecimal amount){
        return calculators.stream()
                .filter(calculator -> calculator.supports(type))
                .findFirst()
                .map(calculator -> calculator.calculate(amount))
                .orElse(BigDecimal.ZERO);
    }
}
