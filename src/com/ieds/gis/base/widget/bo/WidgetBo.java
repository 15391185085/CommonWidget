package com.ieds.gis.base.widget.bo;

public class WidgetBo {
	
	// 选取类型
	private int type;
	private AbsWidgetBo bo;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AbsWidgetBo getBo() {
		return bo;
	}

	public void setBo(AbsWidgetBo bo) {
		this.bo = bo;
	}

}
