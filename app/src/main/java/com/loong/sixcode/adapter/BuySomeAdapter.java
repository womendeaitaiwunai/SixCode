package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/14.
 */

public class BuySomeAdapter extends BaseRecycleAdapter<BuySomeAdapter.MViewHolder,String> {
    public BuySomeAdapter(List<String> someNumbers){
        super(someNumbers);
    }


    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buy_view,parent,false);
        return new MViewHolder(view);
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<String> mineDataList) {
        holder.someCode.setText(mineDataList.get(position));
    }


    public class MViewHolder extends RecyclerView.ViewHolder{
        SuperTextView someCode;
        public MViewHolder(View itemView) {
            super(itemView);
            someCode= (SuperTextView) itemView.findViewById(R.id.code_num);
        }
    }

}
