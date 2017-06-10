package com.loong.sixcode.base;

import android.app.Application;
import android.widget.Toast;
import com.loong.sixcode.util.Cockroach;

/**
 * Created by lxl on 2017/3/4.
 * Application 基础类
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNoCrash();
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
}
