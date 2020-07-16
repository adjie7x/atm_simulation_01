/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.repository;

import com.mitrais.bootcamp.domain.ATMData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMRepository.java, v 0.1 2020‐07‐06 15:17 Aji Atin Mulyadi Exp $$
 */
public class ATMRepository {

    private static List<ATMData> dataList = new ArrayList<ATMData>();
    static {
        ATMData atmData1 = new ATMData();
        atmData1.setName("John Doe");
        atmData1.setAccountNumber(112233);
        atmData1.setPin("012108");
        atmData1.setBalance(100);

        ATMData atmData2 = new ATMData();
        atmData2.setName("Jane Doe");
        atmData2.setAccountNumber(112244);
        atmData2.setPin("932012");
        atmData2.setBalance(30);

        dataList.add(atmData1);
        dataList.add(atmData2);
    }

    public List<ATMData> getATMDataList(){
        return dataList;
    }

    public List<ATMData> getLoginInfo(final ATMData data){
        List<ATMData> result = dataList.stream()
                .filter(c -> c.getAccountNumber() == data.getAccountNumber() && c.getPin().equalsIgnoreCase(data.getPin()))
                .collect(Collectors.toList());
        return result;
    }

    public List<ATMData> getDataByAccountNumber(final ATMData data){
        List<ATMData> result = dataList.stream()
                .filter(c -> c.getAccountNumber() == data.getAccountNumber())
                .collect(Collectors.toList());
        return result;
    }

    public ATMData deductBalance(long accountNumber, long amount){

        int index = -1;

        for (ATMData data : dataList) {
            if (data.getAccountNumber() == accountNumber) {
                index = dataList.indexOf(data);
                break;
            }
        }

        if(index > -1){
            dataList.get(index).setBalance(dataList.get(index).getBalance() - amount);
            ATMData data = dataList.get(index);
            return data;
        }

        return null;
    }

    public ATMData increaseBalance(long accountNumber, long amount){

        int index = -1;

        for (ATMData data : dataList) {
            if (data.getAccountNumber() == accountNumber) {
                index = dataList.indexOf(data);
                break;
            }
        }

        if(index > -1){
            dataList.get(index).setBalance(dataList.get(index).getBalance() + amount);
            ATMData data = dataList.get(index);
            return data;
        }

        return null;

    }

    public void addData(ATMData atmData){
        dataList.add(atmData);
    }
}