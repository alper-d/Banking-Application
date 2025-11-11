package com.fastbank.bank.repository;

import com.fastbank.bank.model.Transaction;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@Repository
public class TransactionRepository {

    private final Map<Long, Deque<Transaction>> transactionsMap = new HashMap<>();
    private static final int MAX_TRANSACTIONS = 50;

    public void save(long id) {
        if (transactionsMap.get(id) == null) {
            transactionsMap.put(id, new ArrayDeque<>());
            //account.setId(UUID.randomUUID().toString());
        }
    }

    public void addTransaction(long id, Transaction transaction) {
        try{
            Deque<Transaction> outgoingTransactions = transactionsMap.get(id);
            if (outgoingTransactions.size() == MAX_TRANSACTIONS) {
                outgoingTransactions.removeFirst();
            }
            transaction.setId(generateId(transaction.getToEmail(), transaction.getTransactionTime()));
            outgoingTransactions.addLast(transaction);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating userId", e);
        }
    }
    public Deque<Transaction> getTransactions(long id) {
        return transactionsMap.get(id);
    }
    public static String generateId(String toEmail, ZonedDateTime creationDate) throws NoSuchAlgorithmException {
        String input = toEmail + creationDate;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return String.valueOf(ByteBuffer.wrap(hash).getLong() & 0x7FFFFFFFFFFFFFFFL);
    }

}
