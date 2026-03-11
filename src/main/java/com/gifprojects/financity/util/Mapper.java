package com.gifprojects.financity.util;

import com.gifprojects.financity.datamap.acc.AccountResponseDTO;
import com.gifprojects.financity.datamap.trx.TransactionResponseDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.Transaction;
import com.gifprojects.financity.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public AccountResponseDTO mapToResponseDTO(Account account){
        AccountResponseDTO response = new AccountResponseDTO();
        response.setId(account.getId());
        response.setIban(account.getIban());
        response.setBalance(account.getBalance());
        response.setName(account.getName());
        response.setAccountType(account.getAccountType().toString());
        return response;
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        if (user == null) return null;
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());

        if (user.getAccounts() != null) {
            response.setAccounts(user.getAccounts().stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList()));
        } else {
            response.setAccounts(Collections.emptyList());
        }
        return response;
    }

    public TransactionResponseDTO mapToResponseDTO(Transaction transaction){
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setCreatedAt(transaction.getCreatedAt());

        if (transaction.getFromAccount() != null) {
            response.setFromIban(transaction.getFromAccount().getIban());
        } else {
            response.setFromIban("CASH_DEPOSIT");
        }

        if (transaction.getToAccount() != null) {
            response.setToIban(transaction.getToAccount().getIban());
        } else {
            response.setToIban("CASH_WITHDRAWAL");
        }

        return response;
    }

}
