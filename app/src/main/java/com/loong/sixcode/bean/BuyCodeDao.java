package com.loong.sixcode.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxl on 2017/7/5.
 */

@Entity
public class BuyCodeDao {
    @Id(autoincrement = true)
    private Long id;

    private String buyCode;

    private int money;

    private Long buyTime;

    @Generated(hash = 564369341)
    public BuyCodeDao(Long id, String buyCode, int money, Long buyTime) {
        this.id = id;
        this.buyCode = buyCode;
        this.money = money;
        this.buyTime = buyTime;
    }

    @Generated(hash = 1349843992)
    public BuyCodeDao() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyCode() {
        return this.buyCode;
    }

    public void setBuyCode(String buyCode) {
        this.buyCode = buyCode;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Long getBuyTime() {
        return this.buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }
}
