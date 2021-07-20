package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Accounts {

    private BigDecimal balance;
    private long userId;
    private long accountId;

    public Accounts() {}

    public Accounts(BigDecimal balance, Integer accountId) {
        this.balance = balance;
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
