/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.base;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ServiceCallback.java, v 0.1 2020‐07‐16 10:26 Aji Atin Mulyadi Exp $$
 */
public interface ServiceCallback {

    /**
     * check param
     */
    void checkParameter();

    /**
     * process business logic
     */
    void process();
}