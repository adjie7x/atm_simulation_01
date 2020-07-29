/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.view.screen.impl;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.enums.WithdrawalType;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.view.screen.Screen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WithdrawalScreenImpl.java, v 0.1 2020‐07‐22 14:33 Aji Atin Mulyadi Exp $$
 */
public class WithdrawalScreenImpl implements Screen {

    private Transaction withdrawalService;
    private Account userDetail;
    private WithdrawalScreenDataResponse screenDataResponse;
    private final long TEN = 10;
    private final long FIFTY = 50;
    private final long HUNDRED = 100;

    public WithdrawalScreenImpl(Transaction withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {
        ATMSimulationResult<BaseScreenResponseData> response;
        response = new ATMSimulationResult<>();
        screenDataResponse = new WithdrawalScreenDataResponse();

        WithdrawalScreenDataRequest screenDataRequest = (WithdrawalScreenDataRequest) requestData;
        userDetail = screenDataRequest.getUserDetail();

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("1. $10\n2. $50\n3. $100\n4. Other\n5. Back\nPlease choose option[5]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
            } else {
                int option;
                option = Integer.parseInt(opt);
                if (option < 1 || option >= 5) {
                    screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
                } else if(option <= 3){
                    switch (option) {
                        case 1:
                            doWithdrawal(WithdrawalType.COMMON, TEN);
                            break;
                        case 2:
                            doWithdrawal(WithdrawalType.COMMON, FIFTY);
                            break;
                        case 3:
                            doWithdrawal(WithdrawalType.COMMON, HUNDRED);
                            break;
                    }

                } else {
                    otherWithdrawScreen();
                }
            }
        } catch (Exception e) {
            screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
        }


        screenDataResponse.setUserDetail(userDetail);
        response.setObject(screenDataResponse);
        response.setSuccess(true);

        return response;
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
                screenDataResponse.setScreenCode(ScreenCode.WITHDRAWAL_SCREEN);
            }else if(WithdrawalType.OTHER == withdrawalType){
                otherWithdrawScreen();
            }
        }else {
            WithdrawalResponse response = (WithdrawalResponse) withdrawalResponse.getObject();
            userDetail = response.getUserDetail();
            summaryScreen(withdrawalAmount);
        }
    }

    public void summaryScreen(long amount) {
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
                screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
            } else {
                screenDataResponse.setScreenCode(ScreenCode.WELCOME_SCREEN);
            }
        } catch (Exception e) {
            summaryScreen(amount);
        }
    }
}