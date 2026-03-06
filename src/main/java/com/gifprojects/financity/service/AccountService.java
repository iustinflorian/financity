package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.acc.AccountCreateRequestDTO;
import com.gifprojects.financity.datamap.acc.AccountResponseDTO;
import com.gifprojects.financity.datamap.acc.AccountTransferDTO;
import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.AccountType;
import com.gifprojects.financity.model.Transaction;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.repository.TransactionRepository;
import com.gifprojects.financity.repository.UserRepository;
import com.gifprojects.financity.util.IbanGenerator;
import com.gifprojects.financity.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final IbanGenerator ibanGenerator;
    private final Mapper mapper;

    @Transactional
    public AccountResponseDTO createAccount(AccountCreateRequestDTO data){
        User owner = userRepository.findById(data.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User account not found!"));
        String newIban = ibanGenerator.generateIban();

        Account account = Account.builder()
                .owner(owner)
                .iban(newIban)
                .balance(new BigDecimal("0.00"))
                .accountType(AccountType.CURRENT)
                .build();
        Account savedAccount = accountRepository.save(account);
        return mapper.mapToResponseDTO(savedAccount);
    }

    public AccountResponseDTO getAccountById(Long id){
        Account savedAccount = accountRepository.getAccountById(id);
        return mapper.mapToResponseDTO(savedAccount);
    }

    @Transactional
    public void deposit(Long id, BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Amount must be a positive number!");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with this iban."));
        account.setBalance(account.getBalance().add(amount));

        Transaction tx = Transaction.builder()
                .toAccount(account)
                .amount(amount)
                .build();
        transactionRepository.save(tx);
    }

    @Transactional
    public void withdraw(Long id, BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Amount must be a positive number!");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No account found with this iban."));
        if (account.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient funds! Current account balance: " + account.getBalance());
        }
        account.setBalance(account.getBalance().subtract(amount));

        Transaction tx = Transaction.builder()
                .fromAccount(account)
                .amount(amount)
                .build();
        transactionRepository.save(tx);
    }

    @Transactional
    public void transferMoney(Long id, AccountTransferDTO data){
        if (data.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Amount must be a positive number!");
        }

        Account fromAcc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        if(fromAcc.getBalance().compareTo(data.getAmount()) < 0){
            throw new RuntimeException("Insufficient funds!");
        }

        Account toAcc = accountRepository.findByIban(data.getToIban())
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        if(fromAcc.equals(toAcc)){
            throw new RuntimeException("Cannot transfer money to the same account!");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(data.getAmount()));
        toAcc.setBalance(toAcc.getBalance().add(data.getAmount()));

        Transaction tx = Transaction.builder()
                .fromAccount(fromAcc)
                .toAccount(toAcc)
                .amount(data.getAmount())
                .build();

        transactionRepository.save(tx);
        //eliminated .save() as Hibernate (Dirty Checking activated by @Transactional)
        // already updates to fromAcc and toAcc.
    }

}
