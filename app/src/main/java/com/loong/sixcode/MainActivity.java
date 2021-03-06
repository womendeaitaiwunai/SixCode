package com.loong.sixcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.loong.sixcode.activity.PowerManageActivity;
import com.loong.sixcode.base.BaseActivity;
import com.loong.sixcode.base.BaseApplication;
import com.loong.sixcode.bean.AnimalBean;
import com.loong.sixcode.bean.LineBean;
import com.loong.sixcode.bean.WaveBean;
import com.loong.sixcode.fragment.AllCodeFragment;
import com.loong.sixcode.fragment.BuyCodeFragment;
import com.loong.sixcode.fragment.LookCodeFragment;
import com.loong.sixcode.fragment.SearchCodeFragment;
import com.loong.sixcode.service.ClipBoardService;
import com.loong.sixcode.service.FloatingWindowService;
import com.loong.sixcode.util.parser.AnimalParser;
import com.loong.sixcode.util.parser.LineParser;
import com.loong.sixcode.util.parser.WaveParser;
import com.loong.sixcode.view.NoScrollViewPager;
import com.loong.sixcode.view.Sneaker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.long1.spacetablayout.SpaceTabLayout;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private SpaceTabLayout tabLayout;
    private ClipBoardReceiver mBoardReceiver;
    private AllCodeFragment allCodeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final NoScrollViewPager viewPager= (NoScrollViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        allCodeFragment=new AllCodeFragment();
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new LookCodeFragment());
        fragmentList.add(new BuyCodeFragment());
        fragmentList.add(allCodeFragment);
        fragmentList.add(new SearchCodeFragment());
        viewPager.setAdapter(new MFragmentAdapter(getSupportFragmentManager(),fragmentList));
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);

        //we need the savedInstanceState to get the position
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    allCodeFragment.addData();
                    viewPager.setScroll(false);
                }else {
                    viewPager.setScroll(true);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        Intent mIntent = new Intent();
        mIntent.setClass(MainActivity.this, ClipBoardService.class);
        startService(mIntent);

        mBoardReceiver = new ClipBoardReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.loong.sixcode.service.ClipBoardReceiver");
        registerReceiver(mBoardReceiver, filter);

//        performCodeWithPermission("设置相应的权限", new PermissionCallback() {
//            @Override
//            public void hasPermission() {}
//            @Override
//            public void noPermission() {
//                showToast("没有相应的权限");
//                finish();
//            }
//        }, android.Manifest.permission.SYSTEM_ALERT_WINDOW,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (BaseApplication.getDaoInstant().getAnimalBeanDao().count()
                +BaseApplication.getDaoInstant().getWaveBeanDao().count()
                +BaseApplication.getDaoInstant().getLineBeanDao().count()==0){
            showProgressDialog("正在初始化数据...");
            new CheckData().execute();
        }

    }


    public void addCodeData(){
        allCodeFragment.addData();
    }

    public class CheckData extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void[] params) {
            if (BaseApplication.getDaoInstant().getAnimalBeanDao().count()==0){
                try {
                    InputStream is = getAssets().open("animal.xml");
                    List<AnimalBean> animalBeanList= AnimalParser.parse(is);
                    for (AnimalBean animalBean:animalBeanList)
                        BaseApplication.getDaoInstant().getAnimalBeanDao().insert(animalBean);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (BaseApplication.getDaoInstant().getWaveBeanDao().count()==0){
                try {
                    InputStream is = getAssets().open("wave.xml");
                    List<WaveBean> animalBeanList= WaveParser.parse(is);
                    for (WaveBean animalBean:animalBeanList)
                        BaseApplication.getDaoInstant().getWaveBeanDao().insert(animalBean);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (BaseApplication.getDaoInstant().getLineBeanDao().count()==0){
                try {
                    InputStream is = getAssets().open("five_line.xml");
                    List<LineBean> animalBeanList= LineParser.parse(is);
                    for (LineBean animalBean:animalBeanList)
                        BaseApplication.getDaoInstant().getLineBeanDao().insert(animalBean);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            hideProgressDialog();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private class MFragmentAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragmentList=new ArrayList<>();
        MFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList=fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivityByIntent(this, PowerManageActivity.class,false);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBoardReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Sneaker.with(this)
                        .setTitle("Success!!")
                        .setDuration(5000)
                        .setMessage("This is the success message")
                        .sneakSuccess();
                break;
            case R.id.button2:
                Sneaker.with(this)
                        .setTag("哈哈")
                        .setOnSneakerClickListener(new Sneaker.OnSneakerClickListener() {
                            @Override
                            public void onSneakerClick(View view) {
                                if (view.getTag()!=null){
                                    String result=(String) view.getTag();
                                    Toast.makeText(MainActivity.this, "点击了view"+result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setTitle("Error!!")
                        .setMessage("This is the error message")
                        .sneakError();
                break;
            case R.id.button3:
                Sneaker.with(this)
                        .autoHide(false)
                        .setTitle("Warning!!")
                        .setMessage("This is the warning message")
                        .sneakWarning();
                break;
        }
    }

    class ClipBoardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                String value = (String) bundle.get("clipboardvalue");
                Intent show = new Intent(MainActivity.this, FloatingWindowService.class);
                show.putExtra(FloatingWindowService.OPERATION,FloatingWindowService.OPERATION_SHOW);
                show.putExtra("copyValue", value);
                MainActivity.this.startService(show);
            }
        }
    }
}
