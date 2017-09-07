package com.loong.sixcode.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.loong.sixcode.R;
import com.loong.sixcode.base.BaseActivity;

/**
 * Created by lxl on 2017/7/11.
 */

public class PowerManageActivity extends BaseActivity {
    private TextView machineId,powerCode,powerState;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_manager);

        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        machineId= (TextView) findViewById(R.id.machine_id);
        powerCode= (TextView) findViewById(R.id.power_code);
        powerState= (TextView) findViewById(R.id.power_state);

        machineId.setText(szImei);
    }
}
