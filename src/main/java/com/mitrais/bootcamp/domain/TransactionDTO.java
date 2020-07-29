package com.mitrais.bootcamp.domain;

import com.mitrais.bootcamp.enums.MutationType;
import com.mitrais.bootcamp.enums.TransactionType;

import java.time.LocalDateTime;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Transaction.java, v 0.1 2020‐07‐29 13:55 Aji Atin Mulyadi Exp $$
 */
public class TransactionDTO {

    private Account srcAccount;
    private Account destAccount;
    private long amount;
    private LocalDateTime transactionDate;
    private MutationType mutationType;
    private TransactionType transactionType;

    public TransactionDTO(Account srcAccount, Account destAccount, long amount, LocalDateTime transactionDate, MutationType mutationType, TransactionType transactionType) {
        this.srcAccount = srcAccount;
        this.destAccount = destAccount;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.mutationType = mutationType;
        this.transactionType = transactionType;
    }

    public TransactionDTO(Account srcAccount, long amount, LocalDateTime transactionDate, MutationType mutationType, TransactionType transactionType) {
        this.srcAccount = srcAccount;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.mutationType = mutationType;
        this.transactionType = transactionType;
    }

    public TransactionDTO() {
    }

    public Account getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(Account srcAccount) {
        this.srcAccount = srcAccount;
    }

    public Account getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(Account destAccount) {
        this.destAccount = destAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}