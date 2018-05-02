package com.ieds.gis.base.widget.bo;

import java.io.Serializable;
import java.util.List;

import android.preference.EditTextPreference;

public class WidgetEditBo extends AbsWidgetBo implements Serializable {

	// 要显示到录入中的值
	private String sqlValue;
	// 和显示值关联，需要保存的数据，在自由选取模式中用到
	private Serializable freeData;

	// 录入值的最大数量
	private int maxNum;
	// 编辑风格
	private int editStyle;
	// 是否单行
	private int is_oneline;
	
	public int getIs_oneline() {
		return is_oneline;
	}

	public void setIs_oneline(int is_oneline) {
		this.is_oneline = is_oneline;
	}

	public Serializable getFreeData() {
		return freeData;
	}

	public void setFreeData(Serializable freeData) {
		this.freeData = freeData;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getSqlValue() {
		return sqlValue;
	}

	@Override
	public void setSqlValue(String sqlValue) {
		// TODO Auto-generated method stub
		this.sqlValue = sqlValue;
	}

	public int getEditStyle() {
		return editStyle;
	}

	public void setEditStyle(int editStyle) {
		this.editStyle = editStyle;
	}

	public WidgetEditBo(String id, boolean enableNull, String title,
			String sqlField, boolean isEnable, int maxNum, int editStyle, int is_oneline) {
		super(id, enableNull, title, sqlField, isEnable);
		this.maxNum = maxNum;
		this.editStyle = editStyle;
		this.is_oneline = is_oneline;
	}

}
