package com.mitrais.bootcamp.repository;

import com.mitrais.bootcamp.domain.ATMSimulationException;
import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.util.AccountCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMRepository.java, v 0.1 2020‐07‐06 15:17 Aji Atin Mulyadi Exp $$
 */
public class ATMRepository {

    private static List<Account> dataList = new ArrayList<Account>();
    static {
        Account account1 = new Account();
        account1.setName("John Doe");
        account1.setAccountNumber(112233);
        account1.setPin("012108");
        account1.setBalance(100);

        Account account2 = new Account();
        account2.setName("Jane Doe");
        account2.setAccountNumber(112244);
        account2.setPin("932012");
        account2.setBalance(30);

        dataList.add(account1);
        dataList.add(account2);
    }

    public List<Account> getATMDataList(){
        return dataList;
    }

    public Account getLoginInfo(final Account data) {
        return dataList.stream()
                .filter(c -> c.getAccountNumber() == data.getAccountNumber() && c.getPin().equalsIgnoreCase(data.getPin()))
//                .findFirst().orElseThrow(() -> new ATMSimulationException(new ErrorContext("invalid", "data not found")));
                .findFirst().orElse(null);
    }

    public Account getDataByAccountNumber(final long accountNumber) {
        //                .findFirst().orElse(null);
        return dataList.stream()
                .filter(c -> c.getAccountNumber() == accountNumber)
//                .findFirst().orElseThrow(() -> new ATMSimulationException(new ErrorContext("invalid","data not found")));
                .findFirst().orElse(null);
    }

    public Account deductBalance(long accountNumber, long amount){

        int index = -1;

        for (Account data : dataList) {
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

    public Account increaseBalance(long accountNumber, long amount){

        int index = -1;

        for (Account data : dataList) {
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

    public void loadAccountDataFromCSV(String path) throws ATMSimulationException, IOException {
        AccountCSVParser accountCSVParser = new AccountCSVParser();
        dataList.addAll(accountCSVParser.parseFromCSV(path).stream().distinct().collect(Collectors.toList()));
        System.out.println("total account : "+dataList.size());
    }
}