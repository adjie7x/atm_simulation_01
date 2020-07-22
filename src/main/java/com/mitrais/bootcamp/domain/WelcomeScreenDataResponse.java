/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

import com.mitrais.bootcamp.enums.ScreenCode;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WelcomeScreenDataResponse.java, v 0.1 2020‐07‐22 11:17 Aji Atin Mulyadi Exp $$
 */
public class WelcomeScreenDataResponse implements ScreenResponseData {

    private ATMData userDetail;

    private ScreenCode screenCode;

    public WelcomeScreenDataResponse(ATMData userDetail) {
        this.userDetail = userDetail;
    }

    public ATMData getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(ATMData userDetail) {
        this.userDetail = userDetail;
    }

    public ScreenCode getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(ScreenCode screenCode) {
        this.screenCode = screenCode;
    }
}