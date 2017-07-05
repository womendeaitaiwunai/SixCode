package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loong.sixcode.R;
import com.loong.sixcode.adapter.AllCodeAdapter;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyResultBean;
import com.loong.sixcode.greendao.BuyCodeDbDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 * 购买所有的码
 */

public class AllCodeFragment extends Fragment {
    private AllCodeAdapter adapter;
    private RecyclerView recyclerView;
    private List<Integer> mineResult=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       initListData();
        return inflater.inflate(R.layout.fragment_all,container,false);
    }

    private void initListData() {
        mineResult.clear();
        for (int i = 0; i < 50; i++) {
            mineResult.add(0);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        recyclerView= (RecyclerView) view.findViewById(R.id.all_code_recycle);
    }

    private void initData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        adapter=new AllCodeAdapter(mineResult,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void refreshView(){
        adapter.cleanView();
        adapter.addSomeItemView(mineResult);
    }

    public void addData(){
        changeType();
        adapter.cleanView();
        adapter.addSomeItemView(mineResult);
    }

    private void changeType(){
        initListData();
        List<BuyCodeDao> buyCodeDaos= BuyCodeDbDao.queryAll();
        for (BuyCodeDao buyCodeDao:buyCodeDaos){
            List<String> list=new ArrayList<>();
            list.addAll(Arrays.asList(buyCodeDao.getBuyCode().split("、")));
            for (String buyCode:list){
                int code=Integer.parseInt(buyCode);
                int firstValue=mineResult.get(code-1);
                int nextValue=firstValue+buyCodeDao.getMoney()/list.size();
                mineResult.remove(code-1);
                mineResult.add(code-1,nextValue);
            }
        }
    }
}
