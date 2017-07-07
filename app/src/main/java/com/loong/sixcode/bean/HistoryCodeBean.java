package com.loong.sixcode.bean;

import java.util.List;

/**
 * Created by lxl on 2017/7/7.
 */

public class HistoryCodeBean {
    private List<ItemsBean> items;
    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * period : 2017年07月04日077期
         * numbers : 15,05,38,27,26,02,18
         * sx : 羊,蛇,猴,羊,猴,猴,龍
         * wx : 木,水,木,土,金,火,金
         */

        private String period;
        private String numbers;
        private String sx;
        private String wx;

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getNumbers() {
            return numbers;
        }

        public void setNumbers(String numbers) {
            this.numbers = numbers;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }
    }
}
