package com.loong.sixcode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.bean.BuyResultBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyResultAdapter extends RecyclerView.Adapter<BuyResultAdapter.MViewHolder> {
    List<BuyResultBean> buyResultBeenList=new ArrayList<>();
    private Context context;
    public BuyResultAdapter(List<BuyResultBean> buyResultBeenList){
        this.buyResultBeenList=buyResultBeenList;
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_buy_result,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        long timeData=buyResultBeenList.get(position).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");
        String time=sdf.format(timeData);
        holder.time.setText(time);

        BuyResultBean resultBean=buyResultBeenList.get(position);
        holder.allMoney.setText(resultBean.getMoney()+0+"块");
        int resultBuyNum=resultBean.getBuyNum().size();
        holder.buyResult.removeAllViews();
        for (int i = 0; i <resultBuyNum; i++) {
            View buyView=LayoutInflater.from(context).inflate(R.layout.layout_buy_view,null,false);
            SuperTextView buyNum= (SuperTextView) buyView.findViewById(R.id.code_num);
            buyNum.setText( resultBean.getBuyNum().get(i));
            holder.buyResult.addView(buyNum);
        }
    }

    @Override
    public int getItemCount() {
        return buyResultBeenList.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        LinearLayout buyResult;
        TextView allMoney;
        public MViewHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.time);
            buyResult= (LinearLayout) itemView.findViewById(R.id.but_code);
            allMoney= (TextView) itemView.findViewById(R.id.all_money);
        }
    }

    public void addItemView(BuyResultBean buyResultBean){
        buyResultBeenList.add(buyResultBean);
        notifyDataSetChanged();
    }
}
