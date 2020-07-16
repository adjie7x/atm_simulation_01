/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.service.base;

import com.mitrais.bootcamp.domain.ATMSimulationException;
import com.mitrais.bootcamp.domain.ATMSimulationResult;
import com.mitrais.bootcamp.domain.ErrorContext;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ServiceTemplate.java, v 0.1 2020‐07‐16 10:25 Aji Atin Mulyadi Exp $$
 */
public class ServiceTemplate {

    /**
     * forbid constructor
     */
    private ServiceTemplate() {

    }

    public static <T> void execute(ATMSimulationResult<T> result, ServiceCallback callback) {
        try {

            callback.checkParameter();
            callback.process();

        }catch (ATMSimulationException e){
            fillFailResult(result, e);
        }catch (Exception e){
            fillFailResult(result, null);
        }
    }

    public static <T> void fillFailResult(ATMSimulationResult<T> result,
                                          ATMSimulationException e) {
        result.setSuccess(false);
        if(e != null){
            result.setErrorContext(e.getErrorContext());
        }else {
            result.setErrorContext(new ErrorContext("500", "unknown error"));
        }
    }

}