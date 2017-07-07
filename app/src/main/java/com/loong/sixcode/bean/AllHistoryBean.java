package com.loong.sixcode.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
    public class AllHistoryBean {
        /**
         * period : 2017年07月04日077期
         * numbers : 15,05,38,27,26,02,18
         * sx : 羊,蛇,猴,羊,猴,猴,龍
         * wx : 木,水,木,土,金,火,金
         */

        @org.greenrobot.greendao.annotation.Id(autoincrement = true)
        private Long Id;
        private String period;
        private String numbers;
        private String sx;
        private String wx;
        @Generated(hash = 1609565014)
        public AllHistoryBean(Long Id, String period, String numbers, String sx,
                String wx) {
            this.Id = Id;
            this.period = period;
            this.numbers = numbers;
            this.sx = sx;
            this.wx = wx;
        }
        @Generated(hash = 1806414600)
        public AllHistoryBean() {
        }
        public Long getId() {
            return this.Id;
        }
        public void setId(Long Id) {
            this.Id = Id;
        }
        public String getPeriod() {
            return this.period;
        }
        public void setPeriod(String period) {
            this.period = period;
        }
        public String getNumbers() {
            return this.numbers;
        }
        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }
        public String getSx() {
            return this.sx;
        }
        public void setSx(String sx) {
            this.sx = sx;
        }
        public String getWx() {
            return this.wx;
        }
        public void setWx(String wx) {
            this.wx = wx;
        }
    }