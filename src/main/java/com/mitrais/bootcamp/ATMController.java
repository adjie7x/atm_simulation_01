package com.mitrais.bootcamp;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.repository.TransactionRepository;
import com.mitrais.bootcamp.service.integration.minibank.Transaction;
import com.mitrais.bootcamp.service.integration.minibank.fundtransfer.FundTransferServiceImpl;
import com.mitrais.bootcamp.service.integration.minibank.history.transactions.TransactionHistoryImpl;
import com.mitrais.bootcamp.service.integration.minibank.widrawal.WithdrawalServiceImpl;
import com.mitrais.bootcamp.view.screen.Screen;
import com.mitrais.bootcamp.view.screen.impl.*;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMController.java, v 0.1 2020‐07‐29 14:52 Aji Atin Mulyadi Exp $$
 */
public class ATMController {

    private Screen welcomeScreen;
    private Screen transactionScreen;
    private Screen withdrawalScreen;
    private Screen fundTransferScreen;
    private Screen trxHistoryScreen;
    private Account userDetail;

    public ATMController(Screen welcomeScreen, Screen transactionScreen,
                Screen withdrawalScreen, Screen fundTransferScreen, Screen trxHistoryScreen) {
        this.welcomeScreen = welcomeScreen;
        this.transactionScreen = transactionScreen;
        this.withdrawalScreen = withdrawalScreen;
        this.fundTransferScreen = fundTransferScreen;
        this.trxHistoryScreen = trxHistoryScreen;
    }

    public static ATMController init(ATMRepository accountRepo){
        TransactionRepository transactionRepository = new TransactionRepository();
        Transaction transactionHistorySvc = new TransactionHistoryImpl(transactionRepository);

        Transaction fundTransferService = new FundTransferServiceImpl(accountRepo, transactionRepository);
        Transaction withdrawalService = new WithdrawalServiceImpl(accountRepo, transactionRepository);

        Screen welcomeScreen = new WelcomeScreenImpl(accountRepo);
        Screen transactionScreen = new TransactionScreenImpl();
        Screen withdrawalScreen = new WithdrawalScreenImpl(withdrawalService);
        Screen fundTransferScreen = new FundTransferScreenImpl(fundTransferService);
        Screen trxHistoryService = new TransactionHistoryScreenImpl(transactionHistorySvc);

        return new ATMController(welcomeScreen, transactionScreen, withdrawalScreen, fundTransferScreen, trxHistoryService);
    }

    public void run() throws ATMSimulationException {
        welcomeScreen();
    }

    private void welcomeScreen() throws ATMSimulationException {
        System.out.println();
        ATMSimulationResult<BaseScreenResponseData> welcomeScreenResponse = welcomeScreen.renderScreen(null);
        if(welcomeScreenResponse.isSuccess()){
            WelcomeScreenDataResponse dataResponse = (WelcomeScreenDataResponse) welcomeScreenResponse.getObject();
            userDetail = dataResponse.getUserDetail();
        }

        redirectScreen(welcomeScreenResponse);
    }

    private void transactionScreen() throws ATMSimulationException {
        System.out.println();
        ATMSimulationResult<BaseScreenResponseData> transactionScreenResponse = transactionScreen.renderScreen(null);

        redirectScreen(transactionScreenResponse);
    }

    private void withdrawScreen() throws ATMSimulationException {
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

    private void fundTransferScreen() throws ATMSimulationException {
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

    private void trxHistoryScreen() throws ATMSimulationException {
        System.out.println();
        TrxHistoryScreenRequest dataRequest = new TrxHistoryScreenRequest();
        dataRequest.setUserDetail(userDetail);
        ATMSimulationResult<BaseScreenResponseData> transactionScreenResponse = trxHistoryScreen.renderScreen(dataRequest);

        redirectScreen(transactionScreenResponse);
    }

    private void redirectScreen(ATMSimulationResult<BaseScreenResponseData> result) throws ATMSimulationException {
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
            } else if(ScreenCode.HISTORY_SCREEN == baseScreenResponseData.getScreenCode()){
                trxHistoryScreen();
            }else {
                welcomeScreen();
            }
        }else {
            System.out.println(result.getErrorContext().getErrorMessage());
            welcomeScreen();
        }
    }


}