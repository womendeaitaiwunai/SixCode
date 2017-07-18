//package com.loong.sixcode.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.loong.sixcode.R;
//import com.loong.sixcode.base.BaseRecycleAdapter;
//
//import java.util.List;
//
///**
// * Created by lxl on 2017/7/12.
// */
//
//public class NextAdapter extends BaseRecycleAdapter<RecyclerView.ViewHolder,String> {
//    public NextAdapter(List<String> mineDataList) {
//        super(mineDataList);
//    }
//    private int IsTitle=1;//是否是头部的标记
//    private int IsItem=2;//只是子视图
//    private int [] titleItemPosition=new int[]{};//头部视图的位置
//    private String[] titleItemName=new String[]{};//头部视图的文字
//
//
//
//    @Override
//    public int getItemViewType(int position) {
//        for (int i=0;i<titleItemPosition.length;i++){
//            if (position==titleItemPosition[i]){
//                return IsTitle;
//            }
//        }
//        return IsItem;
//    }
//
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType==IsTitle){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adater_text1,null,false);
//            return new MTitleViewHolder(view);
//        }else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_test2,null,false);
//            return new MViewHolder(view);
//        }
//    }
//
//    /**
//     * 判断这位置是不是Title
//     * @param position
//     * @return
//     */
//    private boolean isTitlePosition(int position){
//        for (int i = 0; i < titleItemPosition.length; i++) {
//            if (position==titleItemPosition[i]){
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//
//    /**
//     * 设置头部位置
//     * @param positions 所有title的位置
//     */
//    public void setTitleItemPosition(int... positions){
//        for (int position:positions){
//            getAllData().add(position,null);
//        }
//        this.titleItemPosition=positions;
//    }
//
//    /**
//     * 设置头部位的文字
//     * @param titleText 所有title的文字
//     */
//    public void setTitleText(String... titleText){
//        if (this.titleItemPosition.length==0) {
//            Log.e("HomePageGridviewAdapter","没设置Title的位置");
//            return;
//        }
//        if (titleText.length!=this.titleItemPosition.length){
//            Log.e("HomePageGridviewAdapter","设置的title位置数量和text不等");
//            return;
//        }
//        this.titleItemName=titleText;
//    }
//
//
//    @Override
//    protected MViewHolder getViewHolder(ViewGroup parent) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_test2,parent,false);
//        return new MViewHolder(view);
//    }
//
//    @Override
//    protected void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position, List<String> mineDataList) {
//        if (holder instanceof MTitleViewHolder){
//            ((MTitleViewHolder) holder).textView.setText(titleItemName[getTitlePosition(position)]);
//        }
//    }
//
//    /**
//     * 获取头部的位置
//     * @param holdPosition
//     * @return
//     */
//    private int getTitlePosition(int holdPosition){
//        int position=0;
//        for (int i = 0; i < holdPosition; i++) {
//            if (getAllData().get(i)==null){
//                position=position+1;
//            }
//        }
//        return position;
//    }
//
//    public class MViewHolder extends RecyclerView.ViewHolder{
//
//        public MViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    public class MTitleViewHolder extends RecyclerView.ViewHolder{
//        TextView textView;
//        public MTitleViewHolder(View itemView) {
//            super(itemView);
//            textView= (TextView) itemView.findViewById(R.id.name);
//        }
//    }
//}
