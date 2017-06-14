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
 * Created by lxl on 2017/6/10.
 */

public class CodeAdapter extends BaseRecycleAdapter<CodeAdapter.MViewHolder,Integer> {
    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_code,null);
        setClickViewIds(R.id.code_num);
        return new MViewHolder(view);
    }

    @Override
    protected List<Integer> getMyItemData() {
        List<Integer> itemDataList=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemDataList.add(i);
        }
        return itemDataList;
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position) {
        holder.superTextView.setText(position+1+"");
    }


    public class MViewHolder extends RecyclerView.ViewHolder{
        SuperTextView superTextView;
        public MViewHolder(View itemView) {
            super(itemView);
            superTextView= (SuperTextView) itemView.findViewById(R.id.code_num);
        }
    }

}
