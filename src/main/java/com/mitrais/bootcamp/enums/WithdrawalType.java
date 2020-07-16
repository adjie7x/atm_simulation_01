/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.enums;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WithdrawalType.java, v 0.1 2020‐07‐16 12:12 Aji Atin Mulyadi Exp $$
 */
public enum WithdrawalType {

    /**
     * WITHDRAWAL Type
     */
    COMMON ("COMMON", "COMMON WITHDRAWAL"),
    OTHER ("OTHER", "OTHER WITHDRWAL")
    ;

    private String code;
    private String name;

    WithdrawalType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WithdrawalType getByCode(String code) {
        WithdrawalType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            WithdrawalType item = var1[var3];
            if (item.getCode().equalsIgnoreCase(code)) {
                return item;
            }
        }

        return null;
    }
}