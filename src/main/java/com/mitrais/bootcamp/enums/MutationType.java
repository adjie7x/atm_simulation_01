package com.mitrais.bootcamp.enums;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: MutationType.java, v 0.1 2020‐07‐29 14:03 Aji Atin Mulyadi Exp $$
 */
public enum MutationType {

    DEBIT ("DB", "DEBIT"),
    KREDIT ("KR", "KREDIT")
    ;

    private String code;
    private String name;

    MutationType(String code, String name) {
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