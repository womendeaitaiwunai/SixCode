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

    private int type;

    @Generated(hash = 161741555)
    public BuyCodeDao(Long id, String buyCode, int money, Long buyTime, int type) {
        this.id = id;
        this.buyCode = buyCode;
        this.money = money;
        this.buyTime = buyTime;
        this.type = type;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
