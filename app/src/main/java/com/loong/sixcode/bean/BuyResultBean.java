package com.loong.sixcode.bean;

import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyResultBean {
    private long time;
    private List<String> buyNum;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(List<String> buyNum) {
        this.buyNum = buyNum;
    }
}
