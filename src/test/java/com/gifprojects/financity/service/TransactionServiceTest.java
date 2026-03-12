package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.trx.TransactionResponseDTO;
import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.TransactionRepository;
import com.gifprojects.financity.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Test
    void testTransactionHistoryOrdering(){
        //given
        User owner = User.builder().username("test").email("test@gmail.com").password("1234").build();
        userRepository.save(owner);
        Account acc = Account.builder().owner(owner).iban("1234").balance(new BigDecimal("0.00")).build();
        accountRepository.save(acc);
        accountService.deposit(acc.getId(), new BigDecimal("100.0"));
        accountService.withdraw(acc.getId(), new BigDecimal("50.0")); // newer transaction

        //when
        List<TransactionResponseDTO> history = transactionService.transactionsById(owner.getId(),acc.getId());

        //this should happen - newest transaction should be first
        Assertions.assertEquals(2, history.size());
        Assertions.assertEquals("CASH_WITHDRAWAL", history.get(0).getToIban());
        Assertions.assertTrue(history.get(0).getCreatedAt().after(history.get(1).getCreatedAt()));
    }

    @AfterEach
    void delete(){
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
}
