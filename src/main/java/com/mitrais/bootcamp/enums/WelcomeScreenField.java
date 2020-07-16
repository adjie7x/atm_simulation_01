/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.enums;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WelcomeScreen.java, v 0.1 2020‐07‐15 12:57 Aji Atin Mulyadi Exp $$
 */
public enum WelcomeScreenField {
    /**
     * ACOUNT_NUMBER field
     */
    ACOUNT_NUMBER ("ACN", "Account Number"),
    PIN ("PIN", "PIN")
    ;

    private String fieldCode;
    private String fieldName;

    WelcomeScreenField(String fieldCode, String fieldName) {
        this.fieldCode = fieldCode;
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static WelcomeScreenField getByCode(String fieldCode) {
        WelcomeScreenField[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            WelcomeScreenField item = var1[var3];
            if (item.getFieldCode().equalsIgnoreCase(fieldCode)) {
                return item;
            }
        }

        return null;
    }
}