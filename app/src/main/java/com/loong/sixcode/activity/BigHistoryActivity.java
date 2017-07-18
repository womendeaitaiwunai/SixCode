package com.loong.sixcode.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.CodeBean;
import com.loong.sixcode.fragment.AllCodeFragment;
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
 * Created by lxl on 2017/7/11.
 */

public class BigHistoryActivity extends BaseActivity{
    private ColumnChartView columnChartView;
    private List<Integer> mineResult=new ArrayList<>();
    private List<CodeBean> codeBeanList=new ArrayList<>();
    private Spinner dataType;
    private  List<CodeBean> mCodeBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_big_history);
        columnChartView= (ColumnChartView) findViewById(R.id.column_chart);
        dataType= (Spinner) findViewById(R.id.data_type);
        List<String> dataTypeData=new ArrayList<>();
        dataTypeData.add("按号码排序");
        dataTypeData.add("按金额排序");

        ArrayAdapter dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataTypeData);
        //设置下拉列表的风格
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        dataType.setAdapter(dataAdapter);
        //添加事件Spinner事件监听
        dataType.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        dataType.setVisibility(View.VISIBLE);
        changeType();
        initChartView();
        findViewById(R.id.out_scn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void initListData() {
        mineResult.clear();
        for (int i = 0; i < 50; i++) {
            mineResult.add(0);
        }
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            findViewById(R.id.money_and_code).setVisibility(View.GONE);
                if (arg2==0){
                    Collections.sort(codeBeanList,new CodeComparator(CodeComparator.COMPARATOR_CODE));
                }else {
                    Collections.sort(codeBeanList,new CodeComparator(CodeComparator.COMPARATOR_MONEY));
                }
            initChartView();
        }
        public void onNothingSelected(AdapterView<?> arg0) {}
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
        final TextView money= (TextView) findViewById(R.id.money);
        final TextView code= (TextView) findViewById(R.id.code);
        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {
                findViewById(R.id.money_and_code).setVisibility(View.VISIBLE);
                money.setText(mCodeBean.get(i).getMoney()+"块");
                code.setText(mCodeBean.get(i).getCode()+"号");
                //Toast.makeText(BigHistoryActivity.this, codeBeanList.get(i).getCode()+"--->"+codeBeanList.get(i).getMoney(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onValueDeselected() {
                findViewById(R.id.money_and_code).setVisibility(View.GONE);
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
