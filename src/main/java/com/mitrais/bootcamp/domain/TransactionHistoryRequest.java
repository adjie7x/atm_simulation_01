package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionHistoryRequest.java, v 0.1 2020‐07‐29 23:22 Aji Atin Mulyadi Exp $$
 */
public class TransactionHistoryRequest extends BaseTransactionRequest {

    private int limit;
    private String accountNumber;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}