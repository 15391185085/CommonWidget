package com.ieds.gis.base.widget.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 值的对应表
 * @update 2015-4-21 上午10:22:36<br>
 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
 *
 */
public class FieldValue implements Serializable{

	// 要显示到选择按钮中的值
	private String value;
	// 要存储到数据库中的值对应显示值
	private String sqlValue;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSqlValue() {
		return sqlValue;
	}
	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}
	public FieldValue(String value, String sqlValue) {
		super();
		this.value = value;
		this.sqlValue = sqlValue;
	}
	
}
