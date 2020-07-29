/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.integration.minibank.fundtransfer;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.base.ServiceCallback;
import com.mitrais.bootcamp.service.base.ServiceTemplate;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: FundTransferServiceImpl.java, v 0.1 2020‐07‐16 14:19 Aji Atin Mulyadi Exp $$
 */
public class FundTransferServiceImpl implements Transaction {

    ATMRepository atmRepository;

    public FundTransferServiceImpl(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public ATMSimulationResult<Transactionable> execute(BaseTransactionRequest request) {
        ATMSimulationResult<Transactionable> result = new ATMSimulationResult<>();
        FundTransferRequest fundTransferRequest = (FundTransferRequest) request;

        ServiceTemplate.execute(result, new ServiceCallback() {
            @Override
            public void checkParameter() {
                if(fundTransferRequest.getAmount() == 0){
                    throw new ATMSimulationException(new ErrorContext("invalid", "Minimum amount to transfer is $1"));
                }

                if(fundTransferRequest.getAmount() > 1000){
                    throw new ATMSimulationException(new ErrorContext("invalid", "Maximum amount to transfer is $1000"));
                }
            }

            @Override
            public void process() {

                ATMData srcAccount = atmRepository.getDataByAccountNumber(fundTransferRequest.getSrcAccountNumber());
                ATMData destAccount = atmRepository.getDataByAccountNumber(fundTransferRequest.getDestAccountNumber());

                if(srcAccount.getBalance() < fundTransferRequest.getAmount()){
                    throw new ATMSimulationException(new ErrorContext("insufficient", "Insufficient balance $"+srcAccount.getBalance()));
                }

                ATMData currentSrcData = atmRepository.deductBalance(srcAccount.getAccountNumber(), fundTransferRequest.getAmount());
                ATMData currentDestData = atmRepository.increaseBalance(destAccount.getAccountNumber(), fundTransferRequest.getAmount());

                FundTransferResponse fundTransferResponse = new FundTransferResponse();
                fundTransferResponse.setSrcAccount(currentSrcData);
                fundTransferResponse.setDestAccount(currentDestData);

                result.setSuccess(true);
                result.setObject(fundTransferResponse);

            }
        });

        return result;
    }
}