package com.ieds.gis.base.widget.bo;

import java.io.Serializable;
import java.util.List;

public class WidgetTimeBo extends AbsWidgetBo implements Serializable {

	// 要显示的初始时间值
	private String sqlValue;

	public String getSqlValue() {
		return sqlValue;
	}

	@Override
	public void setSqlValue(String sqlValue) {
		// TODO Auto-generated method stub
		this.sqlValue = sqlValue;
	}

	public WidgetTimeBo(String id, boolean enableNull, String title,
			String sqlField, boolean isEnable) {
		super(id, enableNull, title, sqlField, isEnable);
	}

}
