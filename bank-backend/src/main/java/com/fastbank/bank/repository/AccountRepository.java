package com.fastbank.bank.repository;

import com.fastbank.bank.model.Account;
import com.fastbank.bank.model.AccountLog;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Repository
public class AccountRepository {

    private final Map<Long, Account> accounts = new HashMap<>();
    private final Map<String, Long> emailIndex = new HashMap<>();

    public Account save(Account account) {
        try {

            account.setId(generateId(account.getEmail(), ZonedDateTime.now(ZoneId.systemDefault())));
            accounts.put(account.getId(), account);
            emailIndex.put(account.getEmail(), account.getId());
            return account;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating userId", e);
        }
    }
    public static long generateId(String email, ZonedDateTime creationDate) throws NoSuchAlgorithmException {
        String input = email + creationDate;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return ByteBuffer.wrap(hash).getLong() & 0x7FFFFFFFFFFFFFFFL;
    }
    public Account findById(long id) {
        Account found = accounts.get(id);
        return found != null ? found : null;
    }

    public Account findByEmail(String email) {
        return accounts.values().stream()
                .filter(acc -> acc.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void updateBalance(String email, double balance) {
        accounts.get(emailIndex.get(email)).setBalance(balance);
    }

    public List<AccountLog> findAll() {
        //return new ArrayList<>(accounts.values());
        return accounts.values().stream().map(acc -> new AccountLog(
                acc.getId(),
                acc.getEmail(),
                acc.getBalance()
            ))
            .collect(Collectors.toList());
    }
    
    public void deleteById(long id) {
        String email = accounts.get(id).getEmail();
        accounts.remove(id);
        emailIndex.remove(email);
        return;
    }

}
