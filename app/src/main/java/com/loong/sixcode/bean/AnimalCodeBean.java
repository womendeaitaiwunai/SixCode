package com.loong.sixcode.bean;

import java.util.List;

/**
 * Created by lxl on 2017/7/10.
 */

public class AnimalCodeBean {

    private List<ShengxiaoBean> shengxiao;
    private List<SeboBean> sebo;
    private List<WuhangBean> wuhang;

    public List<ShengxiaoBean> getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(List<ShengxiaoBean> shengxiao) {
        this.shengxiao = shengxiao;
    }

    public List<SeboBean> getSebo() {
        return sebo;
    }

    public void setSebo(List<SeboBean> sebo) {
        this.sebo = sebo;
    }

    public List<WuhangBean> getWuhang() {
        return wuhang;
    }

    public void setWuhang(List<WuhangBean> wuhang) {
        this.wuhang = wuhang;
    }

    public static class ShengxiaoBean {
        /**
         * sx : 鼠
         * nums : 10,22,34,46
         */

        private String sx;
        private String nums;

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }
    }

    public static class SeboBean {
        /**
         * sb : 紅
         * nums : 1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46
         */

        private String sb;
        private String nums;

        public String getSb() {
            return sb;
        }

        public void setSb(String sb) {
            this.sb = sb;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }
    }

    public static class WuhangBean {
        /**
         * wx : 金
         * nums : 3,4,17,18,25,26,33,34,47,48
         */

        private String wx;
        private String nums;

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }
    }
}
