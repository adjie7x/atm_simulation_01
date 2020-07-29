/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.enums;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ScreenCode.java, v 0.1 2020‐07‐22 11:54 Aji Atin Mulyadi Exp $$
 */
public enum ScreenCode {

    WELCOME_SCREEN ("WCO_SCR", "Welcome Screen"),
    TRANSACTION_SCREEN ("TRX_SCR", "Transaction Screen"),
    FUNDTRANSFER_SCREEN ("FUND_SCR", "Fund Transfer Screen"),
    WITHDRAWAL_SCREEN ("WDW_SCR", "Withdrawal Screen"),
    HISTORY_SCREEN ("HST_SCR", "Transaction History Screen")
    ;

    private String screenCode;
    private String ScreenName;

    ScreenCode(String screenCode, String screenName) {
        this.screenCode = screenCode;
        ScreenName = screenName;
    }

    public String getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }
}