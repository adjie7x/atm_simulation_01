/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: FundTransferScreenDataRequest.java, v 0.1 2020‐07‐22 20:40 Aji Atin Mulyadi Exp $$
 */
public class FundTransferScreenDataRequest implements ScreenRequestData {
    private Account userDetail;

    public Account getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Account userDetail) {
        this.userDetail = userDetail;
    }
}