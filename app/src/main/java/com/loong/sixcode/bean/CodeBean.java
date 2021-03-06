package com.loong.sixcode.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxl on 2017/7/6.
 */

@Entity
public class CodeBean {
    @Id(autoincrement = true)
    private Long id;
    private String code;
    private int money;
    @Generated(hash = 1875354834)
    public CodeBean(Long id, String code, int money) {
        this.id = id;
        this.code = code;
        this.money = money;
    }
    @Generated(hash = 544591002)
    public CodeBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    
}
