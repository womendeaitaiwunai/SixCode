package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/6/14.
 */

public class BuySomeDialogAdapter extends RecyclerView.Adapter {

    private List<String> stringList=new ArrayList<>();
    public BuySomeDialogAdapter(List<String> stringList){

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buy_some,null,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        SuperTextView superTextView;
        public MViewHolder(View itemView) {
            super(itemView);
            superTextView= (SuperTextView) itemView.findViewById(R.id.code_num);
        }
    }
}
