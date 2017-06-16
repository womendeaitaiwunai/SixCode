package com.loong.sixcode.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.BuySomeAdapter;
import com.loong.sixcode.bean.BuyResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/14.
 */

public class SureBuyDialog extends Dialog {
    private List<String> buyAllNum;
    private BuyResultCallBack buyResultCallBack;
    private RecyclerView buyDialogRecycle;
    private BuySomeAdapter adapter;
    public SureBuyDialog(Context context,List<String> buyNum) {
        super(context);
        this.buyAllNum=buyNum;
        initDialogView();
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        setContentView(dialogView);
        adapter=new BuySomeAdapter(buyAllNum);
        LinearLayoutManager manager=new LinearLayoutManager(this.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        buyDialogRecycle.setLayoutManager(manager);
        buyDialogRecycle.setAdapter(adapter);
    }

    private View dialogView;
    private EditText money;
    private void initDialogView() {
        TextView one,two,three,four;
        TextView five,six,seven,eight;
        TextView sure,cancel;
        dialogView= LayoutInflater.from(getContext()).inflate(R.layout.layout_single_dialog,null,false);
        buyDialogRecycle= (RecyclerView) dialogView.findViewById(R.id.buy_dialog_recycle);
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

        //diaCode.setText(position+1+"");

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
                SureBuyDialog.this.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mMoney=0;
                try {
                    mMoney=Integer.parseInt(money.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(getContext(), "金额输入有误", Toast.LENGTH_SHORT).show();
                }
                if (mMoney==0) return;
                BuyResultBean buyResultBean=new BuyResultBean();
                List<String> buyNum=new ArrayList<>();
                buyNum.addAll(buyAllNum);
                buyResultBean.setBuyNum(buyNum);
                buyResultBean.setMoney(mMoney*buyNum.size());
                buyResultBean.setTime(System.currentTimeMillis());
                if (buyResultCallBack!=null) {
                    buyResultCallBack.resultBack(buyResultBean);
                    buyDialogRecycle.smoothScrollToPosition(buyNum.size());
                }
                SureBuyDialog.this.dismiss();
//                buyResultAdapter.addItemView(buyResultBean);
//                buyDialog.dismiss();
//                resultRecycle.smoothScrollToPosition(buyResultAdapter.getItemCount()-1);
            }
        });
        this.setContentView(dialogView);
    }

    private int moneyNum=0;
    private void checkView(View view){
        if (view.getTag()!=null) {
            moneyNum= Integer.parseInt(view.getTag().toString());
            money.setText(moneyNum+"");
            money.setSelection((moneyNum+"").length());
        }
    }

    public void setBuyResultCallBack(BuyResultCallBack buyResultCallBack){
        this.buyResultCallBack=buyResultCallBack;
    }

    public interface BuyResultCallBack{
        void resultBack(BuyResultBean buyResultBean);
    }
}
