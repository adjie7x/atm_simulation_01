package com.mitrais.bootcamp.view.screen.impl;

import com.mitrais.bootcamp.domain.*;
import com.mitrais.bootcamp.enums.ATMConstant;
import com.mitrais.bootcamp.enums.ScreenCode;
import com.mitrais.bootcamp.enums.WelcomeScreenField;
import com.mitrais.bootcamp.repository.ATMRepository;
import com.mitrais.bootcamp.util.ATMUtil;
import com.mitrais.bootcamp.view.screen.Screen;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: WelcomeScreenImpl.java, v 0.1 2020‐07‐09 11:36 Aji Atin Mulyadi Exp $$
 */
public class WelcomeScreenImpl implements Screen {

    ATMRepository atmRepository;
    private static String input;

    public WelcomeScreenImpl(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) throws ATMSimulationException {
        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();

        String accountNumber;
        do accountNumber = readField(WelcomeScreenField.ACOUNT_NUMBER); while(StringUtils.isBlank(accountNumber));

        String pin;
        do pin = readField(WelcomeScreenField.PIN); while (StringUtils.isBlank(pin));

        Account condition = new Account();
        condition.setAccountNumber(accountNumber);
        condition.setPin(pin);

        Account userDetail = atmRepository.getLoginInfo(condition);

        if(userDetail != null){
            response.setSuccess(true);
            WelcomeScreenDataResponse welcomeScreenDataResponse = new WelcomeScreenDataResponse();
            welcomeScreenDataResponse.setUserDetail(userDetail);
            welcomeScreenDataResponse.setScreenCode(ScreenCode.TRANSACTION_SCREEN);
            response.setObject(welcomeScreenDataResponse);
        }else{
            response.setErrorContext(new ErrorContext("01", "Invalid Account Number/PIN"));
        }

        return response;
    }

    private String readField(WelcomeScreenField field){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter " + field.getFieldName() + ": ");
        input = scanner.nextLine();

        if(field == WelcomeScreenField.ACOUNT_NUMBER){
            validateAccountNumber();
        }else if(field == WelcomeScreenField.PIN){
            validatePIN();
        }

        return input;
    }

    private void validateAccountNumber(){
        boolean isValidAccountNumberType = ATMUtil.validateFormat(input, ATMConstant.ACCOUNT_NUMBER_TYPE_REGEX.getValue());
        if(!isValidAccountNumberType){
            System.out.println("Account Number should only contains numbers");
            System.out.println();
            readField(WelcomeScreenField.ACOUNT_NUMBER);
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(input, ATMConstant.ACCOUNT_NUMBER_LENGTH_REGEX.getValue());
        if(!isValidAccountNumberLength){
            System.out.println("Account Number should have 6 digits length");
            System.out.println();
            readField(WelcomeScreenField.ACOUNT_NUMBER);
        }

    }

    private void validatePIN(){
        boolean isValidAccountNumberType = ATMUtil.validateFormat(input, ATMConstant.PIN_TYPE_REGEX.getValue());
        if(!isValidAccountNumberType){
            System.out.println("PIN should only contains numbers");
            System.out.println();
            readField(WelcomeScreenField.PIN);
        }

        boolean isValidAccountNumberLength = ATMUtil.validateFormat(input, ATMConstant.PIN_LENGTH_REGEX.getValue());
        if(!isValidAccountNumberLength){
            System.out.println("PIN should have 6 digits length");
            System.out.println();
            readField(WelcomeScreenField.PIN);
        }
    }


}