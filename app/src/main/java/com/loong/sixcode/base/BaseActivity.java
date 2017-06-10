package com.loong.sixcode.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loong.sixcode.common.CommonValue;

import java.io.File;

/**
 * Created by lxl on 2017/3/4.
 * Activity基础类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private AlertDialog noCancelDialog;
    private ProgressDialog progressDialog;
    protected static SharedPreferences spUser;
    protected static SharedPreferences.Editor editorUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUser = getSharedPreferences(CommonValue.SP_NAME, MODE_PRIVATE);
        editorUser = spUser.edit();
    }


    public void deleteCrach(){
        File file=BaseActivity.this.getCacheDir();
        if (file.exists()){
            System.gc();
            boolean isDel=file.delete();
            if (isDel) Toast.makeText(BaseActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     *  吐司的方法
     * @param toastMsg StringId
     */
    public void showToast(@StringRes int toastMsg){
        showToast(getString(toastMsg));
    }

    /**
     *  吐司的方法
     * @param toastMsg ""
     */
    public void showToast(String toastMsg){
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据取控件
     * @param viewId 控件ID
     * @param <view> 获取的View
     * @return
     */
    public <view extends View> view getViewById(@IdRes int  viewId){
        return (view)findViewById(viewId);
    }

    /**
     *  跳转页面
     * @param context 要跳转的页面的上下文
     * @param cls 跳转的结果页面
     * @param isFinish 是否结束跳转页面
     */
    public void startActivityByIntent(Context context,Class<?> cls,boolean isFinish){
        startActivityByIntent(context,cls,isFinish,android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     *  跳转页面
     * @param context 要跳转的页面的上下文
     * @param cls 跳转的结果页面
     * @param isFinish 是否结束跳转页面
     * @param startAnim 开始动画
     * @param stopAnim 结束动画
     */
    public void startActivityByIntent(Context context, Class<?> cls, boolean isFinish, @AnimRes int startAnim, @AnimRes int stopAnim){
        startActivity(new Intent(context, cls));
        if (isFinish) {
            finish();
        }
        overridePendingTransition(startAnim, stopAnim);
    }

    public AlertDialog showNocancelDialog(String message){
        if (noCancelDialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            noCancelDialog=builder.show();
        }
        noCancelDialog.setMessage(message);
        noCancelDialog.setCanceledOnTouchOutside(false);
        noCancelDialog.setCancelable(false);
        noCancelDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return noCancelDialog;
    }

    public void showProgressDialog(String message){
        showProgressDialog("",message);
    }

    public void showProgressDialog(String title,String message){
        showProgressDialog(title,message,true);
    }

    public void showProgressDialog(String title,String message,boolean isFinish){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(message);
            progressDialog.setTitle(title);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(isFinish);
            progressDialog.show();
        }else if (!progressDialog.isShowing()){
            progressDialog.setMessage(message);
            progressDialog.show();
        }else {
            progressDialog.setMessage(message);
        }
    }

    public void hideNoCancelDialog(){
        if (noCancelDialog!=null&&noCancelDialog.isShowing())
            noCancelDialog.dismiss();
    }

    public void hideProgressDialog(){
        if (progressDialog!=null&&progressDialog.isShowing())
        progressDialog.dismiss();
    }
    /**
     * 6.0申请权限接口，
     * 调用的activity记得自己重写onRequestPermissionsResult
     * @param PERMISSIONS_STORAGE
     */
    public void verifyPermissions(String[] PERMISSIONS_STORAGE,int PERMISSIONS_REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(this,
                String.valueOf(PERMISSIONS_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, PERMISSIONS_REQUEST_CODE);
        }
    }

}
