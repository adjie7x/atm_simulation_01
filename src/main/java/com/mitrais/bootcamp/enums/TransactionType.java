package com.mitrais.bootcamp.enums;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionType.java, v 0.1 2020‐07‐29 14:03 Aji Atin Mulyadi Exp $$
 */
public enum TransactionType {

    WITHDRAWAL ("WDW", "WITHDRAWAL"),
    FUND_TRANSFER("TRF", "FUND TRANSFER")
    ;

    private String code;
    private String name;

    TransactionType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}