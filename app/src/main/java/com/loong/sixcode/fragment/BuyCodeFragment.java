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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.adapter.CodeAdapter;
import com.loong.sixcode.bean.BuyResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyCodeFragment extends Fragment {
    private RadioGroup radioGroup;
    private RecyclerView codeRecycle,resultRecycle;
    private CodeAdapter codeAdapter;
    private BuyResultAdapter buyResultAdapter;
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isSingle=(checkedId==R.id.single);
            }
        });
    }

    private void initData() {
        codeAdapter=new CodeAdapter();
        GridLayoutManager manager=new GridLayoutManager(getActivity(),7);
        codeRecycle.setLayoutManager(manager);
        codeRecycle.setAdapter(codeAdapter);

        List<BuyResultBean> buyResultBeenList=new ArrayList<>();
        buyResultAdapter=new BuyResultAdapter(buyResultBeenList);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        resultRecycle.setLayoutManager(manager1);
        resultRecycle.setAdapter(buyResultAdapter);

        codeAdapter.setItemOnClickListener(new CodeAdapter.ItemOnClickListener() {
            @Override
            public void onClick(int position) {
                if (isSingle)showSingeBuyDialog(position);
            }
        });
    }

    private void showSingeBuyDialog(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        initDialogView(position);
        builder.setView(dialogView);
        buyDialog=builder.show();
    }

    private View dialogView;
    private EditText money;
    private Dialog buyDialog;
    private void initDialogView(final int position) {
        SuperTextView diaCode;
        TextView one,two,three,four;
        TextView five,six,seven,eight;
        TextView sure,cancel;
        dialogView=LayoutInflater.from(getActivity()).inflate(R.layout.layout_single_dialog,null,false);
        diaCode= (SuperTextView) dialogView.findViewById(R.id.code_num);
        money= (EditText) dialogView.findViewById(R.id.money);
        one= (TextView) dialogView.findViewById(R.id.one);
        two= (TextView) dialogView.findViewById(R.id.two);
        three= (TextView) dialogView.findViewById(R.id.three);
        four= (TextView) dialogView.findViewById(R.id.four);
        five= (TextView) dialogView.findViewById(R.id.five);
        six= (TextView) dialogView.findViewById(R.id.six);
        seven= (TextView) dialogView.findViewById(R.id.seven);
        eight= (TextView) dialogView.findViewById(R.id.eight);
        sure= (TextView) dialogView.findViewById(R.id.sure);
        cancel= (TextView) dialogView.findViewById(R.id.cancel);

        diaCode.setText(position+1+"");

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });


        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });


        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView(v);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyResultBean buyResultBean=new BuyResultBean();
                List<String> buyNum=new ArrayList<>();
                buyNum.add(position+1+"");
                buyResultBean.setBuyNum(buyNum);
                buyResultBean.setMoney(moneyNum);
                buyResultBean.setTime(System.currentTimeMillis());
                buyResultAdapter.addItemView(buyResultBean);
                buyDialog.dismiss();
            }
        });
    }

    private int moneyNum=0;
    private void checkView(View view){
        if (view.getTag()!=null) {
            moneyNum= Integer.parseInt(view.getTag().toString());
            money.setText(moneyNum+"");
            money.setSelection((moneyNum+"").length());
        }
    }
}
