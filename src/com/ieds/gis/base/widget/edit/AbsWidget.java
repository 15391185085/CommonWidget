package com.ieds.gis.base.widget.edit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.Resources.NotFoundException;
import android.text.TextUtils;
import android.view.View;

import com.ieds.gis.base.R;
import com.ieds.gis.base.dialog.MyToast;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.WidgetEditBo;

public abstract class AbsWidget {

	
	private static final String ERROR_WORD = "错误字符！";
	public static final int ERROR_DRAWABLE = R.drawable.textfield_error;

	public abstract AbsWidgetBo getAbsWidgetBo();

	public abstract View getView();

	/**
	 * 检查数值是否为空
	 * 
	 * @return true:是空
	 */
	public boolean isNull() {
		AbsWidgetBo bo = getAbsWidgetBo();
		if (bo.isEnableNull()) {// 录入值可以为空
			return false;
		} else {// 录入值不可以为空
			if (isErrorValue(bo.getSqlValue())) {
				return true;
			}
			if (bo instanceof WidgetEditBo) {
				if (((WidgetEditBo) bo).getEditStyle() == EditData.SYTLE_DECIMAL
						&& WidgetEdit.isErrorDotValue(bo.getSqlValue(),
								((WidgetEditBo) bo).getMaxNum(), true)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 录入数据是否正确
	 * 
	 * @param bo
	 * @throws NotFoundException
	 */
	public static boolean isErrorValue(String getSqlValue) {
		if (getSqlValue == null || getSqlValue.trim().equals("")) {
			return true;
		}
		if (getSqlValue.lastIndexOf("'") != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 输入的小数是否正确 true:小数位异常
	 * 
	 * @update 2014-9-25 下午2:45:28<br>
	 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
	 * 
	 * @param s
	 */
	public static boolean isErrorDotValue(String s, int max, boolean isShow) {
		int index = s.indexOf(".");
		if (index != -1) {
			// 有小数
			if (index < (s.length() - 3)) {
				if (isShow) {
					MyToast.showToast("输入不能大于两位小数！");
				}
				return true;
			} else if (index > (max + 1) - 3) {
				if (isShow) {
					MyToast.showToast("输入的整数位已到最大值！");
				}
				return true;
			}
		} else if (s.length() > (max + 1) - 3) {
			if (isShow) {
				MyToast.showToast("输入的整数位已到最大值！");
			}
			return true;
		}
		return false;
	}

	// 设置正常背景
	public abstract void setNormal();

	// 设置错误背景
	public abstract void setError();

	// 设置保存
	public abstract void setSave();

	public abstract void initSytleText();

	/**
	 * 
	 */
	public static String getPatternString(String source, String find) {
		Pattern pattern = Pattern.compile(find);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 判断是否是整数, 是整数的话加一返回
	 * 
	 * @author 李昊翔
	 * @param number
	 * @return
	 * @since JDK 1.6
	 */
	public static String addOneString(String number) {
		if (TextUtils.isEmpty(number)) {
			return number;
		}
		String s = getPatternString(number, "(\\d+)");
		if (TextUtils.isEmpty(s)) {
			return number;
		} else {
			long l = Long.valueOf(s) + 1;
			return number.replaceFirst("(\\d+)", "" + l);
		}
	}

	/**
	 * 判断是否是整数, 是整数的话减一返回
	 * 
	 * @author 李昊翔
	 * @param number
	 * @return
	 * @since JDK 1.6
	 */
	public static String subOneString(String number) {
		if (TextUtils.isEmpty(number)) {
			return number;
		}
		String s = getPatternString(number, "(\\d+)");
		if (TextUtils.isEmpty(s)) {
			return number;
		} else {
			long l = Long.valueOf(s) - 1;
			if (l < 0) {
				l = 0;
			}
			return number.replaceFirst("(\\d+)", "" + l);
		}
	}
}
