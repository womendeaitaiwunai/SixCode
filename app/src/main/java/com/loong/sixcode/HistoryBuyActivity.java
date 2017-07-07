package com.loong.sixcode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.loong.sixcode.adapter.BuyResultAdapter;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyResultBean;
import com.loong.sixcode.greendao.BuyCodeDbDao;
import com.loong.sixcode.util.QRCodeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lxl on 2017/7/5.
 */

public class HistoryBuyActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private BuyResultAdapter adapter;
    private List<BuyResultBean> buyResultBeenList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_buy);
        initView();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.history_buy_recycle);
        buyResultBeenList=new ArrayList<>();
        adapter=new BuyResultAdapter(buyResultBeenList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        new GetHistory().execute();

//        findViewById(R.id.haha).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CreateQRCode().execute();
//            }
//        });
    }

    public class CreateQRCode extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected void onPreExecute() {
           showProgressDialog("正在准备生成订单图片");
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result=QRCodeUtil.createQRImage("FOLvZxhD4arVNXMwDGDtfgn1QqjX6iZWXGih0XFzVWXw6/7S+NalfX0DnhEfpmUPdk/Qt70WatLV" +
                    "qGgXwYlRaTc0Y09+W0PjgJgzkTh8peWw3/81PvrV9+1+5v0p9TXTE6SappUK2U38eUZOhkOT2dy0" +
                    "eZlcqc0YNyxLIefCjvRBDoQoqmzwEwU7JagqIb85XqfF2B2zaCDmYqMMWd/GoB2KBZVbqLox691t" +
                    "naFplRs0leggKucvI83FQeLe5ngC4PSpiyguC2OYLWe79QNYGG0bnj5IJe6AfavkTuSSaDvgNrN/" +
                    "pYPq19GA04UB/qVbx+sRCAcQS3nue+b7igtskQ==",800,800,null,
                    Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"sixCode.jpg");
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            hideProgressDialog();
            if (aBoolean) Toast.makeText(HistoryBuyActivity.this, "生成订单图片成功", Toast.LENGTH_SHORT).show();
            else Toast.makeText(HistoryBuyActivity.this, "生成订单图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public class GetHistory extends AsyncTask<Void, Void, List<BuyCodeDao>> {
        @Override
        protected void onPreExecute() {
            showProgressDialog("正在加载历史数据...");
        }

        @Override
        protected List<BuyCodeDao> doInBackground(Void... params) {
            return BuyCodeDbDao.queryAll();
        }

        @Override
        protected void onPostExecute(List<BuyCodeDao> buyCodeDaos) {
            for (BuyCodeDao buyCodeDao:buyCodeDaos){
                BuyResultBean buyResultBean=new BuyResultBean();
                List<String> list=new ArrayList<>();
                list.addAll(Arrays.asList(buyCodeDao.getBuyCode().split("、")));
                buyResultBean.setBuyNum(list);
                buyResultBean.setTime(buyCodeDao.getBuyTime());
                buyResultBean.setMoney(buyCodeDao.getMoney());
                buyResultBeenList.add(buyResultBean);
            }
            adapter.cleanView();
            adapter.addSomeItemView(buyResultBeenList);
            hideProgressDialog();
        }

    }
}
