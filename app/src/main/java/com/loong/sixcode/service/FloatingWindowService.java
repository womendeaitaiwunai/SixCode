package com.loong.sixcode.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
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
import com.loong.sixcode.R;
import com.loong.sixcode.bean.BuyResultBean;
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
		params.x = 0;
		params.y = 0;
		
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
		TextView titleText = (TextView) rootview.findViewById(R.id.float_window_title);
		byte[] result=decrypt(copyValue);
		Gson gson=new Gson();
		List<BuyResultBean> buyResultBeanList=new ArrayList<>();
		Type type = new TypeToken<List<BuyResultBean>>() {}.getType();
		if (result!=null) buyResultBeanList= gson.fromJson(new String(result), type);
		String resultString="";
		for (int i = 0; i <buyResultBeanList.size(); i++) {
			BuyResultBean buyResultBean=buyResultBeanList.get(i);
			for (int j = 0; j <buyResultBean.getBuyNum().size() ; j++) {
				resultString=resultString+buyResultBean.getBuyNum().get(j)+"、";
			}
			if (resultString.endsWith("、")) resultString=resultString.substring(0,resultString.length()-1);
			if (buyResultBean.getBuyNum().size()>1) resultString
					=resultString+"各"+(buyResultBean.getMoney()/buyResultBean.getBuyNum().size())+"块"+"\n";
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
		floatView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}


	private byte[] decrypt(String rsaString){
		String privateString="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDzr2QHpjbHC2iQgWRuV6EbiM6J" +
				"V8jfbkO9uwNt1hNrhAibAlU8lD2xsyynAljsSZllV1MCaupc74+ZLZk80dD2ASdfagNCnoYwyysQ" +
				"iIFN2DqaNsmCfrf8BtsHW3rjQQ6NHXrRKIP6KpADm0pNm+yFBirajDEUfwlKAyEM4jlaqBbiAK5V" +
				"miT4N+R9vJLWGuakx8p7pzWlUbw9aE6/sAd08Flo5Kk6pJ8EZTSJRdQI70oWD5KnvYWCoIlc0XWn" +
				"izTIYT0HkSJ33I1cHbs2SZU7BfGHM9c19eNUeva1erOFl2iSc0Ug7EAZ5i+GeKGt+lGq+JtnzlTr" +
				"sPOQ7YRUd+EBAgMBAAECggEACixYULlvr/+Z7e548uJVp4Cenr07dZh024bjLcKyLNrmQRJn3I+T" +
				"w8dnJdHDRJoN/V0X6hHsltSpegudShy4TIzfx+v1FuLnV0IkGhyYyVctmyKOZxRDOhJPMo9vIe2l" +
				"Xd7NMc4vNFJykLP69iJ8TVFkmJshsWlcSeq3hoIrQs2My5QvnQ2/XPFg9jrAJPeUL03ge2JEGSiM" +
				"iMmHhitogKn5SrgDoEvv7alzo9r3xnKipTdTi3RZ9vyV0dU/yly+yKzRXmDVVarGs4MDSRa8iJoR" +
				"s0ecVwEekyr7kK850BomFuTxl6gK5lWvNqcAPrPsBcMHqCMcpvRScdIVs/NQAQKBgQD9wFOGu2ST" +
				"hcXml7dBORiJHQt1ZdggICPeGGCTReCNMMa/gHKkxlL8tsr5UuUER6gjR/AVQ6bPSWM4xpZQWHiw" +
				"FguZoBUY8Fo/4ZvRZf9bbMXwCUdNuN0IPJQrxBE1CSCM9OApx7CIx+QYOU5r/7bt0i3C9uNkfEea" +
				"Mz1Iq7HQkQKBgQD12DpUZg76vTrLyHr9zbCqu/DAAhb9/sGLrjGfDz5irjkjMy1Oh9JcihrbpsoE" +
				"BStad9tNOW6IQBytjbXBCofue1qv1JCmHTTLZKO1x93pBoVniobFgFQ3gsv2w314j447VK7B15VN" +
				"LgYQKKdRl2rOuvCDj1lGUuA8WMX8N6ZBcQKBgHAQUxMKiVV5W7gFwsHERGixchELxFITv6NjIquH" +
				"za6tHukOAhNxHs9KrwNDL68fMks6hvXtJasf3vkBAvtCxzIE2mSjRQ35f/H8YgMtR4QJ2rSydFIH" +
				"1V3A2zMk6PCCMmTt9F2fOgB+PubP0Cl0CkanbtxOiogjNsAL5wHECchxAoGAJMMYtdYtF0GEsURh" +
				"X+KKMH26Gnw1g/W8a2647NgStB7pXGIivEODKWNdcMsYGlHLkdOqb3nPFYDAyRMPwrYYbYj0nqZi" +
				"mvRzGJcMA/rHLDlVaY5FaPMvpL1iUpwY/xY4ZpbbKxFZuhjPv0rU+lZyGkfJ/2JIL0PlVCDSOkz4" +
				"B4ECgYBn8tOGTWP6En7q4b7LDYnAuvtso9d32/cbkhQm7dyPR9OmmptVeBsBK6gCk+4S7itsJ/N0" +
				"2uN1WKlXdigSZ+wyYbLkCrX8C0qTSqj4v1Uls98Eqr+zHynfdCOLLnvNzaV9eoS7ENIDpdF4ErIy" +
				"Um+8vv7V4p8/yah5Qrl+zDyIZw==";
		byte[] result;
		try {
			result=RSAUtils.decryptData(Base64Utils.decode(rsaString),RSAUtils.loadPrivateKey(privateString));
			return result;
		}catch (Exception e){
			return null;
		}

	}

}
