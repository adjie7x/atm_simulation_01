/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ErrorContext.java, v 0.1 2020‐07‐15 16:26 Aji Atin Mulyadi Exp $$
 */
public class ErrorContext {

    private String errorCode;
    private String errorMessage;

    public ErrorContext(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorContext() {

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}