package com.mitrais.bootcamp.service.integration.minibank.history.transactions;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.repository.TransactionRepository;
import com.mitrais.bootcamp.service.base.ServiceCallback;
import com.mitrais.bootcamp.service.base.ServiceTemplate;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionHistoryImpl.java, v 0.1 2020‐07‐29 23:21 Aji Atin Mulyadi Exp $$
 */
public class TransactionHistoryImpl implements Transaction {

    private TransactionRepository transactionRepository;

    public TransactionHistoryImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ATMSimulationResult<Transactionable> execute(BaseTransactionRequest request) {
        ATMSimulationResult<Transactionable> result = new ATMSimulationResult<>();
        TransactionHistoryRequest transactionHistoryRequest = (TransactionHistoryRequest) request;

        ServiceTemplate.execute(result, new ServiceCallback() {
            @Override
            public void checkParameter() {
            }

            @Override
            public void process() {

                TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();

                List<TransactionDTO> transactions = getTransactionDTOS();

                transactionHistoryResponse.addTransactions(transactions);

                result.setSuccess(true);
                result.setObject(transactionHistoryResponse);

            }

            private List<TransactionDTO> getTransactionDTOS() {
                return transactionRepository.getAllTransaction().stream()
                                    .filter(trxDto -> transactionHistoryRequest.getAccountNumber().equals(trxDto.getSourceAccountNumber()))
                                    .sorted(Comparator.comparing(TransactionDTO::getTransactionDate))
                                    .sorted((o1, o2) -> o2.getTransactionDate().compareTo(o1.getTransactionDate()))
                                    .limit(transactionHistoryRequest.getLimit() == 0 ?10:transactionHistoryRequest.getLimit())
                                    .collect(Collectors.toList());
            }
        });


        return result;
    }
}