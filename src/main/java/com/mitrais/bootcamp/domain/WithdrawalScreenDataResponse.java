/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WithdrawalScreenDataResponse.java, v 0.1 2020‐07‐22 14:48 Aji Atin Mulyadi Exp $$
 */
public class WithdrawalScreenDataResponse extends BaseScreenResponseData {
    private ATMData userDetail;

    public ATMData getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(ATMData userDetail) {
        this.userDetail = userDetail;
    }
}