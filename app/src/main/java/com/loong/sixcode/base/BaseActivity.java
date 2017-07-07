package com.loong.sixcode.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.loong.sixcode.R;

import java.io.File;

import lecho.lib.hellocharts.model.Line;

/**
 * Created by lxl on 2017/3/4.
 * Activity基础类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private AlertDialog noCancelDialog;
    private ProgressDialog progressDialog;
    protected static SharedPreferences spUser;
    protected static SharedPreferences.Editor editorUser;
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;
    public interface PermissionCallback{
        void hasPermission();
        void noPermission();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUser = getSharedPreferences("BitKuCheck", MODE_PRIVATE);
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
     * 显示加载的View
     */
    LinearLayout loadLayout;
    public void showLoadLayout(){
        loadLayout= (LinearLayout) findViewById(R.id.load_layout);
        if (loadLayout==null){
            Toast.makeText(this, "The Activity not have LoadLayout", Toast.LENGTH_SHORT).show();
        } else {
            loadLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 关闭加载VIew
     */
    public void hiddingLoadLayout(){
        if (loadLayout==null){
            Toast.makeText(this, "The Activity not have LoadLayout", Toast.LENGTH_SHORT).show();
        } else {
            loadLayout.setVisibility(View.GONE);
        }
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

    /**
     *  跳转页面
     * @param intent 要跳转的意图  便于携带参数
     * @param isFinish 是否结束跳转页面
     */
    public void startActivityByIntent(Intent intent,boolean isFinish){
        startActivity(intent);
        if (isFinish) {
            finish();
        }
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog!=null&&progressDialog.isShowing())
            progressDialog.dismiss();
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

    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），
     *  直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions){
        if(permissions == null || permissions.length == 0)return;
        this.permissionRunnable = runnable;
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            requestPermission(permissionDes,permissionRequestCode,permissions);
        }

    }
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }
    private void requestPermission(String permissionDes,final int requestCode,final String[] permissions){
        if(shouldShowRequestPermissionRationale(permissions)){
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    }).show();

        }else{
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            }else{
                showToast("暂无权限执行相关操作！");
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public boolean verifyPermissions(int[] grantResults) {
        if(grantResults.length < 1){
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * checkSelfPermission检测权限失效问题
     * @param permission
     * @return
     */
    int targetSdkVersion;
    public boolean selfPermissionGranted(String permission) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                result = this.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                result = PermissionChecker.checkSelfPermission(this, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    private void initTagVersion(){
        try {
            final PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
