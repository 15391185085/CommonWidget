package com.ieds.gis.base.widget.bo;

import java.io.Serializable;
import java.util.List;

import com.lidroid.xutils.exception.DbException;

public class WidgetSpinnerBo extends AbsWidgetBo implements Serializable {
	// 要显示到下拉列表中的值，可以是sql值，该值是一条可查询的sql语言
	private List<FieldValue> listValue;
	// 当前选择
	private int currentChoose = NORMAL_CHOOSE;

	public List<FieldValue> getListValue() {
		return listValue;
	}

	public void setListValue(List<FieldValue> listValue) {
		this.listValue = listValue;
	}

	@Override
	public void setSqlValue(String sqlValue) {
		if (listValue != null) {
			for (int i = 0; i < listValue.size(); i++) {
				FieldValue fv = listValue.get(i);
				if (fv.getSqlValue().equals(sqlValue)) {
					setCurrentChoose(i);
				}
			}
		}
	}

	public int getCurrentChoose() {
		return currentChoose;
	}

	public void setCurrentChoose(int currentChoose) {
		this.currentChoose = currentChoose;
	}

	public String getSqlValue() {
		if (currentChoose > listValue.size() - 1) {
			return null;
		} else {
			if (currentChoose >= 0) {
				return listValue.get(currentChoose).getSqlValue();
			} else {
				return null;
			}
		}
	}

	/**
	 * 
	 * @param enableNull
	 * @param title
	 * @param sqlField
	 * @param isChooseEnable
	 * @param listValue
	 * @param currentChoose
	 *            默认为-1，没有选择数据，小于0表示没有选择数据
	 */
	public WidgetSpinnerBo(String id, boolean enableNull, String title,
			String sqlField, boolean isEnable, List<FieldValue> listValue) {
		super(id, enableNull, title, sqlField, isEnable);
		this.listValue = listValue;
	}

}
