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
    private View buyView;
    public BuyResultAdapter(List<BuyResultBean> buyResultBeenList){
        this.buyResultBeenList=buyResultBeenList;
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        buyView=LayoutInflater.from(context).inflate(R.layout.layout_buy_view,null,false);
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_buy_result,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        long timeData=buyResultBeenList.get(position).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time=sdf.format(timeData);
        holder.time.setText(time);

//        for (int i = 0; i < buyResultBeenList.get(position).getBuyNum().size(); i++) {
//            SuperTextView buyNum= (SuperTextView) buyView.findViewById(R.id.code_num);
//            buyNum.setText( buyResultBeenList.get(position).getBuyNum().get(i));
//            holder.buyResult.addView(buyNum);
//        }
    }

    @Override
    public int getItemCount() {
        return buyResultBeenList.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        LinearLayout buyResult;
        public MViewHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.time);
            buyResult= (LinearLayout) itemView.findViewById(R.id.but_code);
        }
    }

    public void addItemView(BuyResultBean buyResultBean){
        buyResultBeenList.add(buyResultBean);
        notifyDataSetChanged();
    }
}
