/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.integration.user;

import com.mitrais.bootcamp.domain.ATMData;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: UserService.java, v 0.1 2020‐07‐15 11:49 Aji Atin Mulyadi Exp $$
 */
public interface UserService {

    boolean validateUser(ATMData condition);
}