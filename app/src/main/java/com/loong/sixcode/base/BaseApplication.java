package com.loong.sixcode.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.loong.sixcode.bean.DaoMaster;
import com.loong.sixcode.bean.DaoSession;
import com.loong.sixcode.util.Cockroach;

/**
 * Created by lxl on 2017/3/4.
 * Application 基础类
 */

public class BaseApplication extends Application {
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        initNoCrash();
        initGreenDao();
    }

    private void initGreenDao() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "code.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    private void initNoCrash() {
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                Toast.makeText(BaseApplication.this, "数据有误 ", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
