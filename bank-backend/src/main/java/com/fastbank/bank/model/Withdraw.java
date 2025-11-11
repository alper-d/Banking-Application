package com.fastbank.bank.model;

import jakarta.validation.constraints.Positive;

public class Withdraw {
    @Positive(message = "Amount must be greater than 0.")
    double amount;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
