package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.loong.sixcode.R;
import com.loong.sixcode.adapter.CodeAdapter;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyCodeFragment extends Fragment {
    private RadioGroup radioGroup;
    private RecyclerView codeRecycle,resultRecycle;
    private CodeAdapter codeAdapter;
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
        radioGroup= (RadioGroup) view.findViewById(R.id.select_group);
    }

    private void initData() {
        codeAdapter=new CodeAdapter();
        GridLayoutManager manager=new GridLayoutManager(getActivity(),7);
        codeRecycle.setLayoutManager(manager);
        codeRecycle.setAdapter(codeAdapter);
    }
}
