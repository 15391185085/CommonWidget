package com.ieds.gis.base;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lidroid.xutils.exception.DbException;

public class BaseActivity extends FragmentActivity {
	public BaseApp mApp;
	private static final String TAG = "BaseActivity";

	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	public int getmScreenWidth() {
		return mScreenWidth;
	}

	public int getmScreenHeight() {
		return mScreenHeight;
	}

	private static LinkedList<BaseActivity> queue = new LinkedList<BaseActivity>();// 打开的activity队列

	public static LinkedList<BaseActivity> getQueue() {
		return queue;
	}

	/**
	 * 安装文件失败
	 * 
	 * @throws DbException
	 */
	public void installError() throws DbException {
		throw new DbException("读写文件失败，请断开设备与USB的连接后重进系统！");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mApp = (BaseApp) getApplication(); // 获得自定义的应用程序MyApp
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
		if (!queue.contains(this)) {
			queue.add(this);
		}

	}

	@Override
	protected void onDestroy() {
		queue.remove(this);// 意外回收
		super.onDestroy();
	}

	/**
	 * 返回之前的activity
	 * @param activity
	 */
	public void backLastActivity(Class activity) {
		for (int i = 0; i < queue.size(); i++) {
			BaseActivity type = queue.get(i);
			if (!(type.getClass().getName().equals(activity.getName()))) {
				type.finish();
			}
		}
	}

	public void exit() {
		for (int i = 0; i < queue.size(); i++) {
			BaseActivity type = queue.get(i);
			type.finish();
		}
	}
}
