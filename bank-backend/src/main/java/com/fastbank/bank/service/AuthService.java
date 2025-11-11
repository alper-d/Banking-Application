package com.fastbank.bank.service;

import com.fastbank.bank.model.Account;
import com.fastbank.bank.repository.AccountRepository;
import com.fastbank.bank.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AccountRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(AccountRepository repo) {
        this.repo = repo;
    }

    public String authenticate(String username, String password) {
        Account account = repo.findByEmail(username);
        if (account != null) {
            if (encoder.matches(password, account.getPassword())) {
                String token = JwtUtil.generateToken(username);
                return token;
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        }
        throw new RuntimeException("Invalid credentials");
    }
}
