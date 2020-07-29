package com.mitrais.bootcamp.view.screen.impl;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.enums.TransactionType;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.view.screen.Screen;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionHistoryScreenImpl.java, v 0.1 2020‐07‐29 23:55 Aji Atin Mulyadi Exp $$
 */
public class TransactionHistoryScreenImpl implements Screen {

    private Transaction transactionHistoryService;
    private TransactionScreenDataResponse screenDataResponse;

    public TransactionHistoryScreenImpl(Transaction transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {

        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();
        screenDataResponse = new TransactionScreenDataResponse();

        TrxHistoryScreenRequest historyRequest = (TrxHistoryScreenRequest) requestData;

        System.out.println("=============================");
        System.out.println("|    Transaction History    |");
        System.out.println("=============================");

        printTransactionHistories(historyRequest);

        response.setSuccess(true);
        response.setObject(screenDataResponse);

        return response;
    }

    private void printTransactionHistories(TrxHistoryScreenRequest historyRequest) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Please input limit of transaction :");
            int limit = scanner.nextInt();

            TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
            transactionHistoryRequest.setAccountNumber(historyRequest.getUserDetail().getAccountNumber());
            transactionHistoryRequest.setLimit(limit);

            ATMSimulationResult<Transactionable> historyResponse = transactionHistoryService.execute(transactionHistoryRequest);
            if(!historyResponse.isSuccess()){
                System.out.println("System error ... ");
                printTransactionHistories(historyRequest);
            }

            TransactionHistoryResponse transactionHistoryResponse = (TransactionHistoryResponse) historyResponse.getObject();
            List<TransactionDTO> transactions = transactionHistoryResponse.getTransactions();

            if(!transactions.isEmpty()){
                transactions.forEach(this::printTransactionRow);
                backToTransactionScreen();
            } else {
                System.out.println("You have not made any transactions");
                screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
            }


        } catch (Exception e){
            System.out.println("Please input correct limit amount ... ");
            printTransactionHistories(historyRequest);
        }

    }

    private void printTransactionRow(TransactionDTO transaction) {
        System.out.println();
        System.out.println("Date: " + transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        System.out.println("Transaction type: " + transaction.getTransactionType().toString());
        if (transaction.getTransactionType().equals(TransactionType.FUND_TRANSFER)) {
            System.out.println("Destination Account: " + transaction.getDestAccount().getAccountNumber());
        }
        System.out.println("Amount: $" + transaction.getAmount());
        System.out.println("Mutation Type: " + transaction.getMutationType().getName());
        System.out.println("--------------------------------------");
    }

    private void backToTransactionScreen() {
        System.out.println("Press any key to go back to the Transaction Screen");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
    }
}