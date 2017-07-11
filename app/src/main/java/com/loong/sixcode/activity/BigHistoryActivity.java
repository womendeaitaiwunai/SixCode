package com.loong.sixcode.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.fragment.AllCodeFragment;

/**
 * Created by lxl on 2017/7/11.
 */

public class BigHistoryActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AllCodeFragment allCodeFragment=new AllCodeFragment();
        setContentView(R.layout.activity_big_history);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.main_view,allCodeFragment);
        ft.commit();

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCodeFragment.addData();
            }
        });
    }
}
