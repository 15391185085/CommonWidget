package com.ieds.gis.base.edit.bo;

import java.io.Serializable;
import java.util.List;

import com.ieds.gis.base.widget.bo.AbsWidgetBo;

public class EditBo implements Serializable{

	private List<AbsWidgetBo> dynamicList;
	private boolean isEditMode;
	private Serializable mainObj;

	public List<AbsWidgetBo> getDynamicList() {
		return dynamicList;
	}

	public void setDynamicList(List<AbsWidgetBo> dynamicList) {
		this.dynamicList = dynamicList;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public Serializable getMainObj() {
		return mainObj;
	}

	public void setMainObj(Serializable mainObj) {
		this.mainObj = mainObj;
	}

	public EditBo(Serializable mainObj, boolean isEditMode) {
		super();
		this.mainObj = mainObj;
		this.isEditMode = isEditMode;
	}

	public EditBo(Serializable mainObj, boolean isEditMode,
			List<AbsWidgetBo> dynamicList) {
		super();
		this.mainObj = mainObj;
		this.isEditMode = isEditMode;
		this.dynamicList = dynamicList;
	}

}
