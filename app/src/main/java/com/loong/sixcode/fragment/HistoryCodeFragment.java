package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.HistoryCodeAdapter;
import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.base.BaseFragment;
import com.loong.sixcode.bean.AllHistoryBean;
import com.loong.sixcode.bean.HistoryCodeBean;
import com.loong.sixcode.common.WebUrl;
import com.loong.sixcode.greendao.HistoryCodeDao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by lxl on 2017/7/7.
 */

public class HistoryCodeFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private HistoryCodeAdapter adapter;
    private List<AllHistoryBean> historyList;
    private String year="2017";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_history_buy,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        TextView title= (TextView) view.findViewById(R.id.title);
        title.setText(year+"年开奖记录");
        recyclerView= (RecyclerView) view.findViewById(R.id.history_buy_recycle);
        if (BaseApplication.getDaoInstant().getAllHistoryBeanDao().count()>0) historyList=HistoryCodeDao.queryAll();
        else historyList=new ArrayList<>();
        adapter=new HistoryCodeAdapter(historyList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        showLoadLayout();
        initData();
    }

    private void initData() {
        OkHttpUtils.get().url(WebUrl.HistoryCodeUrl+year).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hiddingLoadLayout();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("接收到的数据",response);
                Gson gson=new Gson();
                HistoryCodeBean historyCodeBean=gson.fromJson(response,HistoryCodeBean.class);
                List<AllHistoryBean> allHistoryBeenList=historyCodeBean.getItems();
                adapter.cleanView();
                adapter.addSomeItemView(allHistoryBeenList);
                hiddingLoadLayout();
                HistoryCodeDao.delectAll();
                for (AllHistoryBean allHistoryBean:allHistoryBeenList)
                HistoryCodeDao.insertHistoryCode(allHistoryBean);
            }
        });
    }
}
