package com.ieds.gis.base.widget.bo;

import java.io.Serializable;

import com.ieds.gis.base.widget.edit.AbsWidget;

public abstract class AbsWidgetBo implements Serializable {
	// 唯一id
	private String id;
	// 录入值是否可以为空 true:可为空，同时不显示*标签
	private boolean enableNull;
	// 标题
	private String title;
	// 录入值对应数据库的字段key
	private String sqlField;
	// 选择按钮是否可用
	private boolean isEnable;
	public static final int NORMAL_CHOOSE = -1;

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSqlField() {
		return sqlField;
	}

	public void setSqlField(String sqlField) {
		this.sqlField = sqlField;
	}

	public boolean isEnableNull() {
		return enableNull;
	}

	public void setEnableNull(boolean enableNull) {
		this.enableNull = enableNull;
	}

	/**
	 * 获取要存入数据库中的值
	 * 
	 * @return
	 */
	public abstract String getSqlValue();

	/**
	 * 把数据库中的值赋值给显示控件
	 * 
	 * @param sqlValue
	 */
	public abstract void setSqlValue(String sqlValue);

	public AbsWidgetBo(String id, boolean enableNull, String title,
			String sqlField, boolean isEnable) {
		super();
		this.id = id;
		this.enableNull = enableNull;
		this.title = title;
		this.sqlField = sqlField;
		this.isEnable = isEnable;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
