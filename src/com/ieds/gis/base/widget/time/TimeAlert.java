/**
 * Project Name:TestAijiaTime
 * File Name:SelectPicPopupWindow.java
 * Package Name:com.testaijia.time
 * Date:2014-2-7下午02:41:34
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.ieds.gis.base.widget.time;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.ieds.gis.base.R;
import com.ieds.gis.base.dialog.AbsPopupWindow;
import com.ieds.gis.base.dialog.MyToast;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.DateUtil;

public class TimeAlert extends AbsPopupWindow {

	private WheelMain wheelMain;
	public static final int DATE = 0;// flag 表示只显示日期控件
	public static final int DATE_AND_TIME = 1;// flag 表示显示日期和时间控件

	public TimeAlert(Activity context, final OnTimeClickListener callback,
			final int flag) {
		super(context, R.layout.alert_time, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		View timePicker1 = this.getContentView().findViewById(R.id.timePicker1);
		wheelMain = new WheelMain(timePicker1, flag);
		this.getContentView().findViewById(R.id.confirm)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (callback != null) {
							switch (flag) {
							case 0:// 只显示日期
								callback.onCallback(wheelMain.getDateToDay());
								break;
							case 1:// 显示日期和时间
								callback.onCallback(wheelMain.getDateToSecond());
								break;
							}
						}
						dismiss();
					}
				});
		this.getContentView().findViewById(R.id.cancel)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (TimeAlert.this.isShowing()) {
							dismiss();
						}
					}
				});
		this.getContentView().measure(0, 0);
		int height = this.getContentView().getMeasuredHeight();
		this.showAtLocation(timePicker1, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, -height); // 设置layout在PopupWindow中显示的位置

	}

	/**
	 * 初始化控件显示时间
	 * 
	 * @update 2014-7-11 上午10:22:04<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @param calendar
	 */
	public void initTimeAlert(Calendar calendar) {
		wheelMain.initTime(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE));
	}

	/**
	 * 初始化控件显示时间
	 * 
	 * @update 2014-7-11 上午10:22:04<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @param calendar
	 */
	public void initTimeAlert(String date) {
		try {
			if (date == null || date.trim().equals("")) {
				throw new NullArgumentException();
			}
			Calendar calendar = Calendar.getInstance();
			Date d = DateUtil.sqlStringToDate(date);
			if (d == null) {
				throw new DbException("格式解析出错 date = " + date);
			}
			calendar.setTime(d);
			initTimeAlert(calendar);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MyToast.showToast(e.getMessage());
		}
	}

	public interface OnTimeClickListener {
		void onCallback(String date);
	}

}
