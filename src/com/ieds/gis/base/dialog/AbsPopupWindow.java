package com.ieds.gis.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.ieds.gis.base.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.Tools;

/**
 * PopupWindow管理类
 */
public class AbsPopupWindow extends PopupWindow {
	private View mMenuView;
	private LayoutInflater inflater;

	public View getView() {
		return mMenuView;
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	public AbsPopupWindow(Activity act, int layout, int width, int height) {
		super(act);
		inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(layout, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(width);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(height);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// 影藏软键盘
		Tools.hideSoftInput((Activity) act);
	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		ViewUtils.inject(this, view);

	}
}
