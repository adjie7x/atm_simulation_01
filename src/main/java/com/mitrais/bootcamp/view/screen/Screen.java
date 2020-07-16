/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.view.screen;

import com.mitrais.bootcamp.domain.ATMSimulationResult;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: ScreenInterface.java, v 0.1 2020‐07‐09 10:30 Aji Atin Mulyadi Exp $$
 */
public interface Screen {

    ATMSimulationResult<Object> renderScreen();
}