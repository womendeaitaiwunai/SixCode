package com.loong.sixcode.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loong.sixcode.activity.BigHistoryActivity;
import com.loong.sixcode.activity.CodeCameraActivity;
import com.loong.sixcode.activity.HistoryBuyActivity;
import com.loong.sixcode.R;
import com.loong.sixcode.adapter.AllCodeAdapter;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.CodeBean;
import com.loong.sixcode.greendao.BuyCodeDbDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by lxl on 2017/6/10.
 * 购买所有的码
 */

public class AllCodeFragment extends Fragment implements View.OnClickListener{
    private AllCodeAdapter adapter;
    private RecyclerView recyclerView;
    private ColumnChartView columnChartView;
    private Spinner viewType,dataType;
    private TextView money,code;
    private LinearLayout moneyAndCode;
    private ImageView allScn;
    private List<Integer> mineResult=new ArrayList<>();
    private List<CodeBean> codeBeanList=new ArrayList<>();
    private List<CodeBean> mCodeBean;
    private UpdateViewReceiver updateViewReceiver;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initListData();
        initCodeList();
        return inflater.inflate(R.layout.fragment_all,container,false);
    }

    private void initCodeList() {
        for (int i = 0; i < 50; i++) {
            CodeBean codeBean=new CodeBean();
            codeBean.setCode((i+1)+"");
            codeBean.setMoney(0);
            codeBeanList.add(codeBean);
        }
    }

    private void initListData() {
        mineResult.clear();
        for (int i = 0; i < 50; i++) {
            mineResult.add(0);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        recyclerView= (RecyclerView) view.findViewById(R.id.all_code_recycle);
        columnChartView= (ColumnChartView) view.findViewById(R.id.column_chart);
        viewType= (Spinner) view.findViewById(R.id.view_type);
        dataType= (Spinner) view.findViewById(R.id.data_type);
        money= (TextView) view.findViewById(R.id.money);
        code= (TextView) view.findViewById(R.id.code);
        allScn= (ImageView) view.findViewById(R.id.all_scn);
        moneyAndCode= (LinearLayout) view.findViewById(R.id.money_and_code);
        view.findViewById(R.id.edit_all_code).setOnClickListener(this);
        view.findViewById(R.id.history_buy).setOnClickListener(this);
        view.findViewById(R.id.two_code_add).setOnClickListener(this);
        view.findViewById(R.id.all_scn).setOnClickListener(this);
    }



    private void initData() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        adapter=new AllCodeAdapter(codeBeanList,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        List<String> viewTypeData=new ArrayList<>();
        viewTypeData.add("表格形式");
        viewTypeData.add("图形形式");
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,viewTypeData);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        viewType.setAdapter(adapter);
        //添加事件Spinner事件监听
        viewType.setOnItemSelectedListener(new SpinnerSelectedListener(true));
        //设置默认值
        viewType.setVisibility(View.VISIBLE);

        List<String> dataTypeData=new ArrayList<>();
        dataTypeData.add("按号码排序");
        dataTypeData.add("按金额排序");
        ArrayAdapter dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,dataTypeData);
        //设置下拉列表的风格
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        dataType.setAdapter(dataAdapter);
        //添加事件Spinner事件监听
        dataType.setOnItemSelectedListener(new SpinnerSelectedListener(false));
        //设置默认值
        dataType.setVisibility(View.VISIBLE);
        addData();
        updateViewReceiver=new UpdateViewReceiver();
        IntentFilter intentFilter=new IntentFilter("UpdateAllCode");
        getActivity().registerReceiver(updateViewReceiver,intentFilter);
    }

    private class UpdateViewReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            addData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(updateViewReceiver);
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        private boolean isViewSpinner=true;
        public SpinnerSelectedListener(boolean isViewSpinner){
            this.isViewSpinner=isViewSpinner;
        }
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            moneyAndCode.setVisibility(View.GONE);
            if (isViewSpinner){
                if (arg2==0){
                    recyclerView.setVisibility(View.VISIBLE);
                    columnChartView.setVisibility(View.GONE);
                    allScn.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    columnChartView.setVisibility(View.VISIBLE);
                    allScn.setVisibility(View.VISIBLE);
                }
            }else {
                if (arg2==0){
                    Collections.sort(codeBeanList,new CodeComparator(CodeComparator.COMPARATOR_CODE));
                    adapter.cleanView();
                    adapter.addSomeItemView(codeBeanList);
                }else {
                    Collections.sort(codeBeanList,new CodeComparator(CodeComparator.COMPARATOR_MONEY));
                    adapter.cleanView();
                    adapter.addSomeItemView(codeBeanList);
                }
            }
            initChartView();
        }
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    public void addData(){
        changeType();
        adapter.cleanView();
        adapter.addSomeItemView(codeBeanList);
    }

    private void changeType(){
        initListData();
        List<BuyCodeDao> buyCodeDaos= BuyCodeDbDao.queryAll();
        for (BuyCodeDao buyCodeDao:buyCodeDaos){
            List<String> list=new ArrayList<>();
            list.addAll(Arrays.asList(buyCodeDao.getBuyCode().split("、")));
            for (String buyCode:list){
                int code=Integer.parseInt(buyCode);
                int firstValue=mineResult.get(code-1);
                int nextValue=firstValue+buyCodeDao.getMoney()/list.size();
                mineResult.remove(code-1);
                mineResult.add(code-1,nextValue);
            }
        }

        codeBeanList.clear();
        for (int i = 0; i < mineResult.size(); i++) {
            CodeBean codeBean=new CodeBean();
            codeBean.setCode((i+1)+"");
            codeBean.setMoney(mineResult.get(i));
            codeBeanList.add(codeBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_all_code:
                Collections.sort(codeBeanList,new CodeComparator(CodeComparator.COMPARATOR_MONEY));
                adapter.cleanView();
                adapter.addSomeItemView(codeBeanList);
                break;
            case R.id.history_buy:
                startActivity(new Intent(getActivity(), HistoryBuyActivity.class));
                break;
            case R.id.two_code_add:
                startActivity(new Intent(getActivity(), CodeCameraActivity.class));
                break;
            case R.id.all_scn:
                startActivity(new Intent(getActivity(), BigHistoryActivity.class));
                break;
        }
    }

    private List<CodeBean> checkCodeListSize(){
        List<CodeBean> codeBeans=new ArrayList<>();
        for (int i = 0; i < codeBeanList.size(); i++) {
            if (codeBeanList.get(i).getMoney()!=0) codeBeans.add(codeBeanList.get(i));
        }
        return codeBeans;
    }

    private void initChartView() {
        //每个集合显示几条柱子
        int numSubColumns = 1;
        //显示多少个集合
        mCodeBean= checkCodeListSize();
        int numColumns =mCodeBean.size();
        //保存所有的柱子
        List<Column> columns = new ArrayList<>();
        //保存每个竹子的值
        List<SubcolumnValue> values;
        List<AxisValue> axisXValues = new ArrayList<>();
        //对每个集合的柱子进行遍历
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubColumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                //if (mCodeBean.get(i).getMoney()==0) break;
                SubcolumnValue subcolumnValue=new SubcolumnValue();
                subcolumnValue.setValue(mCodeBean.get(i).getMoney());
                subcolumnValue.setColor(ChartUtils.pickColor());
                //subcolumnValue.setLabel(codeBeanList.get(i).getCode()+"");
                values.add(subcolumnValue);
                //设置X轴的柱子所对应的属性名称
                axisXValues.add(new AxisValue(i).setLabel(mCodeBean.get(i).getCode()+""));
            }
            //将每个属性的拥有的柱子，添加到Column中
            Column column = new Column(values);
            //是否显示每个柱子的Lable
            column.setHasLabels(true);
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            column.setHasLabelsOnlyForSelected(false);
            //将每个属性得列全部添加到List中
            columns.add(column);
        }
        //设置Columns添加到Data中
        ColumnChartData data = new ColumnChartData(columns);
        //设置X轴显示在底部，并且显示每个属性的Lable，字体颜色为黑色，X轴的名字为“学历”，每个柱子的Lable斜着显示，距离X轴的距离为8
        data.setAxisXBottom(new Axis(axisXValues).setHasLines(true).setTextColor(Color.RED).setTextSize(12).setName("购买的号码").setMaxLabelChars(3));
        //属性值含义同X轴
        data.setAxisYLeft(new Axis().setHasLines(true).setName("对应号码的钱").setLineColor(Color.GREEN).setTextColor(Color.RED).setMaxLabelChars(3));
        //最后将所有值显示在View中
        columnChartView.setContentDescription("买码设计图");
        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                moneyAndCode.setVisibility(View.VISIBLE);
                money.setText(mCodeBean.get(i).getMoney()+"块");
                code.setText(mCodeBean.get(i).getCode()+"号");
                //Toast.makeText(getActivity(), codeBeanList.get(i).getCode()+"--->"+codeBeanList.get(i).getMoney(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onValueDeselected() {
                moneyAndCode.setVisibility(View.GONE);
            }
        });
        columnChartView.setInteractive(true);//设置图表是否可以与用户互动
        columnChartView.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        columnChartView.setColumnChartData(data);
    }

    private static class CodeComparator implements Comparator<CodeBean> {
        public static int COMPARATOR_MONEY=0x01;
        public static int COMPARATOR_CODE=0x02;
        private int comparatorCode=0;
        public CodeComparator(int comparatorCode){
            this.comparatorCode=comparatorCode;
        }
        //按升序排列
        public int compare(CodeBean lhs, CodeBean rhs) {
            // TODO Auto-generated method stub
            if (comparatorCode==COMPARATOR_CODE){
                if(Integer.parseInt(lhs.getCode()) == Integer.parseInt(rhs.getCode())){
                    return 0;
                }
                else if(Integer.parseInt(lhs.getCode()) > Integer.parseInt(rhs.getCode())){
                    return 1;
                }
                else{
                    return -1;
                }
            }else  {
                if(lhs.getMoney() == rhs.getMoney()){
                    return 0;
                }
                else if(lhs.getMoney() < rhs.getMoney()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
    }
}
