/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ATMConstant;
import com.mitrais.bootcamp.enums.WithdrawalType;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.service.integration.minibank.fundtransfer.FundTransferServiceImpl;
import com.mitrais.bootcamp.service.integration.minibank.widrawal.WithdrawalServiceImpl;
import com.mitrais.bootcamp.util.ATMUtil;
import com.mitrais.bootcamp.view.screen.Screen;
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

    private static Screen welcomeScreen;
    private static ATMData userDetail;
    private static Transaction withdrawalService;
    private static Transaction fundTransferService;
    private static ATMRepository atmRepository;
    private static long withdrawalAmount;

    public static void main(String[] args) {
        atmRepository =  new ATMRepository();
        welcomeScreen();

    }

    public static void welcomeScreen(){

        System.out.println();

        welcomeScreen = new WelcomeScreenImpl(atmRepository);

        ATMSimulationResult<Object> welcomeScreenResponse = welcomeScreen.renderScreen();
        if(welcomeScreenResponse.isSuccess()){
            userDetail = (ATMData) welcomeScreenResponse.getObject();
            transactionScreen();
        }else {
            System.out.println(welcomeScreenResponse.getErrorContext().getErrorMessage());
            welcomeScreen();
        }
    }

    public static void transactionScreen(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("1. Withdraw\n2. Fund Transfer\n3. Exit\nPlease choose option[3]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                welcomeScreen();
            } else {
                option = Integer.parseInt(opt);
                if (option == 1) {
                    withdrawScreen();
                } else if (option == 2) {
                    fundTransferScreen();
                } else if (option == 3){
                    welcomeScreen();
                } else {
                    transactionScreen();
                }
            }
        } catch (Exception e) {
            transactionScreen();
        }

    }

    public static void withdrawScreen(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        try {
            System.out.print("1. $10\n2. $50\n3. $100\n4. Other\n5. Back\nPlease choose option[5]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                transactionScreen();
            } else {
                option = Integer.parseInt(opt);
                if (option < 1 || option >= 5) {
                    transactionScreen();
                } else if(option >= 1 && option <= 3){

                    switch (option) {
                        case 1:
                            withdrawalAmount = 10;
                            doWithdrawal(WithdrawalType.COMMON);
                        case 2:
                            withdrawalAmount = 50;
                            doWithdrawal(WithdrawalType.COMMON);
                        case 3:
                            withdrawalAmount = 100;
                            doWithdrawal(WithdrawalType.COMMON);
                    }

                } else if(option == 4){
                    otherWithdrawScreen();
                }
            }
        } catch (Exception e) {
        }
    }

    public static void otherWithdrawScreen(){
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Other Withdraw");

        try {
            System.out.print("Enter amount to withdraw:");
            withdrawalAmount = scanner.nextLong();

            doWithdrawal(WithdrawalType.OTHER);

        } catch (Exception e) {
            System.out.println("Invalid ammount");
            otherWithdrawScreen();
        }
    }

    public static void doWithdrawal(WithdrawalType withdrawalType){

        withdrawalService = new WithdrawalServiceImpl(atmRepository);

        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAccountNumber(userDetail.getAccountNumber());
        withdrawalRequest.setAmount(withdrawalAmount);

        ATMSimulationResult<Object> withdrawalResponse = withdrawalService.execute(withdrawalRequest);
        if(withdrawalResponse.isSuccess() != true){
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

    public static void summaryScreen(long amount){
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

    public static void fundTransferScreen(){
        System.out.println();
        String inputDestAccount = getFundTransferValue(ATMConstant.DESTINATION_ACCOUNT_FIELD_NAME.getValue());
        String inputTrfAmount = getFundTransferValue(ATMConstant.TRANSFER_AMOUNT_FIELD_NAME.getValue());
        long refNumber = refNumberScreen();

        fundTransferConfirmationScreen(inputDestAccount, inputTrfAmount, refNumber);

    }

    public static String getFundTransferValue(String fieldName){
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
        long ref = 0;
        if (!opt.isEmpty()) {
            refNumberScreen();
        }
        ref = ATMUtil.getRandomIntegerBetweenRange(100000, 999999);
        return ref;
    }

    public static void fundTransferConfirmationScreen(String destAccount, String trfAmount, long refNo){
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

                List<String> errors = validateFundTransferFields(destAccount, trfAmount, refNo);
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

    public static List<String> validateFundTransferFields(String destAccount, String trfAmount, long refNo){
        List<String> errors = new ArrayList<>();
        boolean isValidAccountNumberType = ATMUtil.validateFormat(destAccount, ATMConstant.ACCOUNT_NUMBER_TYPE_REGEX.getValue());
        if(isValidAccountNumberType != true){
            errors.add("Account Number should only contains numbers");
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(destAccount, ATMConstant.ACCOUNT_NUMBER_LENGTH_REGEX.getValue());
        if(isValidAccountNumberLength != true){
            errors.add("Account Number should have 6 digits length");
        }

        boolean isValidAmount = ATMUtil.validateFormat(destAccount, ATMConstant.AMOUNT_TYPE_REGEX.getValue());
        if(isValidAmount != true){
            errors.add("Invalid amount");
        }

        if(refNo == 0){
            errors.add("Invalid  Reference Number");
        }
        return errors;
    }

    private static void doFundTransfer(String destAccount, String trfAmount, long refNo){

        fundTransferService = new FundTransferServiceImpl(atmRepository);
        FundTransferRequest fundTransferRequest = new FundTransferRequest();
        fundTransferRequest.setDestAccountNumber(Long.valueOf(destAccount));
        fundTransferRequest.setSrcAccountNumber(userDetail.getAccountNumber());
        fundTransferRequest.setRefNumber(refNo);
        fundTransferRequest.setAmount(Long.valueOf(trfAmount));

        ATMSimulationResult<Object> fundTransferResponse = fundTransferService.execute(fundTransferRequest);
        if(fundTransferResponse.isSuccess() != true){
            System.out.println(fundTransferResponse.getErrorContext().getErrorMessage());
            System.out.println();
            fundTransferScreen();
        }else {
            FundTransferResponse fundTransferResponseObj = (FundTransferResponse) fundTransferResponse.getObject();
            userDetail = fundTransferResponseObj.getSrcAccount();
            summaryFunTransfer(destAccount, trfAmount, refNo);
        }
    }

    public static void summaryFunTransfer(String acc, String amount, long ref) {
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