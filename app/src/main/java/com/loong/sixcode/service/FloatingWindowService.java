package com.loong.sixcode.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loong.rsac.SixCodeUtil;
import com.loong.sixcode.R;
import com.loong.sixcode.bean.BuyCodeDao;
import com.loong.sixcode.bean.BuyResultBean;
import com.loong.sixcode.greendao.BuyCodeDbDao;
import com.loong.sixcode.util.Base64Utils;
import com.loong.sixcode.util.RSAUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FloatingWindowService extends Service {

	public static final String OPERATION = "operation";
	public static final int OPERATION_SHOW = 100;
	public static final int OPERATION_HIDE = 101;

	private boolean isAdded = false; // 是否已增加悬浮窗
	
	private static WindowManager wm;
	
	private static WindowManager.LayoutParams params;
	
	private View floatView;

	private float startX = 0;
	
	private float startY = 0;
	
	private float x;
	
	private float y;
	
	private String copyValue;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		createFloatView();		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (intent != null) {
			int operation = intent.getIntExtra(OPERATION, OPERATION_SHOW);
			switch (operation) {
			case OPERATION_SHOW:
				if (!isAdded) {
					wm.addView(floatView, params);
					isAdded = true;
				}
				break;
			case OPERATION_HIDE:
				if (isAdded) {
					wm.removeView(floatView);
					isAdded = false;
				}
				break;
			}
			copyValue = intent.getStringExtra("copyValue");
			setupCellView(floatView);
			Log.e(this.getClass().getSimpleName(), "=====copyValue :"+copyValue);
		}
	}
	
	/**
	 * 创建悬浮窗
	 */
	private void createFloatView() {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		floatView = layoutInflater.inflate(R.layout.dict_popup_window, null);
		
		wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();

		// 设置window type
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		
		/*
		 * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
		 * 即拉下通知栏不可见
		 */
		params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

		// 设置Window flag
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		// 设置悬浮窗的长得宽
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;

		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = 10;
		params.y = 10;
		
		// 设置悬浮窗的Touch监听
//		floatView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				x = event.getRawX();
//				y = event.getRawY();
//
//				switch(event.getAction()){
//				case MotionEvent.ACTION_DOWN:
//					startX = event.getX();
//					startY = event.getY();
//					break;
//				case MotionEvent.ACTION_MOVE:
//					params.x = (int)( x - startX);
//					params.y = (int) (y - startY);
//					wm.updateViewLayout(floatView, params);
//					break;
//				case MotionEvent.ACTION_UP:
//					startX = startY = 0;
//					break;
//				}
//				return true;
//			}
//		});
		
		wm.addView(floatView, params);
		isAdded = true;
	}

	/**
	 * 设置浮窗view内部子控件
	 * @param rootview
	 */
	private void setupCellView(View rootview) {
		ImageView closedImg = (ImageView) rootview.findViewById(R.id.float_window_closed);
		ImageView adddImg = (ImageView) rootview.findViewById(R.id.float_window_add);
		TextView titleText = (TextView) rootview.findViewById(R.id.float_window_title);
		byte[] result=decrypt(copyValue);
		Gson gson=new Gson();
		List<BuyCodeDao> buyResultBeanList=new ArrayList<>();
		Type type = new TypeToken<List<BuyCodeDao>>() {}.getType();
		if (result!=null) buyResultBeanList= gson.fromJson(new String(result), type);
		String resultString="";
		for (int i = 0; i <buyResultBeanList.size(); i++) {
			BuyCodeDao buyResultBean=buyResultBeanList.get(i);
			resultString=resultString+buyResultBean.getBuyCode();
			if (resultString.endsWith("、")) resultString=resultString.substring(0,resultString.length()-1);
			if (resultString.split("、").length>1) resultString
					=resultString+"各"+(buyResultBean.getMoney()/resultString.split("、").length)+"块"+"\n";
			else resultString=resultString+"/"+buyResultBean.getMoney()+"块"+"\n";
		}
		if (resultString.endsWith("\n")) resultString=resultString.substring(0,resultString.length()-1);
		if (result!=null) titleText.setText("特码:"+resultString);
		else wm.removeView(floatView);
		closedImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isAdded) {
					wm.removeView(floatView);
					isAdded = false;
				}
			}
		});
		final List<BuyCodeDao> finalBuyResultBeanList = new ArrayList<>();
		finalBuyResultBeanList.addAll(buyResultBeanList);

		adddImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isAdded) {
					for (BuyCodeDao buyCodeDao:finalBuyResultBeanList){
						BuyCodeDbDao.insertBuyCode(buyCodeDao);
					}
					wm.removeView(floatView);
					isAdded = false;
				}
			}
		});
		floatView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}


	private byte[] decrypt(String rsaString){
		String privateString= new SixCodeUtil().getJMS(this);
		byte[] result;
		try {
			result=RSAUtils.decryptData(Base64Utils.decode(rsaString),RSAUtils.loadPrivateKey(privateString));
			return result;
		}catch (Exception e){
			return null;
		}

	}
}
