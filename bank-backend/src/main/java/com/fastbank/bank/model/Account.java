package com.fastbank.bank.model;

import jakarta.validation.constraints.NotBlank;

public class Account extends AccountGeneric {
    @NotBlank(message = "Password is required")
    private String password;
    private long id;

    public Account() {}

    public Account(String email, String password) {
        super(email);
        this.password = password;
    }
    
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}