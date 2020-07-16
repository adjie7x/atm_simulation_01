/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMSimulationResult.java, v 0.1 2020‐07‐15 16:31 Aji Atin Mulyadi Exp $$
 */
public class ATMSimulationResult<T> extends BaseResult {

    private T object;

    /**
     * Getter method for property object.
     *
     * @return property value of object
     */
    public T getObject() {
        return object;
    }

    /**
     * Setter method for property object.
     *
     * @param object value to be assigned to property object
     */
    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}