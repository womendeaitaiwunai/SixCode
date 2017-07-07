package com.loong.sixcode.greendao;

import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyCodeDaoDao;

import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class BuyCodeDbDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertBuyCode(BuyCodeDao shop) {
        BaseApplication.getDaoInstant().getBuyCodeDaoDao().insert(shop);
    }

    /**
     * 查询全部数据
     */
    public static List<BuyCodeDao> queryAll() {
        return BaseApplication.getDaoInstant().getBuyCodeDaoDao().queryBuilder().orderDesc(BuyCodeDaoDao.Properties.BuyTime).list();
    }

    public static void delectAll(){
        BaseApplication.getDaoInstant().getBuyCodeDaoDao().deleteAll();
    }

    public static void delectSomeCode(BuyCodeDao... buyCodeDaos){
        BaseApplication.getDaoInstant().getBuyCodeDaoDao().deleteInTx(buyCodeDaos);
    }

}
/**
 * 增加单个数据
 * getShopDao().insert(shop);
 * getShopDao().insertOrReplace(shop);
 * 增加多个数据
 * getShopDao().insertInTx(shopList);
 * getShopDao().insertOrReplaceInTx(shopList);
 * 查询全部
 * List< Shop> list = getShopDao().loadAll();
 * List< Shop> list = getShopDao().queryBuilder().list();
 * 查询附加单个条件
 * .where()
 * .whereOr()
 * 查询附加多个条件
 * .where(, , ,)
 * .whereOr(, , ,)
 * 查询附加排序
 * .orderDesc()
 * .orderAsc()
 * 查询限制当页个数
 * .limit()
 * 查询总个数
 * .count()
 * 修改单个数据
 * getShopDao().update(shop);
 * 修改多个数据
 * getShopDao().updateInTx(shopList);
 * 删除单个数据
 * getTABUserDao().delete(user);
 * 删除多个数据
 * getUserDao().deleteInTx(userList);
 * 删除数据ByKey
 * getTABUserDao().deleteByKey();
 */