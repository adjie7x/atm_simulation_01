/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

import com.mitrais.bootcamp.enums.ScreenCode;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionScreenDataResponse.java, v 0.1 2020‐07‐22 12:24 Aji Atin Mulyadi Exp $$
 */
public class TransactionScreenDataResponse implements ScreenResponseData {

    private ScreenCode screenCode;

    public TransactionScreenDataResponse() {
    }

    public TransactionScreenDataResponse(ScreenCode screenCode) {
        this.screenCode = screenCode;
    }

    public ScreenCode getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(ScreenCode screenCode) {
        this.screenCode = screenCode;
    }
}