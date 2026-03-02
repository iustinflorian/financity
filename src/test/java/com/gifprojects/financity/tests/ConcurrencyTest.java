package com.gifprojects.financity.tests;

import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testOptimisticLocking() throws InterruptedException {

        User user = User.builder()
                .username("vasile3")
                .email("vasile3@gmail.com")
                .password("123")
                .build();

        userRepository.save(user);

        // 1. this is given
        Account initialAccount = Account.builder()
                .iban("RO01")
                .owner(user)
                .balance(new BigDecimal("200.00"))
                .build();
        Account savedAccount = accountRepository.save(initialAccount);
        Long accountId = savedAccount.getId();

        Thread t1 = new Thread(() -> {
            try {
                Account acc1 = accountRepository.findById(accountId)
                        .orElseThrow(() -> new EntityNotFoundException("Account with ID: " + accountId + "doesn't exist."));
                Thread.sleep(1500);
                acc1.setBalance(acc1.getBalance().subtract(new BigDecimal("100.00")));
                accountRepository.save(acc1);
                System.out.println("Thread 1: Successfully saved (This shouldn't happen)");
            } catch (ObjectOptimisticLockingFailureException e){
                System.out.println("Thread 1: Someone else modified the account (Correct): " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
           try {
               Thread.sleep(500);
               Account acc2 = accountRepository.findById(accountId)
                       .orElseThrow(() -> new EntityNotFoundException("Account with ID: " + accountId + "doesn't exist."));
               acc2.setBalance(acc2.getBalance().subtract(new BigDecimal("50.00")));
               accountRepository.save(acc2);
               System.out.println("Thread 2: Successfully saved (Correct)");
           } catch (Exception e){
               throw new RuntimeException(e);
           }
        });

        // 2. when this happens (start the threads and wait for them to finish)
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // 3. this should happen
        Account finalAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID: " + accountId + "doesn't exist."));

        System.out.println("Final balance: " + finalAccount.getBalance());
        System.out.println("Final version in DB: " + finalAccount.getVersion());

        Assertions.assertEquals(0, new BigDecimal("150.00").compareTo(finalAccount.getBalance()));
        Assertions.assertEquals(1, finalAccount.getVersion());
    }
}
