package com.fastbank.bank.service;

import com.fastbank.bank.model.Account;
import com.fastbank.bank.model.Deposit;
import com.fastbank.bank.model.Transaction;
import com.fastbank.bank.model.Withdraw;
import com.fastbank.bank.repository.AccountRepository;
import com.fastbank.bank.repository.TransactionRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Deque;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void sendMoney(String senderEmail, Transaction transaction) {
        Account sender = accountRepository.findByEmail(senderEmail);
        Account recipient = accountRepository.findByEmail(transaction.getToEmail());
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient does not exist");
        }
        if (sender.getEmail().equalsIgnoreCase(recipient.getEmail())) {
            throw new IllegalArgumentException("You cannot send money to same account.");
        }
        double amount = transaction.getAmount();
        double senderBalance = sender.getBalance();
        if (senderBalance < amount) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Transfer Amount Should be Greater Than Zero.");
        }

        sender.setBalance(senderBalance - amount);
        double recipientBalance = recipient.getBalance();
        recipient.setBalance(recipientBalance + amount);
        transaction.setTransactionTime(ZonedDateTime.now(ZoneId.systemDefault()));
        transactionRepository.addTransaction(sender.getId(), transaction);
    }

    public void depositMoney(String recipientEmail, Deposit deposit) {
        Account recipient = accountRepository.findByEmail(recipientEmail);
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient does not exist");
        }
        double amount = deposit.getAmount();

        recipient.setBalance(recipient.getBalance() + amount);
    }

    public void withdrawMoney(String recipientEmail, Withdraw withdraw) throws IllegalArgumentException{
        Account recipient = accountRepository.findByEmail(recipientEmail);
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient does not exist");
        }
        if(withdraw.getAmount() <= 0) {
            throw new IllegalArgumentException("Withdraw Amount Should be Greater Than Zero.");
        }
        double amount = withdraw.getAmount();
        if (recipient.getBalance() < amount) {
            throw new IllegalArgumentException("Balance Insufficient.");
        }
        recipient.setBalance(recipient.getBalance() - amount);
    }
    
    public double getBalance(String recipientEmail) {
        Account recipient = accountRepository.findByEmail(recipientEmail);
        return recipient.getBalance();
    }

    public Deque<Transaction> getTransactionsForAccount(long accountId) {
        return transactionRepository.getTransactions(accountId);
    }
}
