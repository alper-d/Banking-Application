package com.fastbank.bank.model;
import java.time.ZonedDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;



public class Transaction {
    private String id;
    private ZonedDateTime transactionTime;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String toEmail;
    @Positive(message = "Amount must be greater than 0.")
    private double amount;

    public Transaction() {
        this.transactionTime = null;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public ZonedDateTime getTransactionTime() { return transactionTime; }
    public void setTransactionTime(ZonedDateTime transactionTime) { this.transactionTime = transactionTime; }
}
