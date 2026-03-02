package com.gifprojects.financity.service;

import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.Transaction;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.TransactionRepository;
import com.gifprojects.financity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    private AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void transferMoney(String fromIban, String toIban, BigDecimal amount){
        try{
            Account fromAcc = accountRepository.findByIban(fromIban)
                    .orElseThrow(() -> new RuntimeException("Account not found!"));

            Account toAcc = accountRepository.findByIban(toIban)
                    .orElseThrow(() -> new RuntimeException("Account not found!"));

            if(fromAcc.getBalance().compareTo(amount) < 0){
                throw new RuntimeException("Insufficient funds!");
            }

            fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
            toAcc.setBalance(toAcc.getBalance().add(amount));

            Transaction tx = Transaction.builder()
                    .fromAccount(fromAcc)
                    .toAccount(toAcc)
                    .amount(amount)
                    .build();

            transactionRepository.save(tx);

            accountRepository.save(fromAcc);
            accountRepository.save(toAcc);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
