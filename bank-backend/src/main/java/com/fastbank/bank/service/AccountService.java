package com.fastbank.bank.service;

import com.fastbank.bank.model.Account;
import com.fastbank.bank.model.AccountLog;
import com.fastbank.bank.repository.AccountRepository;
import com.fastbank.bank.repository.TransactionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepo, TransactionRepository transactionRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAccount(Account account) {
        String hashed = passwordEncoder.encode(account.getPassword());
        account.setPassword(hashed);
        account.setBalance(0);
        Account createdAccount = accountRepo.save(account);
        transactionRepo.save(createdAccount.getId());
    }

    public boolean accountExists(Account account) {
        if (accountRepo.findByEmail(account.getEmail()) != null) {
            return true;
        }
        return false;
    }

    public List<AccountLog> getAllAccounts() {
        return accountRepo.findAll();
    }
}