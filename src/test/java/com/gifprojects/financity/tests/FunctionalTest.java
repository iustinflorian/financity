package com.gifprojects.financity.tests;

import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
public class FunctionalTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldSaveUserCorrectly() {
        // 1. this is given
        User user = User.builder()
                .username("vasile2")
                .email("vasile2@gmail.com")
                .password("123")
                .build();

        // 2. when this happens
        User savedUser = userRepository.save(user);

        // 3. this should also happen
        Assertions.assertNotNull(savedUser.getId(),"Database should generate an ID!");
        User found = userRepository.findById(savedUser.getId())
                        .orElseThrow(() -> new RuntimeException("No user with this ID found"));

        Assertions.assertEquals("vasile2", found.getUsername());
        Assertions.assertEquals("vasile2@gmail.com", found.getEmail());

        System.out.println("User successfully saved in MySQL database! ID: " + found.getId());
    }

    @Test
    @Transactional
    public void shouldTransferCorrectly(){
        User user = User.builder()
                .username("vasile2")
                .email("vasile2@gmail.com")
                .password("123")
                .build();
        userRepository.save(user);

        Account a1 = Account.builder()
                .owner(user)
                .iban("RO01")
                .balance(new BigDecimal("1000.00"))
                .build();
        Account a2 = Account.builder()
                .owner(user)
                .iban("RO02")
                .balance(new BigDecimal("500.00"))
                .build();
        accountRepository.save(a1);
        accountRepository.save(a2);

        BigDecimal transferSum = new BigDecimal("230.00");

        a1.setBalance(a1.getBalance().subtract(transferSum));
        a2.setBalance(a2.getBalance().add(transferSum));

        accountRepository.save(a1);
        accountRepository.save(a2);

        Assertions.assertEquals(0, new BigDecimal("770.00").compareTo(a1.getBalance()));
        Assertions.assertEquals(0, new BigDecimal("730.00").compareTo(a2.getBalance()));
    }

}
