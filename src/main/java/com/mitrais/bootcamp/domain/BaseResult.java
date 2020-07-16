/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: BaseResult.java, v 0.1 2020‐07‐15 16:25 Aji Atin Mulyadi Exp $$
 */
public abstract class BaseResult {

    protected boolean success;
    protected ErrorContext errorContext;

    public BaseResult() {
        this.success = false;
        this.errorContext = new ErrorContext();
    }

    public BaseResult(boolean success, ErrorContext errorContext) {
        this.success = success;
        this.errorContext = errorContext;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }
}