package com.gifprojects.financity.controllers;

import com.gifprojects.financity.datamap.trx.TransactionResponseDTO;
import com.gifprojects.financity.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trx")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping("/{userId}/{accountId}")
    public List<TransactionResponseDTO> getTransactions(
            @PathVariable Long userId,
            @PathVariable Long accountId){
        return transactionService.transactionsById(userId, accountId);
    }

}
