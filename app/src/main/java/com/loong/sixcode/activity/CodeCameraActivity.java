package com.loong.sixcode.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loong.rsac.SixCodeUtil;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyResultBean;
import com.loong.sixcode.util.Base64Utils;
import com.loong.sixcode.util.RSAUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/7/10.
 */

public class CodeCameraActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout firstView;
    private RecyclerView recyclerView;
    private BuyResultAdapter adapter;
    private List<BuyCodeDao> buyResultBeenList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
    }

    private void initView() {
        firstView= (LinearLayout) findViewById(R.id.first_view);
        firstView.setOnClickListener(this);

        recyclerView= (RecyclerView) findViewById(R.id.camera_recycle);
        buyResultBeenList=new ArrayList<>();
        adapter=new BuyResultAdapter(buyResultBeenList,true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData(){
        ZXingLibrary.initDisplayOpinion(this);
        performCodeWithPermission("设置相应的权限", new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                    }
                    @Override
                    public void noPermission() {
                        showToast("没有相应的权限");
                        finish();
                    }
                }, android.Manifest.permission.VIBRATE,
                Manifest.permission.CAMERA);
        initCamera();
    }

    private void initCamera(){
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Toast.makeText(CodeCameraActivity.this, "结果"+result, Toast.LENGTH_SHORT).show();
            analyzeData(result);
            firstView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(CodeCameraActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    };

    private void analyzeData(String result) {
        byte[] resultCode=decrypt(result);
        Gson gson=new Gson();
        List<BuyCodeDao> buyResultBeanList=new ArrayList<>();
        Type type = new TypeToken<List<BuyResultBean>>() {}.getType();
        try {
            if (result!=null) buyResultBeanList= gson.fromJson(new String(resultCode), type);
            adapter.addSomeItemView(buyResultBeanList);
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "扫描数据有误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first_view:
                initCamera();
                firstView.setVisibility(View.GONE);
                break;
        }
    }

    private byte[] decrypt(String rsaString){
        String privateString=new SixCodeUtil().getJMS(this);
        byte[] result;
        try {
            result= RSAUtils.decryptData(Base64Utils.decode(rsaString),RSAUtils.loadPrivateKey(privateString));
            return result;
        }catch (Exception e){
            return null;
        }

    }
}
