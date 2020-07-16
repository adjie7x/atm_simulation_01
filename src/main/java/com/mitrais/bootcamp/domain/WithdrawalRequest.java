/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WithdrawalRequest.java, v 0.1 2020‐07‐16 9:51 Aji Atin Mulyadi Exp $$
 */
public class WithdrawalRequest extends BaseTransactionRequest {

    private long accountNumber;

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}