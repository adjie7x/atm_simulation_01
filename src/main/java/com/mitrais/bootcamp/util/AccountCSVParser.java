package com.mitrais.bootcamp.util;

import com.mitrais.bootcamp.domain.Account;
import com.mitrais.bootcamp.domain.CSVReaderUtilException;
import com.mitrais.bootcamp.domain.ErrorContext;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: AccountCSVParser.java, v 0.1 2020‐07‐29 10:37 Aji Atin Mulyadi Exp $$
 */
public class AccountCSVParser extends CSVReaderUtil<Account> {

    @Override
    protected Account parseRecord(CSVRecord record) throws CSVReaderUtilException {

        if(recordsMap.get(record.get("Account Number")) != null){
            throw new CSVReaderUtilException(new ErrorContext("duplicate", "There can't be 2 different accounts with the same Account Number."+record.get("Account Number")));
        }

        Account account = new Account();
        account.setName(record.get("Name"));
        account.setPin(record.get("PIN"));
        account.setBalance(Long.parseLong(record.get("Balance")));
        account.setAccountNumber(record.get("Account Number"));

        recordsMap.put(record.get("Account Number"), record.get("Account Number"));

        return account;
    }
}