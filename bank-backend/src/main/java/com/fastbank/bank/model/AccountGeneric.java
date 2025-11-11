package com.fastbank.bank.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AccountGeneric {


    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    private double balance = 0.0;
    
    public AccountGeneric() {}

    public AccountGeneric(String email) {
        this.email = email;
    }
    public AccountGeneric(String email, double balance) {

        this.email = email;
        this.balance = balance;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

}