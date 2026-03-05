package com.gifprojects.financity.servicetest;

import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.TransactionRepository;
import com.gifprojects.financity.repository.UserRepository;
import com.gifprojects.financity.service.AccountService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testConcurrentWithdrawals() throws InterruptedException {
        User owner = User.builder().username("test").email("test@gmail.com").password("1234").build();
        userRepository.save(owner);
        Account acc = Account.builder().owner(owner).iban("1234").balance(new BigDecimal("0.00")).build();
        accountRepository.save(acc);
        String testIban = acc.getIban();

        accountService.deposit(testIban, new BigDecimal("1000.00"));

        int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i  = 0; i < numberOfThreads; i++){
            int index = i + 1;
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        accountService.withdraw(testIban, new BigDecimal("100.00"));
                        System.out.println("Execution thread " + index + " successfully completed the task!");
                    } catch (Exception e){
                        System.err.println("Execution thread " + index + " failed: " + e.getMessage());
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Sold final: " + accountRepository.findByIban(testIban).get().getBalance());
    }

    @Test
    public void transferMoneyTest(){
        // 1. given
        User user1 = userRepository.save(User.builder()
                .username("testUser1")
                .email("test1@mail.com")
                .password("1234")
                .build());

        User user2 = userRepository.save(User.builder()
                .username("testUser2")
                .email("test2@mail.com")
                .password("1234")
                .build());

        Account acc1 = accountRepository.save(Account.builder()
                .iban("RO03")
                .balance(new BigDecimal("1000.00"))
                .owner(user1)
                .build());

        Account acc2 = accountRepository.save(Account.builder()
                .iban("RO04")
                .balance(new BigDecimal("500.00"))
                .owner(user2)
                .build());

        // 2. when we transfer
        BigDecimal transferAmount = new BigDecimal("300.00");
        accountService.transferMoney(acc1.getIban(),acc2.getIban(),transferAmount);

        // 3. this should happen
        Assertions.assertEquals(0, new BigDecimal("700.00").compareTo(acc1.getBalance()));
        Assertions.assertEquals(0, new BigDecimal("800.00").compareTo(acc2.getBalance()));

        long transactionCount = transactionRepository.count();
        Assertions.assertEquals(1, transactionCount, "Exactly one transaction!");
    }

    @Test
    @Transactional
    void testTransactionHistory() {
        accountService.transferMoney("RO03", "RO04", new BigDecimal("100"));

        Account source = accountRepository.findByIban("RO03")
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        Assertions.assertEquals(1, source.getOutgoingTransactions().size());
    }

    @AfterEach
    void delete(){
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
}
