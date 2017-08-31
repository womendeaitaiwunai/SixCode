package com.loong.sixcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseRecycleAdapter;
import com.loong.sixcode.bean.AllHistoryBean;
import com.loong.sixcode.bean.HistoryCodeBean;

import java.util.List;

/**
 * Created by lxl on 2017/7/7.
 */

public class HistoryCodeAdapter extends BaseRecycleAdapter<HistoryCodeAdapter.MViewHolder,AllHistoryBean> {
    public HistoryCodeAdapter(List<AllHistoryBean> mineDataList) {
        super(mineDataList);
    }

    @Override
    protected MViewHolder getViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_code,parent,false);
        return new MViewHolder(view);
    }

    @Override
    protected void onMyBindViewHolder(MViewHolder holder, int position, List<AllHistoryBean> mineDataList) {
        AllHistoryBean itemsBean=mineDataList.get(position);
        String number=itemsBean.getNumbers();
        String sx=itemsBean.getSx();
        String[] numbers=number.split(",");
        String[] sxs=sx.split(",");
        if (numbers.length<7) return;
        holder.oneCode.setText(numbers[0]);
        holder.twoCode.setText(numbers[1]);
        holder.threeCode.setText(numbers[2]);
        holder.fourCode.setText(numbers[3]);
        holder.fiveCode.setText(numbers[4]);
        holder.sixCode.setText(numbers[5]);
        holder.sevenCode.setText(numbers[6]);

        holder.oneCodeValue.setText(sxs[0]);
        holder.twoCodeValue.setText(sxs[1]);
        holder.threeCodeValue.setText(sxs[2]);
        holder.fourCodeValue.setText(sxs[3]);
        holder.fiveCodeValue.setText(sxs[4]);
        holder.sixCodeValue.setText(sxs[5]);
        holder.sevenCodeValue.setText(sxs[6]);
        String timeAndNo=itemsBean.getPeriod();
        holder.codeNo.setText(timeAndNo.substring(timeAndNo.length()-4,timeAndNo.length()));
        holder.time.setText(timeAndNo.substring(0,timeAndNo.length()-4));
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        private SuperTextView oneCode;
        private TextView oneCodeValue;
        private SuperTextView twoCode;
        private TextView twoCodeValue;
        private SuperTextView threeCode;
        private TextView threeCodeValue;
        private SuperTextView fourCode;
        private TextView fourCodeValue;
        private SuperTextView fiveCode;
        private TextView fiveCodeValue;
        private SuperTextView sixCode;
        private TextView sixCodeValue;
        private SuperTextView sevenCode;
        private TextView sevenCodeValue;
        private TextView codeNo;
        private TextView time;
        public MViewHolder(View view) {
            super(view);
            oneCode = (SuperTextView) view.findViewById(R.id.one_code);
            oneCodeValue = (TextView) view.findViewById(R.id.one_code_value);
            twoCode = (SuperTextView) view.findViewById(R.id.two_code);
            twoCodeValue = (TextView) view.findViewById(R.id.two_code_value);
            threeCode = (SuperTextView) view.findViewById(R.id.three_code);
            threeCodeValue = (TextView) view.findViewById(R.id.three_code_value);
            fourCode = (SuperTextView) view.findViewById(R.id.four_code);
            fourCodeValue = (TextView) view.findViewById(R.id.four_code_value);
            fiveCode = (SuperTextView) view.findViewById(R.id.five_code);
            fiveCodeValue = (TextView) view.findViewById(R.id.five_code_value);
            sixCode = (SuperTextView) view.findViewById(R.id.six_code);
            sixCodeValue = (TextView) view.findViewById(R.id.six_code_value);
            sevenCode = (SuperTextView) view.findViewById(R.id.seven_code);
            sevenCodeValue = (TextView) view.findViewById(R.id.seven_code_value);
            codeNo = (TextView) view.findViewById(R.id.code_no);
            time = (TextView) view.findViewById(R.id.my_time);
        }
    }
}
