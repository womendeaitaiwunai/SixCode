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
import com.loong.sixcode.bean.CodeBean;

import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class AllCodeAdapter extends BaseRecycleAdapter<AllCodeAdapter.MViewHolder,CodeBean> {
    private boolean isEdit=false;
    public AllCodeAdapter(List<CodeBean> mineDataList,boolean isEdit) {
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
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<CodeBean> mineDataList) {
        holder.codeNum.setText(mineDataList.get(position).getCode());
        holder.codeMoney.setText((mineDataList.get(position).getMoney())==0?"":(mineDataList.get(position).getMoney())+"");
        if (isEdit) holder.codeMoney.setEnabled(true);
        else holder.codeMoney.setEnabled(false);
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
