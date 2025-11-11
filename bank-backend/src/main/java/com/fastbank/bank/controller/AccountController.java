package com.fastbank.bank.controller;

import com.fastbank.bank.model.Account;
import com.fastbank.bank.model.AccountLog;
import com.fastbank.bank.model.Transaction;
import com.fastbank.bank.service.AccountService;
import com.fastbank.bank.service.TransactionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Deque;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService; 
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody Account request) {
        try {
            if (accountService.accountExists(request)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Account Exists");
            }
            accountService.createAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public List<AccountLog> getAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        Deque<Transaction> transactions = transactionService.getTransactionsForAccount(id);
        List<Transaction> list = new ArrayList<>(transactions);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
}
