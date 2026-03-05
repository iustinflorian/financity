package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.TransactionResponseDTO;
import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.Transaction;
import com.gifprojects.financity.repository.AccountRepository;
import com.gifprojects.financity.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private AccountRepository accountRepository;
    private Mapper mapper;

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> transactionsByUserIdAndIban(Long id, String iban){
        Account acc = accountRepository.findByIban(iban)
                .orElseThrow(() -> new RuntimeException("No account with this iban."));
        if (!acc.getOwner().getId().equals(id)) {
            throw new RuntimeException("Unauthorized access: You do not own this account.");
        }

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.addAll(acc.getIncomingTransactions());
        transactionList.addAll(acc.getOutgoingTransactions());

        transactionList.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));

        return transactionList.stream()
                .map(transaction -> mapper.mapToResponseDTO(transaction))
                .toList();
    }
}
