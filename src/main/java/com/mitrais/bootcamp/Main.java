/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ATMConstant;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.service.integration.minibank.fundtransfer.FundTransferServiceImpl;
import com.mitrais.bootcamp.service.integration.minibank.widrawal.WithdrawalServiceImpl;
import com.mitrais.bootcamp.util.ATMUtil;
import com.mitrais.bootcamp.view.screen.Screen;
import com.mitrais.bootcamp.view.screen.impl.TransactionScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.WelcomeScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.WithdrawalScreenImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Main.java, v 0.1 2020‐07‐06 10:17 Aji Atin Mulyadi Exp $$
 */
public class Main {

    private Screen welcomeScreen;
    private Screen transactionScreen;
    private Screen withdrawalScreen;
    private Transaction withdrawalService;
    private Transaction fundTransferService;
    private ATMData userDetail;

    public Main(Transaction withdrawalService, Transaction fundTransferService,
                Screen welcomeScreen, Screen transactionScreen,
                Screen withdrawalScreen) {
        this.withdrawalService = withdrawalService;
        this.fundTransferService = fundTransferService;
        this.welcomeScreen = welcomeScreen;
        this.transactionScreen = transactionScreen;
        this.withdrawalScreen = withdrawalScreen;
    }

    public static void main(String[] args) {
        ATMRepository atmRepository = new ATMRepository();

        Transaction fundTransferService = new FundTransferServiceImpl(atmRepository);
        Transaction withdrawalService = new WithdrawalServiceImpl(atmRepository);

        Screen welcomeScreen = new WelcomeScreenImpl(atmRepository);
        Screen transactionScreen = new TransactionScreenImpl();
        Screen withdrawalScreen = new WithdrawalScreenImpl(withdrawalService);

        Main main = new Main(withdrawalService, fundTransferService, welcomeScreen, transactionScreen, withdrawalScreen);
        main.welcomeScreen();

    }

    public void welcomeScreen(){
        System.out.println();
        ATMSimulationResult<BaseScreenResponseData> welcomeScreenResponse = welcomeScreen.renderScreen(null);
        if(welcomeScreenResponse.isSuccess()){
            WelcomeScreenDataResponse dataResponse = (WelcomeScreenDataResponse) welcomeScreenResponse.getObject();
            userDetail = dataResponse.getUserDetail();
        }

        redirectScreen(welcomeScreenResponse);
    }

    public void transactionScreen(){
        System.out.println();
        ATMSimulationResult<BaseScreenResponseData> transactionScreenResponse = transactionScreen.renderScreen(null);

        redirectScreen(transactionScreenResponse);
    }

    public void withdrawScreen(){
        System.out.println();
        WithdrawalScreenDataRequest dataRequest = new WithdrawalScreenDataRequest();
        dataRequest.setUserDetail(userDetail);
        ATMSimulationResult<BaseScreenResponseData> withdrawalScreenResponse = withdrawalScreen.renderScreen(dataRequest);
        if(withdrawalScreenResponse.isSuccess()){
            WithdrawalScreenDataResponse dataResponse = (WithdrawalScreenDataResponse) withdrawalScreenResponse.getObject();
            userDetail = dataResponse.getUserDetail();
        }

        redirectScreen(withdrawalScreenResponse);
    }

    public void redirectScreen(ATMSimulationResult<BaseScreenResponseData> result){
        BaseScreenResponseData baseScreenResponseData = result.getObject();
        if(result.isSuccess()){
            if(ScreenCode.TRANSACTION_SCREEN == baseScreenResponseData.getScreenCode()){
                transactionScreen();
            } else if(ScreenCode.FUNDTRANSFER_SCREEN == baseScreenResponseData.getScreenCode()){
                fundTransferScreen();
            } else if(ScreenCode.WITHDRAWAL_SCREEN == baseScreenResponseData.getScreenCode()) {
                withdrawScreen();
            } else if(ScreenCode.WELCOME_SCREEN == baseScreenResponseData.getScreenCode()) {
                welcomeScreen();
            }
        }else {
            System.out.println(result.getErrorContext().getErrorMessage());
            welcomeScreen();
        }
    }

    public void fundTransferScreen(){
        System.out.println();
        String inputDestAccount = getFundTransferValue(ATMConstant.DESTINATION_ACCOUNT_FIELD_NAME.getValue());
        String inputTrfAmount = getFundTransferValue(ATMConstant.TRANSFER_AMOUNT_FIELD_NAME.getValue());
        long refNumber = refNumberScreen();

        fundTransferConfirmationScreen(inputDestAccount, inputTrfAmount, refNumber);

    }

    public String getFundTransferValue(String fieldName){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter "+fieldName+" and press enter to continue or \n" +
                "press enter to go back to Transaction : ");
        String inputUser = scanner.nextLine();
        if(inputUser.isEmpty()){
            transactionScreen();
        }

        return inputUser;
    }

    public static long refNumberScreen() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Reference Number: (This is an autogenerated random 6 digits number)\n" +
                "press enter to continue");
        String opt = scanner.nextLine();
        if (!opt.isEmpty()) {
            refNumberScreen();
        }
        return ATMUtil.getRandomIntegerBetweenRange(100000, 999999);
    }

    public void fundTransferConfirmationScreen(String destAccount, String trfAmount, long refNo){
        System.out.println();
        System.out.print("Transfer Confirmation\n" +
                "Destination Account : " + destAccount + "\n" +
                "Transfer Amount     : $" + trfAmount + "\n" +
                "Reference Number    : " + refNo + "\n" +
                "\n" +
                "1. Confirm Trx\n" +
                "2. Cancel Trx\n" +
                "Choose option[2]:");
        Scanner scanner = new Scanner(System.in);
        try {
            int opt = scanner.nextInt();
            if (opt == 1) {

                List<String> errors = validateFundTransferFields(destAccount, refNo);
                if(errors.size() > 0){
                    for (String error : errors) {
                        System.out.println(error);
                    }

                    fundTransferScreen();

                }else {
                    doFundTransfer(destAccount, trfAmount, refNo);
                }

            } else {
                welcomeScreen();
            }
        } catch (Exception e) {
            fundTransferConfirmationScreen(destAccount, trfAmount, refNo);
        }

    }

    public static List<String> validateFundTransferFields(String destAccount, long refNo){
        List<String> errors = new ArrayList<>();
        boolean isValidAccountNumberType = ATMUtil.validateFormat(destAccount, ATMConstant.ACCOUNT_NUMBER_TYPE_REGEX.getValue());
        if(!isValidAccountNumberType){
            errors.add("Account Number should only contains numbers");
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(destAccount, ATMConstant.ACCOUNT_NUMBER_LENGTH_REGEX.getValue());
        if(!isValidAccountNumberLength){
            errors.add("Account Number should have 6 digits length");
        }

        boolean isValidAmount = ATMUtil.validateFormat(destAccount, ATMConstant.AMOUNT_TYPE_REGEX.getValue());
        if(!isValidAmount){
            errors.add("Invalid amount");
        }

        if(refNo == 0){
            errors.add("Invalid  Reference Number");
        }
        return errors;
    }

    private void doFundTransfer(String destAccount, String trfAmount, long refNo){

        FundTransferRequest fundTransferRequest = new FundTransferRequest();
        fundTransferRequest.setDestAccountNumber(Long.parseLong(destAccount));
        fundTransferRequest.setSrcAccountNumber(userDetail.getAccountNumber());
        fundTransferRequest.setRefNumber(refNo);
        fundTransferRequest.setAmount(Long.parseLong(trfAmount));

        ATMSimulationResult<Transactionable> fundTransferResponse = fundTransferService.execute(fundTransferRequest);
        if(!fundTransferResponse.isSuccess()){
            System.out.println(fundTransferResponse.getErrorContext().getErrorMessage());
            System.out.println();
            fundTransferScreen();
        }else {
            FundTransferResponse fundTransferResponseObj = (FundTransferResponse) fundTransferResponse.getObject();
            userDetail = fundTransferResponseObj.getSrcAccount();
            summaryFunTransfer(destAccount, trfAmount, refNo);
        }
    }

    public void summaryFunTransfer(String acc, String amount, long ref) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Fund Transfer Summary\n" +
                "Destination Account : " + acc + "\n" +
                "Transfer Amount     : $" + amount + "\n" +
                "Reference Number    : " + ref + "\n" +
                "Balance             : $" + userDetail.getBalance() + "\n" +
                "\n" +
                "1. Transaction\n" +
                "2. Exit\n" +
                "Choose option[2]:");
        String opt = scanner.nextLine();
        if (opt.isEmpty()) {
            welcomeScreen();
        } else {
            try {
                int option = Integer.parseInt(opt);
                if (option == 1) {
                    transactionScreen();
                }
            } catch (Exception e) {
                fundTransferScreen();
            }
        }
    }

}