package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;

/**
 * Created by lxl on 2017/6/10.
 */

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.MViewHolder> {
    private ItemOnClickListener itemOnClickListener;
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_code,null);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int position) {
        holder.superTextView.setText(position+1+"");
        holder.superTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickListener!=null){
                    itemOnClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 49;
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        SuperTextView superTextView;
        public MViewHolder(View itemView) {
            super(itemView);
            superTextView= (SuperTextView) itemView.findViewById(R.id.code_num);
        }
    }

    public interface ItemOnClickListener{
        void onClick(int position);
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }
}
