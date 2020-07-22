/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ATMConstant;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.enums.WithdrawalType;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.service.integration.minibank.fundtransfer.FundTransferServiceImpl;
import com.mitrais.bootcamp.service.integration.minibank.widrawal.WithdrawalServiceImpl;
import com.mitrais.bootcamp.util.ATMUtil;
import com.mitrais.bootcamp.view.screen.Screen;
import com.mitrais.bootcamp.view.screen.impl.TransactionScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.WelcomeScreenImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Main.java, v 0.1 2020‐07‐06 10:17 Aji Atin Mulyadi Exp $$
 */
public class Main {

    private Screen welcomeScreen;
    private Screen transactionScreen;
    private Transaction withdrawalService;
    private Transaction fundTransferService;
    private ATMData userDetail;

    public Main(Transaction withdrawalService, Transaction fundTransferService, Screen welcomeScreen, Screen transactionScreen) {
        this.withdrawalService = withdrawalService;
        this.fundTransferService = fundTransferService;
        this.welcomeScreen = welcomeScreen;
        this.transactionScreen = transactionScreen;
    }

    public static void main(String[] args) {
        ATMRepository atmRepository = new ATMRepository();
        Screen welcomeScreen = new WelcomeScreenImpl(atmRepository);
        Screen transactionScreen = new TransactionScreenImpl();
        Transaction fundTransferService = new FundTransferServiceImpl(atmRepository);
        Transaction withdrawalService = new WithdrawalServiceImpl(atmRepository);
        Main main = new Main(withdrawalService, fundTransferService, welcomeScreen, transactionScreen);
        main.welcomeScreen();

    }

    public void welcomeScreen(){
        System.out.println();
        ATMSimulationResult<ScreenResponseData> welcomeScreenResponse = welcomeScreen.renderScreen();
        if(welcomeScreenResponse.isSuccess()){
            WelcomeScreenDataResponse dataResponse = (WelcomeScreenDataResponse) welcomeScreenResponse.getObject();
            userDetail = dataResponse.getUserDetail();
            if(ScreenCode.TRANSACTION_SCREEN == dataResponse.getScreenCode()){
                transactionScreen();
            }
        }else {
            System.out.println(welcomeScreenResponse.getErrorContext().getErrorMessage());
            welcomeScreen();
        }
    }

    public void transactionScreen(){
        System.out.println();
        ATMSimulationResult<ScreenResponseData> transactionScreenResponse = transactionScreen.renderScreen();
        if(transactionScreenResponse.isSuccess()){
            TransactionScreenDataResponse dataResponse = (TransactionScreenDataResponse) transactionScreenResponse.getObject();
            if(ScreenCode.TRANSACTION_SCREEN == dataResponse.getScreenCode()){
                transactionScreen();
            } else if(ScreenCode.FUNDTRANSFER_SCREEN == dataResponse.getScreenCode()){
                fundTransferScreen();
            }else if(ScreenCode.WITHDRAWAL_SCREEN == dataResponse.getScreenCode()) {
                withdrawScreen();
            }
        }else {
            System.out.println(transactionScreenResponse.getErrorContext().getErrorMessage());
            welcomeScreen();
        }
    }

    public void withdrawScreen(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("1. $10\n2. $50\n3. $100\n4. Other\n5. Back\nPlease choose option[5]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                transactionScreen();
            } else {
                int option;
                option = Integer.parseInt(opt);
                if (option < 1 || option >= 5) {
                    transactionScreen();
                } else if(option <= 3){
                    long withdrawalAmount;
                    switch (option) {
                        case 1:
                            withdrawalAmount = 10;
                            doWithdrawal(WithdrawalType.COMMON, withdrawalAmount);
                        case 2:
                            withdrawalAmount = 50;
                            doWithdrawal(WithdrawalType.COMMON, withdrawalAmount);
                        case 3:
                            withdrawalAmount = 100;
                            doWithdrawal(WithdrawalType.COMMON, withdrawalAmount);
                    }

                } else {
                    otherWithdrawScreen();
                }
            }
        } catch (Exception e) {
            transactionScreen();
        }
    }

    public void otherWithdrawScreen(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Other Withdraw");

        try {
            System.out.print("Enter amount to withdraw:");
            long withdrawalAmount = scanner.nextLong();

            doWithdrawal(WithdrawalType.OTHER, withdrawalAmount);

        } catch (Exception e) {
            System.out.println("Invalid ammount");
            otherWithdrawScreen();
        }
    }

    public void doWithdrawal(WithdrawalType withdrawalType, long withdrawalAmount){

        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAccountNumber(userDetail.getAccountNumber());
        withdrawalRequest.setAmount(withdrawalAmount);

        ATMSimulationResult<Transactionable> withdrawalResponse = withdrawalService.execute(withdrawalRequest);
        if(!withdrawalResponse.isSuccess()){
            System.out.println(withdrawalResponse.getErrorContext().getErrorMessage());
            System.out.println();
            if(WithdrawalType.COMMON == withdrawalType){
                withdrawScreen();
            }else if(WithdrawalType.OTHER == withdrawalType){
                otherWithdrawScreen();
            }
        }else {
            userDetail = (ATMData) withdrawalResponse.getObject();
            summaryScreen(withdrawalAmount);
        }
    }

    public void summaryScreen(long amount){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
        Date date = new Date();
        System.out.print("Summary\n" + formatter.format(date)
                + "\n" +
                "Withdraw : $" + amount + "\n" +
                "Balance : $" + userDetail.getBalance() + "\n" +
                "\n" +
                "1. Transaction \n" +
                "2. Exit\n" +
                "Choose option[2]:");
        try {
            int option = scanner.nextInt();
            if (option == 1) {
                transactionScreen();
            } else {
                welcomeScreen();
            }
        } catch (Exception e) {
            summaryScreen(amount);
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