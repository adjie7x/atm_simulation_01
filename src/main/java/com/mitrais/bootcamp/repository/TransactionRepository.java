package com.mitrais.bootcamp.repository;

import com.mitrais.bootcamp.domain.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionRepository.java, v 0.1 2020‐07‐29 22:45 Aji Atin Mulyadi Exp $$
 */
public class TransactionRepository {
    private List<TransactionDTO> transactionList = new ArrayList<TransactionDTO>();

    public List<TransactionDTO> getAllTransaction(){
        return this.transactionList;
    }

    public void addNew(TransactionDTO transaction){
        this.transactionList.add(transaction);
    }

    public void addAll(List<TransactionDTO> transactions){
        this.transactionList.addAll(transactions);
    }

}