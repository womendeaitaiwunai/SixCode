package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.BuyResultBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lxl on 2017/6/10.
 */

public class BuyResultAdapter extends BaseRecycleAdapter<BuyResultAdapter.MViewHolder,BuyResultBean> {
    public BuyResultAdapter(List<BuyResultBean> buyResultBeenList){
        super(buyResultBeenList);
    }

    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buy_result,parent,false);
        setClickViewIds(R.id.change_result);
        return new MViewHolder(view);
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<BuyResultBean> mineDataList) {
        long timeData=mineDataList.get(position).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String time=sdf.format(timeData);
        holder.time.setText(time);

        BuyResultBean resultBean=mineDataList.get(position);
        holder.allMoney.setText(resultBean.getMoney()+0+"块");
        int resultBuyNum=resultBean.getBuyNum().size();
        String result="";
        for (int i = 0; i <resultBuyNum; i++) {
            result=result+resultBean.getBuyNum().get(i)+"、";
        }
        if (result.endsWith("、")) result=result.substring(0,result.length()-1);
        if (resultBuyNum>1) result=result+"各"+(resultBean.getMoney()/resultBuyNum)+"块";
        holder.buyResult.setText(result);
    }

    class MViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView buyResult;
        TextView allMoney;
        MViewHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.time);
            buyResult= (TextView) itemView.findViewById(R.id.but_code);
            allMoney= (TextView) itemView.findViewById(R.id.all_money);
        }
    }

}
