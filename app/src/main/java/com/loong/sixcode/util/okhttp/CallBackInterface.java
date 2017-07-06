package com.loong.sixcode.util.okhttp;

/**
 * Created by lxl on 2017/3/4.
 * 请求数据的回调
 */

public interface CallBackInterface<V> {
    void onSuccess(V response, int id);
    void onFail(String error);
}
