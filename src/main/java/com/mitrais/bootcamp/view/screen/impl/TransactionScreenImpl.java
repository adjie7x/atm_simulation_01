/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.view.screen.impl;

import com.mitrais.bootcamp.domain.ATMSimulationResult;
import com.mitrais.bootcamp.domain.ScreenResponseData;
import com.mitrais.bootcamp.domain.TransactionScreenDataResponse;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.view.screen.Screen;

import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TransactionScreen.java, v 0.1 2020‐07‐22 9:47 Aji Atin Mulyadi Exp $$
 */
public class TransactionScreenImpl implements Screen {

    @Override
    public ATMSimulationResult<ScreenResponseData> renderScreen() {

        ATMSimulationResult<ScreenResponseData> response;
        response = new ATMSimulationResult<>();
        TransactionScreenDataResponse screenDataResponse = new TransactionScreenDataResponse();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("1. Withdraw\n2. Fund Transfer\n3. Exit\nPlease choose option[3]:");
            String opt = scanner.nextLine();
            if (opt.isEmpty()) {
                screenDataResponse.setScreenCode(ScreenCode.WELCOME_SCREEN);
            } else {
                int option = Integer.parseInt(opt);
                if (option == 1) {
                    screenDataResponse.setScreenCode(ScreenCode.WITHDRAWAL_SCREEN);
                } else if (option == 2) {
                    screenDataResponse.setScreenCode(ScreenCode.FUNDTRANSFER_SCREEN);
                } else if (option == 3){
                    screenDataResponse.setScreenCode(ScreenCode.WELCOME_SCREEN);
                } else {
                    screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
                }
            }
        } catch (Exception e) {
            screenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
        }

        response.setSuccess(true);
        response.setObject(screenDataResponse);

        return response;
    }
}