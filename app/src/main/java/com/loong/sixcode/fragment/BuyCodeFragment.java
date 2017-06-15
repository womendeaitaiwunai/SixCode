package com.loong.sixcode.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.adapter.BuySomeAdapter;
import com.loong.sixcode.adapter.CodeAdapter;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.BuyResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyCodeFragment extends Fragment {
    private RadioGroup radioGroup;
    private RecyclerView codeRecycle,resultRecycle,someRecycle;
    private CodeAdapter codeAdapter;
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
        radioGroup= (RadioGroup) view.findViewById(R.id.select_group);
        nextView= (LinearLayout) view.findViewById(R.id.next_view);
        someRecycle= (RecyclerView) nextView.findViewById(R.id.some_select_recycle);

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
        codeAdapter=new CodeAdapter();
        GridLayoutManager manager=new GridLayoutManager(getActivity(),7);
        codeRecycle.setLayoutManager(manager);
        codeRecycle.setAdapter(codeAdapter);

        List<Integer> someBuy=new ArrayList<>();
        buySomeAdapter=new BuySomeAdapter(someBuy);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        someRecycle.setLayoutManager(layoutManager);
        someRecycle.setAdapter(buySomeAdapter);

        List<BuyResultBean> buyResultBeenList=new ArrayList<>();
        buyResultAdapter=new BuyResultAdapter(buyResultBeenList);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        resultRecycle.setLayoutManager(manager1);
        resultRecycle.setAdapter(buyResultAdapter);

        codeAdapter.setOnItemViewClickListener(new BaseRecycleAdapter.OnItemViewClickListener<Integer>() {
            @Override
            public void itemViewClick(Integer integer) {
                if (isSingle) showSingeBuyDialog(integer);
                else buySomeAdapter.addItemView(integer);
            }
        });

    }

    private void showSingeBuyDialog(int position){
        Toast.makeText(getActivity(), "点击了"+(position+1), Toast.LENGTH_SHORT).show();
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//        initDialogView(position);
//        builder.setView(dialogView);
//        buyDialog=builder.show();
    }

}
