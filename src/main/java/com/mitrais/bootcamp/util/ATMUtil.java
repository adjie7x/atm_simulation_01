/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.util;

import java.util.regex.Pattern;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ATMUtil.java, v 0.1 2020‐07‐15 9:58 Aji Atin Mulyadi Exp $$
 */
public class ATMUtil {

    public static boolean validateFormat(String input, String regexPattern){
        Pattern pattern = Pattern.compile(regexPattern);
        boolean result = pattern.matcher(input).matches();

        return result;
    }

    public static int getRandomIntegerBetweenRange(int min, int max) {
        int x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }
}