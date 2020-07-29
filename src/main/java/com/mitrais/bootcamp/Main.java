/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.service.integration.minibank.fundtransfer.FundTransferServiceImpl;
import com.mitrais.bootcamp.service.integration.minibank.widrawal.WithdrawalServiceImpl;
import com.mitrais.bootcamp.view.screen.Screen;
import com.mitrais.bootcamp.view.screen.impl.FundTransferScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.TransactionScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.WelcomeScreenImpl;
import com.mitrais.bootcamp.view.screen.impl.WithdrawalScreenImpl;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: Main.java, v 0.1 2020‐07‐06 10:17 Aji Atin Mulyadi Exp $$
 */
public class Main {

    private Screen welcomeScreen;
    private Screen transactionScreen;
    private Screen withdrawalScreen;
    private Screen fundTransferScreen;
    private ATMData userDetail;

    public Main(Screen welcomeScreen, Screen transactionScreen,
                Screen withdrawalScreen, Screen fundTransferScreen) {
        this.welcomeScreen = welcomeScreen;
        this.transactionScreen = transactionScreen;
        this.withdrawalScreen = withdrawalScreen;
        this.fundTransferScreen = fundTransferScreen;
    }

    public static void main(String[] args) {
        ATMRepository atmRepository = new ATMRepository();


        Transaction fundTransferService = new FundTransferServiceImpl(atmRepository);
        Transaction withdrawalService = new WithdrawalServiceImpl(atmRepository);

        Screen welcomeScreen = new WelcomeScreenImpl(atmRepository);
        Screen transactionScreen = new TransactionScreenImpl();
        Screen withdrawalScreen = new WithdrawalScreenImpl(withdrawalService);
        Screen fundTransferScreen = new FundTransferScreenImpl(fundTransferService);

        Main main = new Main(welcomeScreen, transactionScreen, withdrawalScreen, fundTransferScreen);
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

    public void fundTransferScreen(){
        System.out.println();
        FundTransferScreenDataRequest dataRequest = new FundTransferScreenDataRequest();
        dataRequest.setUserDetail(userDetail);
        ATMSimulationResult<BaseScreenResponseData> fundTransferScreenResponse = fundTransferScreen.renderScreen(dataRequest);
        if(fundTransferScreenResponse.isSuccess()){
            FundTransferScreenDataResponse dataResponse = (FundTransferScreenDataResponse) fundTransferScreenResponse.getObject();
            userDetail = dataResponse.getUserDetail();
        }

        redirectScreen(fundTransferScreenResponse);

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

}