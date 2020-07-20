/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: FundTransferResponse.java, v 0.1 2020‐07‐16 17:13 Aji Atin Mulyadi Exp $$
 */
public class FundTransferResponse implements Transactionable {

    private ATMData srcAccount;
    private ATMData destAccount;

    public ATMData getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(ATMData srcAccount) {
        this.srcAccount = srcAccount;
    }

    public ATMData getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(ATMData destAccount) {
        this.destAccount = destAccount;
    }
}