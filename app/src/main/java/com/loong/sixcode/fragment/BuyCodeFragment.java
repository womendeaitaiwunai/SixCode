package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.loong.sixcode.R;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyCodeFragment extends Fragment {
    private RadioGroup radioGroup;
    private RecyclerView codeRecycle,resultRecycle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        codeRecycle= (RecyclerView) view.findViewById(R.id.code_recycle);

    }
}
