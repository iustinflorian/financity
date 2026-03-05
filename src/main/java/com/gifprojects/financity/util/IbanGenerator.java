package com.gifprojects.financity.util;

import com.gifprojects.financity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IbanGenerator {
    private final AccountRepository accountRepository;

    public String generateIban(){
        String newIban;
        boolean exists = false;
        do {
            String countryCode = "RO";
            String bankCode = "FCTY";
            String randomDigits = String.format("%02d", new Random().nextInt(100));
            String accountPart = UUID.randomUUID().toString().replace("-","").substring(0, 16).toUpperCase();

            newIban = countryCode + randomDigits + bankCode + accountPart;
            exists = accountRepository.existsByIban(newIban);
        } while (exists);
        return newIban;
    }
}
