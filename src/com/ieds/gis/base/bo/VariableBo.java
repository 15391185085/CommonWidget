package com.ieds.gis.base.bo;
/**
 * 要显示的对象T中的变量
 * 
 * @author lihx
 * 
 */
public class VariableBo {
	// 用来显示控件的标签
	private String variableLabel;
	// 用来查询多选控件显示的域名
	private String variableName;
	public String getVariableLabel() {
		return variableLabel;
	}
	public void setVariableLabel(String variableLabel) {
		this.variableLabel = variableLabel;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public VariableBo(String variableLabel, String variableName) {
		super();
		this.variableLabel = variableLabel;
		this.variableName = variableName;
	}

}
