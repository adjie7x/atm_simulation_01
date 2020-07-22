/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

import com.mitrais.bootcamp.enums.ScreenCode;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: BaseScreenRequestData.java, v 0.1 2020‐07‐22 15:59 Aji Atin Mulyadi Exp $$
 */
public abstract class BaseScreenResponseData {

    private ScreenCode screenCode;

    public ScreenCode getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(ScreenCode screenCode) {
        this.screenCode = screenCode;
    }
}