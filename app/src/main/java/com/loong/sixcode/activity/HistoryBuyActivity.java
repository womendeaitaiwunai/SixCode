package com.loong.sixcode.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.greendao.BuyCodeDbDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class HistoryBuyActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private BuyResultAdapter adapter;
    private List<BuyCodeDao> buyResultBeenList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_buy);
        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.history_buy_recycle);
        buyResultBeenList=new ArrayList<>();
        adapter=new BuyResultAdapter(buyResultBeenList,true);
        adapter.setOnItemViewLongClickListener(new BaseRecycleAdapter.OnItemViewLongClickListener<BuyCodeDao>() {
            @Override
            public void itemLongViewClick(BuyCodeDao buyResultBean, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(HistoryBuyActivity.this);
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
                        adapter.removeItemView(position);

                    }
                });
                builder.show();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        new GetHistory().execute();
    }


    public class GetHistory extends AsyncTask<Void, Void, List<BuyCodeDao>> {
        @Override
        protected void onPreExecute() {
            showProgressDialog("正在加载历史数据...");
        }

        @Override
        protected List<BuyCodeDao> doInBackground(Void... params) {
            return BuyCodeDbDao.queryAll();
        }

        @Override
        protected void onPostExecute(List<BuyCodeDao> buyCodeDaos) {
            buyResultBeenList.addAll(buyCodeDaos);
            adapter.cleanView();
            adapter.addSomeItemView(buyResultBeenList);
            hideProgressDialog();
        }
    }
}
