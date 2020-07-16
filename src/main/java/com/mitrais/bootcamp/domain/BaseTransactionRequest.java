/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: BaseTransactionRequest.java, v 0.1 2020‐07‐16 9:52 Aji Atin Mulyadi Exp $$
 */
public class BaseTransactionRequest {
    protected long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}