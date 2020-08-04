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

    private String destAccountNumber;
    private long refNumber;
    private String srcAccountNumber;

    public String getDestAccountNumber() {
        return destAccountNumber;
    }

    public void setDestAccountNumber(String destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    public long getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(long refNumber) {
        this.refNumber = refNumber;
    }

    public String getSrcAccountNumber() {
        return srcAccountNumber;
    }

    public void setSrcAccountNumber(String srcAccountNumber) {
        this.srcAccountNumber = srcAccountNumber;
    }
}