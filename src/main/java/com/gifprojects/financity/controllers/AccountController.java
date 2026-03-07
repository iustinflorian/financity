package com.gifprojects.financity.controllers;

import com.gifprojects.financity.datamap.acc.AccountCreateRequestDTO;
import com.gifprojects.financity.datamap.acc.AccountResponseDTO;
import com.gifprojects.financity.datamap.acc.AccountTransferDTO;
import com.gifprojects.financity.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/createacc")
    public AccountResponseDTO createAccount(@RequestBody AccountCreateRequestDTO data){
        return accountService.createAccount(data);
    }

    @GetMapping("/{accountId}")
    public AccountResponseDTO getAccount(@PathVariable Long accountId){
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/{userId}/acc")
    public List<AccountResponseDTO> getAccountList(@PathVariable Long userId){
        return accountService.getAccountsByUser(userId);
    }

    @PostMapping("/{accountId}/deposit")
    public void deposit(@PathVariable Long accountId, @RequestBody BigDecimal amount){
        accountService.deposit(accountId, amount);
    }

    @PostMapping("/{accountId}/withdraw")
    public void withdraw(@PathVariable Long accountId, @RequestBody BigDecimal amount){
        accountService.withdraw(accountId, amount);
    }

    @PostMapping("/{accountId}/transfer")
    public void transfer(@PathVariable Long accountId, @RequestBody AccountTransferDTO data){
        accountService.transferMoney(accountId, data);
    }

}
