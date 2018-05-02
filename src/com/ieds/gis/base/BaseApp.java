package com.ieds.gis.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Bundle;
import android.os.Handler;

import com.ieds.gis.base.util.SettingUtil;
import com.lidroid.xutils.exception.MyLog;
import com.lidroid.xutils.exception.NullArgumentException;

import java.util.Calendar;

/**
 * 初始化更具输，变，配不同业务需要修正的系统常量
 * 
 * @author lihx
 * 
 */
@SuppressLint("NewApi")
public class BaseApp extends Application {
  
	private static BaseApp mContext;

	/**
	 * 图片的缓存文件，需要定期清除
	 */
	private Thread mUiThread;
	private Handler handler = new Handler();

	public Handler getHandler() {
		return handler;
	}

	public static BaseApp getmContext() {
		return mContext;
	}

	public void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			handler.post(action);
		} else {
			action.run();
		}
	}
  
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MyLog.isWrite = true;
		mContext = this;
		this.mUiThread = Thread.currentThread();
	}
 
}
