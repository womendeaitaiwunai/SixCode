package com.loong.sixcode.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lxl on 2017/6/14.
 * 简单的基础类，整合了整体点击和部分view的点击事件
 */

public abstract class BaseRecycleAdapter<VH extends RecyclerView.ViewHolder,V> extends RecyclerView.Adapter<VH> {
    private OnViewClickListener onViewClickListener;
    private OnItemViewClickListener onItemViewClickListener;
    private int[] intIds;
    private Context context;
    private List<V> mineDataList=new ArrayList<>();
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        mineDataList=getMyItemData();
        return getViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (onItemViewClickListener!=null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClickListener.itemViewClick(mineDataList.get(position));
            }
        });
        if (onViewClickListener!=null&&intIds!=null&&intIds.length>0){
            for (final int ids:intIds){
                View view=holder.itemView.findViewById(ids);
                if (view!=null){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onViewClickListener.viewClick(mineDataList.get(position),ids);
                        }
                    });
                }else {
                    Toast.makeText(context, "Holder View Id Is Null", Toast.LENGTH_SHORT).show();
                }
            }
        }
        onMyBindViewHolder(holder,position);
    }

    protected abstract VH getViewHolder(ViewGroup parent);
    protected abstract List<V> getMyItemData();
    protected abstract void onMyBindViewHolder(VH holder, final int position);

    @Override
    public int getItemCount() {
        return getMyItemData().size();
    }

    public interface OnViewClickListener{
        void viewClick(V v,int clickId);
    }

    public interface OnItemViewClickListener{
        void itemViewClick(V v);
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener){
        this.onViewClickListener=onViewClickListener;
    }

    public void setClickViewIds(int... clickIds){
        this.intIds=clickIds;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener){
        this.onItemViewClickListener=onItemViewClickListener;
    }

    public void addItemView(V value){
        mineDataList.add(value);
        notifyDataSetChanged();
    }

    public void addSomeItemView(List<V> values){
        mineDataList.addAll(values);
        notifyDataSetChanged();
    }

    public void refreshView(){
        mineDataList=new ArrayList<>();
        notifyDataSetChanged();
    }

}
