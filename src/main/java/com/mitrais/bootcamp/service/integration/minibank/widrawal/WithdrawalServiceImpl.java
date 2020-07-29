/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.integration.minibank.widrawal;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.base.ServiceCallback;
import com.mitrais.bootcamp.service.base.ServiceTemplate;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WithdrawalServiceImpl.java, v 0.1 2020‐07‐16 9:58 Aji Atin Mulyadi Exp $$
 */
public class WithdrawalServiceImpl implements Transaction {

    ATMRepository atmRepository;

    public WithdrawalServiceImpl(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public ATMSimulationResult<Transactionable> execute(BaseTransactionRequest request) {
        ATMSimulationResult<Transactionable> result = new ATMSimulationResult<>();
        WithdrawalRequest withdrawalRequest = (WithdrawalRequest) request;

        ServiceTemplate.execute(result, new ServiceCallback() {
            @Override
            public void checkParameter() {
                if(withdrawalRequest == null){
                    throw new ATMSimulationException(new ErrorContext("null", "request can't be null"));
                }

                if(withdrawalRequest.getAccountNumber() == 0){
                    throw new ATMSimulationException(new ErrorContext("invalid", "invalid account number"));
                }

                if(withdrawalRequest.getAmount() == 0 || withdrawalRequest.getAmount() % 10 != 0){
                    throw new ATMSimulationException(new ErrorContext("invalid", "Invalid ammount"));
                }

                if(withdrawalRequest.getAmount() > 1000){
                    throw new ATMSimulationException(new ErrorContext("invalid", "Maximum amount to withdraw is $1000"));
                }

            }

            @Override
            public void process() {

                Account userDetail = atmRepository.getDataByAccountNumber(withdrawalRequest.getAccountNumber());

                long userBalance = userDetail.getBalance();
                long accountNumber = userDetail.getAccountNumber();

                if(userBalance < withdrawalRequest.getAmount()){
                    throw new ATMSimulationException(new ErrorContext("insufficient", "Insufficient balance $"+userBalance));
                }

                atmRepository.deductBalance(accountNumber, withdrawalRequest.getAmount());

                userDetail = atmRepository.getDataByAccountNumber(withdrawalRequest.getAccountNumber());

                WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
                withdrawalResponse.setUserDetail(userDetail);

                result.setSuccess(true);
                result.setObject(withdrawalResponse);

            }
        });

        return result;
    }
}