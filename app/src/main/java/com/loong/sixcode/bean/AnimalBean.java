package com.loong.sixcode.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxl on 2017/7/12.
 */
@Entity
public class AnimalBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String code;
    private int no;
    @Generated(hash = 822044304)
    public AnimalBean(Long id, String name, String code, int no) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.no = no;
    }
    @Generated(hash = 978228517)
    public AnimalBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getNo() {
        return this.no;
    }
    public void setNo(int no) {
        this.no = no;
    }

}
