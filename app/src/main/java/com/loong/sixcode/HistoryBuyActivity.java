package com.loong.sixcode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyResultBean;
import com.loong.sixcode.greendao.BuyCodeDbDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class HistoryBuyActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private BuyResultAdapter adapter;
    private List<BuyResultBean> buyResultBeenList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_buy);
        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.history_buy_recycle);
        buyResultBeenList=new ArrayList<>();
        adapter=new BuyResultAdapter(buyResultBeenList);
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
            for (BuyCodeDao buyCodeDao:buyCodeDaos){
                BuyResultBean buyResultBean=new BuyResultBean();
                List<String> list=new ArrayList<>();
                list.addAll(Arrays.asList(buyCodeDao.getBuyCode().split("、")));
                buyResultBean.setBuyNum(list);
                buyResultBean.setTime(buyCodeDao.getBuyTime());
                buyResultBean.setMoney(buyCodeDao.getMoney());
                buyResultBeenList.add(buyResultBean);
            }
            adapter.cleanView();
            adapter.addSomeItemView(buyResultBeenList);
            hideProgressDialog();
        }

    }
}
