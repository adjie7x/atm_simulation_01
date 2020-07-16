/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.view.screen.impl;

import com.mitrais.bootcamp.domain.ATMData;
import com.mitrais.bootcamp.domain.ATMSimulationResult;
import com.mitrais.bootcamp.domain.ErrorContext;
import com.mitrais.bootcamp.enums.ATMConstant;
import com.mitrais.bootcamp.enums.WelcomeScreenField;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.util.ATMUtil;
import com.mitrais.bootcamp.view.screen.Screen;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WelcomeScreenImpl.java, v 0.1 2020‐07‐09 11:36 Aji Atin Mulyadi Exp $$
 */
public class WelcomeScreenImpl implements Screen {

    private static HashMap<String, String> fieldValueMap = new HashMap<>();
    ATMRepository atmRepository;
    private static String input;

    public WelcomeScreenImpl(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public ATMSimulationResult<Object> renderScreen() {
        ATMSimulationResult<Object> response = new ATMSimulationResult<>();

        renderfield(WelcomeScreenField.ACOUNT_NUMBER);
        renderfield(WelcomeScreenField.PIN);

        ATMData condition = new ATMData();
        condition.setAccountNumber(Long.valueOf(fieldValueMap.get(WelcomeScreenField.ACOUNT_NUMBER.getFieldCode())));
        condition.setPin(fieldValueMap.get(WelcomeScreenField.PIN.getFieldCode()));

        List<ATMData> dataList = atmRepository.getLoginInfo(condition);

        if(dataList.size() > 0){
            response.setSuccess(true);
            response.setObject(dataList.get(0));
        }else{
            response.setErrorContext(new ErrorContext("01", "Invalid Account Number/PIN"));
        }


        return response;
    }

    private void renderfield(WelcomeScreenField field){
        Scanner scanner = new Scanner(System.in);
        input = null;
        System.out.print("Enter " + field.getFieldName() + ": ");
        input = scanner.nextLine();

        if(field == WelcomeScreenField.ACOUNT_NUMBER){
            validateAccountNumber(field);
        }else if(field == WelcomeScreenField.PIN){
            validatePIN(field);
        }

        fieldValueMap.put(field.getFieldCode(), input);
    }

    private void validateAccountNumber(WelcomeScreenField field){
        boolean isValidAccountNumberType = ATMUtil.validateFormat(input, ATMConstant.ACCOUNT_NUMBER_TYPE_REGEX.getValue());
        if(isValidAccountNumberType != true){
            System.out.println("Account Number should only contains numbers");
            System.out.println();
            renderfield(field);
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(input, ATMConstant.ACCOUNT_NUMBER_LENGTH_REGEX.getValue());
        if(isValidAccountNumberLength != true){
            System.out.println("Account Number should have 6 digits length");
            System.out.println();
            renderfield(field);
        }

    }

    private void validatePIN(WelcomeScreenField field){
        boolean isValidAccountNumberType = ATMUtil.validateFormat(input, ATMConstant.PIN_TYPE_REGEX.getValue());
        if(!isValidAccountNumberType){
            System.out.println("PIN should only contains numbers");
            System.out.println();
            renderfield(field);
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(input, ATMConstant.PIN_LENGTH_REGEX.getValue());
        if(!isValidAccountNumberLength){
            System.out.println("PIN should have 6 digits length");
            System.out.println();
            renderfield(field);
        }
    }


}