package com.ieds.gis.base.widget.spinner;

import java.util.List;

public interface OnSpinnerItemClickListener<T> {
	/**
	 * 多项时返回
	 * 
	 * @param tList
	 */
	public void onClickList(List<T> tList);

	/**
	 * 单项时返回
	 * 
	 * @param tList
	 */
	public void onClickItem(T t);

}
