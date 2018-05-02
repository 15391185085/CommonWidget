package com.ieds.gis.base.widget.edit;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.util.SettingUtil;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.WidgetTimeBo;
import com.ieds.gis.base.widget.time.TimeAlert;

public class WidgetTime extends AbsWidget {
	private TextView tv;
	private Button btn;
	private WidgetTimeBo bo;
	private View view;
	private Drawable bg;

	public WidgetTime(final Activity context, WidgetTimeBo bo) {
		view = WidgetFather.get(context, EditData.TYPE_TIME);
		tv = (TextView) view.findViewById(R.id.text1);
		btn = (Button) view.findViewById(R.id.spinner1);
		this.bo = bo;
		initView(context);
	}

	public void setNameEMS(int ems) {
		tv.setEms(ems);
	}

	public View getView() {
		return view;
	}

	/**
	 * 
	 */
	public void initSytleText() {
		String su = SettingUtil.getInstance().getString(bo.getId(), "");
		if (TextUtils.isEmpty(btn.getText()) && bo.isEnable()) {
			// 只有未赋值的时候可以初始化
			bo.setSqlValue(su);
			btn.setText(bo.getSqlValue());
		}
	}

	@Override
	public void setSave() {
		SettingUtil.getInstance().putString(bo.getId(),
				btn.getText().toString());
	}

	private void initView(final Activity context) {
		bg = btn.getBackground();
		btn.setText(bo.getSqlValue());
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TimeAlert tba = new TimeAlert(context, listener,
						TimeAlert.DATE_AND_TIME);
			}
		});
		btn.setEnabled(bo.isEnable());

		if (bo.isEnableNull()) {
			tv.setText(bo.getTitle());
		} else {
			tv.setText("*" + bo.getTitle());
		}
		btn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (btn.getText().toString().equals("")) {
					setNormal();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	TimeAlert.OnTimeClickListener listener = new TimeAlert.OnTimeClickListener() {

		@Override
		public void onCallback(String date) {
			// TODO Auto-generated method stub
			btn.setText(date);
			bo.setSqlValue(date);
		}
	};

	@Override
	public AbsWidgetBo getAbsWidgetBo() {
		// TODO Auto-generated method stub
		return bo;
	}

	public void setNormal() {
		btn.setBackgroundDrawable(bg);
	}

	public void setError() {
		btn.setBackgroundResource(ERROR_DRAWABLE);
	}

}
