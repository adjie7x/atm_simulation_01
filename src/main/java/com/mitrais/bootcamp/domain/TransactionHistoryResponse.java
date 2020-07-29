package com.mitrais.bootcamp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionHistoryResponse.java, v 0.1 2020‐07‐29 23:23 Aji Atin Mulyadi Exp $$
 */
public class TransactionHistoryResponse implements Transactionable {

    private List<TransactionDTO> transactions = new ArrayList<>();

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}