/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.integration.user.impl;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.integration.user.UserService;

import java.util.List;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: UserServiceImpl.java, v 0.1 2020‐07‐15 11:52 Aji Atin Mulyadi Exp $$
 */
public class UserServiceImpl implements UserService {

    ATMRepository atmRepository;

    public UserServiceImpl(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public boolean validateUser(Account condition) {
        List<Account> dataList = atmRepository.getATMDataList();
        return dataList.size() > 0 ? true : false;
    }
}