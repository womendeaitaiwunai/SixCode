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
 * 使用方法：
 * public class CodeAdapter extends BaseRecycleAdapter<CodeAdapter.MViewHolder,Integer> {
 * public CodeAdapter(List<Integer> mineDataList) {
 * super(mineDataList);
 * }
 *
 * @Override
 * protected MViewHolder getViewHolder(ViewGroup parent) {
 * View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_code,null);
 * setClickViewIds(R.id.code_num);//设置点击事件
 * return new MViewHolder(view);
 * }

 * @Override
 * protected void onMyBindViewHolder(MViewHolder holder, int position) {
 * holder.superTextView.setText(position+1+"");
 * }


 * public class MViewHolder extends RecyclerView.ViewHolder{
 * SuperTextView superTextView;
 * public MViewHolder(View itemView) {
 * super(itemView);
 * superTextView= (SuperTextView) itemView.findViewById(R.id.code_num);
 * }
 * }
 */

public abstract class BaseRecycleAdapter<VH extends RecyclerView.ViewHolder,V> extends RecyclerView.Adapter<VH> {
    private OnViewClickListener<V> onViewClickListener;
    private OnItemViewClickListener<V> onItemViewClickListener;
    private int[] intIds;
    private Context context;
    private List<V> mineDataList=new ArrayList<>();
    public BaseRecycleAdapter(List<V> mineDataList){
        this.mineDataList=mineDataList;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context=parent.getContext();
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
                    Toast.makeText(context,"The Id Isn't Belong The ViewHolder", Toast.LENGTH_SHORT).show();
                }
            }
        }
        onMyBindViewHolder(holder,position);
    }

    protected abstract VH getViewHolder(ViewGroup parent);
    protected abstract void onMyBindViewHolder(VH holder, final int position);

    @Override
    public int getItemCount() {
        return mineDataList.size();
    }


    public interface OnViewClickListener<V>{
        void viewClick(V v,int clickId);
    }

    public interface OnItemViewClickListener<V>{
        void itemViewClick(V v);
    }

    public void setOnViewClickListener(OnViewClickListener<V> onViewClickListener){
        this.onViewClickListener=onViewClickListener;
    }

    public void setClickViewIds(int... clickIds){
        this.intIds=clickIds;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener<V> onItemViewClickListener){
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
