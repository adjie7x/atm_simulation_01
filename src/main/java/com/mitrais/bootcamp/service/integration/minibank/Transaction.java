/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.integration.minibank;

import com.mitrais.bootcamp.domain.ATMSimulationResult;
import com.mitrais.bootcamp.domain.BaseTransactionRequest;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Transaction.java, v 0.1 2020‐07‐09 11:29 Aji Atin Mulyadi Exp $$
 */
public interface Transaction {

    ATMSimulationResult<Object> execute(BaseTransactionRequest request);
}