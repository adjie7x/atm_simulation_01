/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: FundTransferScreenDataResponse.java, v 0.1 2020‐07‐22 20:44 Aji Atin Mulyadi Exp $$
 */
public class FundTransferScreenDataResponse extends BaseScreenResponseData {
    private Account userDetail;

    public Account getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Account userDetail) {
        this.userDetail = userDetail;
    }
}