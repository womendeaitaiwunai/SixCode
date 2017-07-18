package com.loong.sixcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loong.sixcode.R;
import com.loong.sixcode.adapter.AllCodeAdapter;
import com.loong.sixcode.adapter.AnimalAdapter;
import com.loong.sixcode.adapter.LineAdapter;
import com.loong.sixcode.adapter.WaveAdapter;
import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.base.BaseFragment;
import com.loong.sixcode.bean.AnimalBean;
import com.loong.sixcode.bean.LineBean;
import com.loong.sixcode.bean.WaveBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/7/10.
 */

public class AnimalFragment extends BaseFragment{
    private RecyclerView oneRecycle,twoRecycle,threeRecycle;
    private AnimalAdapter animalAdapter;
    private LineAdapter lineAdapter;
    private WaveAdapter waveAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        List<AnimalBean> animalBeanList= BaseApplication.getDaoInstant().getAnimalBeanDao().loadAll();
        List<LineBean> lineBeanList= BaseApplication.getDaoInstant().getLineBeanDao().loadAll();
        List<WaveBean> waveBeanList= BaseApplication.getDaoInstant().getWaveBeanDao().loadAll();

        oneRecycle= (RecyclerView) view.findViewById(R.id.one_recycle);
        twoRecycle= (RecyclerView) view.findViewById(R.id.two_recycle);
        threeRecycle= (RecyclerView) view.findViewById(R.id.three_recycle);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        animalAdapter=new AnimalAdapter(animalBeanList);
        oneRecycle.setLayoutManager(gridLayoutManager);
        oneRecycle.setAdapter(animalAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lineAdapter=new LineAdapter(lineBeanList);
        twoRecycle.setLayoutManager(linearLayoutManager);
        twoRecycle.setAdapter(lineAdapter);

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        waveAdapter=new WaveAdapter(waveBeanList);
        threeRecycle.setLayoutManager(linearLayoutManager1);
        threeRecycle.setAdapter(waveAdapter);
    }

}
