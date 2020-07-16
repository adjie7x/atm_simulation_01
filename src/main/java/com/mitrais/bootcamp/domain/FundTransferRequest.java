/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: FundTransferRequest.java, v 0.1 2020‐07‐15 8:15 Aji Atin Mulyadi Exp $$
 */
public class FundTransferRequest extends BaseTransactionRequest {

    private long destAccountNumber;
    private long refNumber;
    private long srcAccountNumber;

    public long getDestAccountNumber() {
        return destAccountNumber;
    }

    public void setDestAccountNumber(long destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    public long getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(long refNumber) {
        this.refNumber = refNumber;
    }

    public long getSrcAccountNumber() {
        return srcAccountNumber;
    }

    public void setSrcAccountNumber(long srcAccountNumber) {
        this.srcAccountNumber = srcAccountNumber;
    }
}