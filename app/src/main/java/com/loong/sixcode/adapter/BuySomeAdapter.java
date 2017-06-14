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

public class BuySomeAdapter extends RecyclerView.Adapter<BuySomeAdapter.MViewHolder> {
    List<Integer> someNumbers=new ArrayList<>();
    public BuySomeAdapter(List<Integer> someNumbers){
        this.someNumbers=someNumbers;
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buy_view,null,false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        holder.someCode.setText(someNumbers.get(position)+1+"");
    }

    @Override
    public int getItemCount() {
        return someNumbers.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        SuperTextView someCode;
        public MViewHolder(View itemView) {
            super(itemView);
            someCode= (SuperTextView) itemView.findViewById(R.id.code_num);
        }
    }

    public void addItemView(int itemNum){
        someNumbers.add(itemNum);
        notifyDataSetChanged();
    }
}
