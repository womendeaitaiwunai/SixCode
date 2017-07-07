package com.loong.sixcode.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loong.sixcode.R;

/**
 * Created by lxl on 2017/7/7.
 */

public class BaseFragment extends Fragment {
    private AlertDialog noCancelDialog;
    private ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadLayout= (LinearLayout) view.findViewById(R.id.load_layout);
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
        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据取控件
     * @param viewId 控件ID
     * @param <view> 获取的View
     * @return
     */
    public <view extends View> view getViewById(View parentView,@IdRes int  viewId){
        return (view)parentView.findViewById(viewId);
    }

    /**
     * 显示加载的View
     */
    LinearLayout loadLayout;
    public void showLoadLayout(){
        if (loadLayout==null){
            Toast.makeText(getActivity(), "The Fragment not have LoadLayout", Toast.LENGTH_SHORT).show();
        } else {
            loadLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 关闭加载VIew
     */
    public void hiddingLoadLayout(){
        if (loadLayout==null){
            Toast.makeText(getActivity(), "The Fragment not have LoadLayout", Toast.LENGTH_SHORT).show();
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
    public void startActivityByIntent(Context context, Class<?> cls, boolean isFinish){
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
            getActivity().finish();
        }
        getActivity().overridePendingTransition(startAnim, stopAnim);
    }

    /**
     *  跳转页面
     * @param intent 要跳转的意图  便于携带参数
     * @param isFinish 是否结束跳转页面
     */
    public void startActivityByIntent(Intent intent,boolean isFinish){
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public AlertDialog showNocancelDialog(String message){
        if (noCancelDialog==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
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
            progressDialog=new ProgressDialog(getActivity());
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
    public void onDestroyView() {
        super.onDestroyView();
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
}
