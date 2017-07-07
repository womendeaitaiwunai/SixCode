package com.loong.sixcode.greendao;

import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.bean.AllHistoryBean;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyCodeDaoDao;

import java.util.List;

/**
 * Created by lxl on 2017/7/7.
 */

public class HistoryCodeDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param allHistoryBean
     */
    public static void insertHistoryCode(AllHistoryBean allHistoryBean) {
        BaseApplication.getDaoInstant().getAllHistoryBeanDao().insert(allHistoryBean);
    }

    /**
     * 查询全部数据
     */
    public static List<AllHistoryBean> queryAll() {
        return BaseApplication.getDaoInstant().getAllHistoryBeanDao().queryBuilder().list();
    }

    public static void delectAll(){
        BaseApplication.getDaoInstant().getAllHistoryBeanDao().deleteAll();
    }
}
