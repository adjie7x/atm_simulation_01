package com.mitrais.bootcamp.repository;

import com.mitrais.bootcamp.domain.ATMData;

import java.util.ArrayList;
import java.util.List;

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

    public ATMData getLoginInfo(final ATMData data) {
        return dataList.stream()
                .filter(c -> c.getAccountNumber() == data.getAccountNumber() && c.getPin().equalsIgnoreCase(data.getPin()))
//                .findFirst().orElseThrow(() -> new ATMSimulationException(new ErrorContext("invalid", "data not found")));
                .findFirst().orElse(null);
    }

    public ATMData getDataByAccountNumber(final long accountNumber) {
        //                .findFirst().orElse(null);
        return dataList.stream()
                .filter(c -> c.getAccountNumber() == accountNumber)
//                .findFirst().orElseThrow(() -> new ATMSimulationException(new ErrorContext("invalid","data not found")));
                .findFirst().orElse(null);
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
            return dataList.get(index);
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
            return dataList.get(index);
        }

        return null;

    }
}