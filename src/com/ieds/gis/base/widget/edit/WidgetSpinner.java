package com.ieds.gis.base.widget.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.util.SettingUtil;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.FieldValue;
import com.ieds.gis.base.widget.bo.WidgetSpinnerBo;
import com.ieds.gis.base.widget.checklist.ItemClickCallback;
import com.ieds.gis.base.widget.checklist.SpinnerEditButton;

public class WidgetSpinner extends AbsWidget {
	private TextView tv;
	private SpinnerEditButton button;
	private WidgetSpinnerBo bo;
	private View view;
	private Drawable bg;

	public WidgetSpinner(Context context, final WidgetSpinnerBo bo) {
		view = WidgetFather.get(context, EditData.TYPE_SPINNER);
		tv = (TextView) view.findViewById(R.id.text1);
		button = (SpinnerEditButton) view.findViewById(R.id.spinner1);
		this.bo = bo;
		initView();
	}

	public void initSytleText() {
		if (bo.getCurrentChoose() == AbsWidgetBo.NORMAL_CHOOSE && bo.isEnable()) {
			bo.setCurrentChoose(SettingUtil.getInstance().getInt(bo.getId(),
					AbsWidgetBo.NORMAL_CHOOSE));
			initButtonText();
		}
	}

	public void setNameEMS(int ems) {
		tv.setEms(ems);
	}

	public View getView() {
		return view;
	}

	@Override
	public void setSave() {
		SettingUtil.getInstance().putInt(bo.getId(), bo.getCurrentChoose());
	}

	private void initView() {
		bg = button.getBackground();
		List<String> stringList = new ArrayList<String>();
		for (Iterator iterator = bo.getListValue().iterator(); iterator
				.hasNext();) {
			FieldValue fv = (FieldValue) iterator.next();
			stringList.add(fv.getValue());
		}

		button.initView(stringList, bo.getTitle(), new ItemClickCallback() {

			@Override
			public void callback(String str, int position) {
				// TODO Auto-generated method stub
				bo.setCurrentChoose(position);
				button.setTitle(button.getText().toString());
			}
		});
		initButtonText();

		button.setEnabled(bo.isEnable());

		if (bo.isEnableNull()) {
			tv.setText(bo.getTitle());
		} else {
			tv.setText("*" + bo.getTitle());
		}

		button.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (button.getText().toString().equals("")) {
					setNormal();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void initButtonText() {
		if (bo.getCurrentChoose() < bo.getListValue().size()
				&& bo.getCurrentChoose() >= 0) {
			button.setText(bo.getListValue().get(bo.getCurrentChoose())
					.getValue());
			button.setTitle(button.getText().toString());
		}
	}

	@Override
	public AbsWidgetBo getAbsWidgetBo() {
		// TODO Auto-generated method stub
		return bo;
	}

	public void setNormal() {
		button.setBackgroundDrawable(bg);
	}

	public void setError() {
		button.setBackgroundResource(ERROR_DRAWABLE);
	}

}
