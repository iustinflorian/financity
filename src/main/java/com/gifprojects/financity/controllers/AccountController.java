package com.gifprojects.financity.controllers;

import com.gifprojects.financity.datamap.acc.AccountCreateRequestDTO;
import com.gifprojects.financity.datamap.acc.AccountResponseDTO;
import com.gifprojects.financity.datamap.acc.AccountTransferDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import com.gifprojects.financity.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreateRequestDTO data){
        AccountResponseDTO response = accountService.createAccount(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable Long accountId){
        AccountResponseDTO response = accountService.getAccountById(accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}/acc")
    public ResponseEntity<List<AccountResponseDTO>> getAccountList(@PathVariable Long userId){
        List<AccountResponseDTO> response = accountService.getAccountsByUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @NonNull @RequestBody BigDecimal amount){
        accountService.deposit(accountId, amount);
        return new ResponseEntity<>("Deposit success!", HttpStatus.OK);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @NonNull @RequestBody BigDecimal amount){
        accountService.withdraw(accountId, amount);
        return new ResponseEntity<>("Withdraw success!", HttpStatus.OK);
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<String> transfer(@PathVariable Long accountId, @NonNull @RequestBody AccountTransferDTO data){
        accountService.transferMoney(accountId, data);
        return new ResponseEntity<>("Transfer success!", HttpStatus.OK);
    }

}
