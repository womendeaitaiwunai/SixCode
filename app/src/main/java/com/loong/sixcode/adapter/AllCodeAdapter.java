package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.BuyResultBean;

import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class AllCodeAdapter extends BaseRecycleAdapter<AllCodeAdapter.MViewHolder,Integer> {
    private boolean isEdit=false;
    public AllCodeAdapter(List<Integer> mineDataList,boolean isEdit) {
        super(mineDataList);
        this.isEdit=isEdit;
    }

    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_code,parent,false);
        return new MViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 49;
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<Integer> mineDataList) {
        holder.codeNum.setText(position+1+"");
        holder.codeMoney.setText(mineDataList.get(position)+"");
        if (isEdit) holder.codeMoney.setEnabled(true);
        else holder.codeMoney.setEnabled(false);
//        for (BuyResultBean buyResultBean:mineDataList){
//            List<String> buyNum=buyResultBean.getBuyNum();
//            for (String buyN:buyNum){
//                if (TextUtils.equals(buyN,holder.codeNum.getText().toString())){
//                    int money=Integer.parseInt(TextUtils.isEmpty(holder.codeMoney.getText().toString())
//                            ?"0":holder.codeMoney.getText().toString());
//                    holder.codeMoney.setText(money+(buyResultBean.getMoney()/buyNum.size())+"");
//                }
//            }
//        }
    }

    class MViewHolder extends RecyclerView.ViewHolder{
        TextView codeNum;
        EditText codeMoney;
        public MViewHolder(View itemView) {
            super(itemView);
            codeNum= (TextView) itemView.findViewById(R.id.code_num);
            codeMoney= (EditText) itemView.findViewById(R.id.code_money);
        }
    }
}