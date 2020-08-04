/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMData.java, v 0.1 2020‐07‐06 15:01 Aji Atin Mulyadi Exp $$
 */
public class Account implements Comparable<Account> {

    private String name;
    private String pin;
    private long balance;
    private String accountNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccountNumber().compareTo(o.getAccountNumber());
    }

    public void deduct(long amount) {
        this.balance = this.balance - amount;
    }

    public void increase(long amount) {
        this.balance = this.balance + amount;
    }
}