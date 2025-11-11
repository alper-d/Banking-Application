package com.fastbank.bank.controller;

import com.fastbank.bank.model.Deposit;
import com.fastbank.bank.model.Withdraw;
import com.fastbank.bank.model.Transaction;
import com.fastbank.bank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@Valid @RequestBody Transaction transaction) {
        try {
            String senderEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            service.sendMoney(senderEmail, transaction);
            return ResponseEntity.status(HttpStatus.OK).body("Transfer Completed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody Deposit deposit) {
        try {
            String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            if(deposit.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit Amount Should be Greater Than Zero!");
            }
            service.depositMoney(recipientEmail, deposit);
            return ResponseEntity.status(HttpStatus.OK).body("Deposit Completed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody Withdraw withdraw) {
        try {
            String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            service.withdrawMoney(recipientEmail, withdraw);
            return ResponseEntity.status(HttpStatus.OK).body("Withdraw Completed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        try {
            String recipientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("balance", service.getBalance(recipientEmail)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
