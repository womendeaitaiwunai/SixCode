package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.LineBean;
import com.loong.sixcode.bean.WaveBean;

import java.util.List;

/**
 * Created by lxl on 2017/7/13.
 */

public class WaveAdapter extends BaseRecycleAdapter<WaveAdapter.MViewHolder,WaveBean> {
    public WaveAdapter(List<WaveBean> mineDataList) {
        super(mineDataList);
    }

    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_animal,parent,false);
        return new MViewHolder(view);
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<WaveBean> mineDataList) {
        WaveBean waveBean=mineDataList.get(position);
        holder.codeNum.setText(waveBean.getName());
        holder.codeMoney.setText(waveBean.getCode());
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
