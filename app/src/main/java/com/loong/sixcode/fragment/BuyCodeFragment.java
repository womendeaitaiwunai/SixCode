package com.loong.sixcode.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loong.rsac.SixCodeUtil;
import com.loong.sixcode.activity.HistoryBuyActivity;
import com.loong.sixcode.MainActivity;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.adapter.BuySomeAdapter;
import com.loong.sixcode.adapter.CodeAdapter;
import com.loong.sixcode.base.BaseFragment;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.greendao.BuyCodeDbDao;
import com.loong.sixcode.util.Base64Utils;
import com.loong.sixcode.util.QRCodeUtil;
import com.loong.sixcode.util.RSAUtils;
import com.loong.sixcode.view.Sneaker;
import com.loong.sixcode.view.SureBuyDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 * 购买码页面
 */

public class BuyCodeFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView codeRecycle,resultRecycle,someRecycle;
    private BuyResultAdapter buyResultAdapter;
    private BuySomeAdapter buySomeAdapter;
    private LinearLayout nextView;
    private boolean isSingle=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        codeRecycle= (RecyclerView) view.findViewById(R.id.code_recycle);
        resultRecycle= (RecyclerView) view.findViewById(R.id.buy_recycle);
        RadioGroup radioGroup= (RadioGroup) view.findViewById(R.id.select_group);
        nextView= (LinearLayout) view.findViewById(R.id.next_view);
        someRecycle= (RecyclerView) nextView.findViewById(R.id.some_select_recycle);
        nextView.findViewById(R.id.some_sure).setOnClickListener(this);
        nextView.findViewById(R.id.some_canal).setOnClickListener(this);
        view.findViewById(R.id.first_show).setOnClickListener(this);
        view.findViewById(R.id.first_op).setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isSingle=(checkedId==R.id.single);
                if (isSingle)  nextView.setVisibility(View.GONE);
                else nextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initData() {
        List<Integer> codeList=new ArrayList<>();
        for (int i = 1; i < 50; i++) codeList.add(i);
        CodeAdapter codeAdapter=new CodeAdapter(codeList);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),7);
        codeRecycle.setLayoutManager(manager);
        codeRecycle.setAdapter(codeAdapter);


        List<String> someBuy=new ArrayList<>();
        buySomeAdapter=new BuySomeAdapter(someBuy);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        someRecycle.setLayoutManager(layoutManager);
        someRecycle.setAdapter(buySomeAdapter);

        List<BuyCodeDao> buyResultBeenList=new ArrayList<>();
        buyResultAdapter=new BuyResultAdapter(buyResultBeenList,false);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        resultRecycle.setLayoutManager(manager1);
        resultRecycle.setAdapter(buyResultAdapter);

        codeAdapter.setOnItemViewClickListener(new BaseRecycleAdapter.OnItemViewClickListener<Integer>() {
            @Override
            public void itemViewClick(Integer integer,int position) {
                if (isSingle) showSingeBuyDialog(integer+"","",-1);
                else {
                    buySomeAdapter.addItemView(integer+"",false);
                    someRecycle.smoothScrollToPosition(buySomeAdapter.getItemCount());
                }
            }
        });

        buySomeAdapter.setOnItemViewClickListener(new BaseRecycleAdapter.OnItemViewClickListener<String>() {
            @Override
            public void itemViewClick(String s,int position) {
                buySomeAdapter.removeItemView(position);
            }
        });


        buyResultAdapter.setOnViewClickListener(new BaseRecycleAdapter.OnViewClickListener<BuyCodeDao>() {
            @Override
            public void viewClick(BuyCodeDao buyResultBean, int clickId, int position) {
                if (clickId==R.id.change_result){
                    showSingeBuyDialog(buyResultBean.getBuyCode(),(buyResultBean.getMoney()/buyResultBean.getBuyCode().split("、").length)+"",position);
                }
            }
        });

        buyResultAdapter.setOnItemViewLongClickListener(new BaseRecycleAdapter.OnItemViewLongClickListener<BuyCodeDao>() {
            @Override
            public void itemLongViewClick(BuyCodeDao buyResultBean, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("是否删除这条记录");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyResultAdapter.removeItemView(position);
                    }
                });
                builder.show();
            }
        });


    }

    /**
     * 选择购买的弹框
     */
    private void showSingeBuyDialog(String sureResult,String money,int changePosition){
        SureBuyDialog sureBuyDialog=new SureBuyDialog(getActivity(),sureResult,money,changePosition);
        sureBuyDialog.setBuyResultCallBack(new SureBuyDialog.BuyResultCallBack() {
            @Override
            public void resultBack(BuyCodeDao buyResultBean,int changePosition) {
                if (changePosition==-1) buyResultAdapter.addItemView(buyResultBean,true);
                else buyResultAdapter.changeDataByPosition(changePosition,buyResultBean);
                resultRecycle.smoothScrollToPosition(buyResultAdapter.getItemCount());
                showBuySneaker(buyResultBean);
            }
        });
        sureBuyDialog.show();
    }

    private void showBuySneaker(BuyCodeDao buyResultBean){
        int resultBuyNum=buyResultBean.getBuyCode().split("、").length;
        String result=buyResultBean.getBuyCode();
        if (result.endsWith("、")) result=result.substring(0,result.length()-1);
        if (resultBuyNum>1) result=result+"/各"+(buyResultBean.getMoney()/resultBuyNum)+"块";
        else result=result+"/"+buyResultBean.getMoney()+"块";
        Sneaker.with(getActivity())
                .setTitle("单据加入成功")
                .setDuration(3000)
                .setMessage(result)
                .sneakSuccess();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.some_sure:
                String buyNum="";
                List<String> buyList=buySomeAdapter.getAllData();
                for (String buyData:buyList){
                    buyNum=buyNum+buyData+"、";
                }
                if (buyNum.endsWith("、")) buyNum=buyNum.substring(0,buyNum.length());
                if (buySomeAdapter.getItemCount()!=0) showSingeBuyDialog(buyNum,"",-1);
                buySomeAdapter.cleanView();
                break;
            case R.id.some_canal:
                buySomeAdapter.cleanView();
                break;
            case R.id.first_op:
                startActivity(new Intent(getActivity(), HistoryBuyActivity.class));
                break;
            case R.id.first_show:
                ((MainActivity)getActivity()).showProgressDialog("正在提交数据...");
                List<BuyCodeDao> buyResultBeanList = buyResultAdapter.getAllData();
                for (BuyCodeDao buyResultBean:buyResultBeanList){
                    BuyCodeDbDao.insertBuyCode(buyResultBean);
                }
                ((MainActivity)getActivity()).addCodeData();
                Toast.makeText(getActivity(), "数据增加成功", Toast.LENGTH_SHORT).show();
                buyResultAdapter.cleanView();
                ((MainActivity)getActivity()).hideProgressDialog();

                Gson gson=new Gson();
                String jsonString=gson.toJson(buyResultBeanList);
                Log.i("要加密字段",jsonString);
                String jiamiString=jiaMi(jsonString);
                Log.i("加密",jiamiString);
                copy(jiamiString,getActivity());

                new CreateQRCode().execute(jiamiString);
                break;
        }
    }

    public class CreateQRCode extends AsyncTask<String,Void,Boolean> {
        @Override
        protected void onPreExecute() {
            showProgressDialog("正在准备生成订单图片");
        }
        @Override
        protected Boolean doInBackground(String... params) {
            boolean result= QRCodeUtil.createQRImage(params[0],500,500,null,
                    Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"sixCode.jpg");
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            hideProgressDialog();
            if (aBoolean) Toast.makeText(getActivity(), "生成订单图片成功", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getActivity(), "生成订单图片失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 实现文本复制功能
     * @param content 主页
     */
    public static void copy(String content, Context context) {
        Toast.makeText(context, "已经复制请选择地方粘贴", Toast.LENGTH_SHORT).show();
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    private String jiaMi(String jiami){
        String publicString=new SixCodeUtil().getJS(getActivity());
        String result="";
        try {
            result= Base64Utils.encode(RSAUtils.encryptData(jiami.getBytes(),RSAUtils.loadPublicKey(publicString)));
        }catch (Exception e){
            Toast.makeText(getActivity(), "加密出错", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
