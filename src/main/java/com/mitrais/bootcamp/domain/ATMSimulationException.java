/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMSimulationException.java, v 0.1 2020‐07‐16 10:32 Aji Atin Mulyadi Exp $$
 */
public class ATMSimulationException extends Exception {

    private ErrorContext errorContext;

    public ATMSimulationException(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }
}