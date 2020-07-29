package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: TrxHistoryScreenRequest.java, v 0.1 2020‐07‐30 0:05 Aji Atin Mulyadi Exp $$
 */
public class TrxHistoryScreenRequest implements ScreenRequestData {
    private Account userDetail;

    public Account getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Account userDetail) {
        this.userDetail = userDetail;
    }
}