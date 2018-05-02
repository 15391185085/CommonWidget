package com.ieds.gis.base.widget.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ieds.gis.base.R;
import com.ieds.gis.base.edit.date.EditData;

public class WidgetFather {

	/**
	 * @param args
	 */
	public static View get(Context mContext, int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EditData.TYPE_EDIT:
			return LayoutInflater.from(mContext).inflate(
					R.layout.a_layout_edittext, null);
		case EditData.TYPE_RADIO:
			return LayoutInflater.from(mContext).inflate(
					R.layout.a_layout_radio, null);
		case EditData.TYPE_SPINNER:
			return LayoutInflater.from(mContext).inflate(
					R.layout.a_layout_spinner, null);
		case EditData.TYPE_TIME:
			return LayoutInflater.from(mContext).inflate(
					R.layout.a_layout_time, null);

		default:
			return null;
		}

	}
}
