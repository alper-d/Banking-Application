package com.fastbank.bank.model;

public class AccountLog extends AccountGeneric {

    private String id;

    public AccountLog() {}

    public AccountLog(long id, String email, double balance) {
        super(email, balance);
        this.id = String.valueOf(id);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public void setId(long id) { this.id = String.valueOf(id); }
    

}