package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.adapter.HistoryCodeAdapter;
import com.loong.sixcode.base.BaseFragment;
import com.loong.sixcode.bean.HistoryCodeBean;
import com.loong.sixcode.util.okhttp.PixelOkHttpUtil;
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
    private List<HistoryCodeBean.ItemsBean> historyList;
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
        recyclerView= (RecyclerView) view.findViewById(R.id.history_buy_recycle);
        historyList=new ArrayList<>();
        adapter=new HistoryCodeAdapter(historyList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        OkHttpUtils.get().url("http://m1.1396ck.com/stat/5?client_lang=zh-tw&year="+year).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getActivity(), " 获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                HistoryCodeBean historyCodeBean=gson.fromJson(response,HistoryCodeBean.class);
                adapter.cleanView();
                adapter.addSomeItemView(historyCodeBean.getItems());
            }
        });
    }
}
