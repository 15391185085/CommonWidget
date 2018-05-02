package com.ieds.gis.base.widget.edit;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.util.SettingUtil;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.WidgetRadioBo;

/**
 * 最多只支持3个选项
 * 
 * @update 2015-4-20 下午3:51:40<br>
 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
 * 
 */
public class WidgetRadio extends AbsWidget {

	private TextView tv;
	private RadioButton rb0;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioGroup radioGroup;
	private WidgetRadioBo bo;
	private View view;

	public WidgetRadio(Context context, final WidgetRadioBo bo) {
		view = WidgetFather.get(context, EditData.TYPE_RADIO);
		tv = (TextView) view.findViewById(R.id.text1);
		rb0 = (RadioButton) view.findViewById(R.id.radio0);
		rb1 = (RadioButton) view.findViewById(R.id.radio1);
		rb2 = (RadioButton) view.findViewById(R.id.radio2);
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		this.bo = bo;
		initView();
	}

	public void setNameEMS(int ems) {
		tv.setEms(ems);
	}

	public View getView() {
		return view;
	}

	public void initSytleText() {
		if (bo.getCurrentChoose() == AbsWidgetBo.NORMAL_CHOOSE && bo.isEnable()) {
			bo.setCurrentChoose(SettingUtil.getInstance().getInt(bo.getId(),
					AbsWidgetBo.NORMAL_CHOOSE));
			initButtonText();
		}
	}

	@Override
	public void setSave() {
		SettingUtil.getInstance().putInt(bo.getId(), bo.getCurrentChoose());
	}

	private void initView() {
		rb0.setEnabled(bo.isEnable());
		rb1.setEnabled(bo.isEnable());
		rb2.setEnabled(bo.isEnable());

		int size = bo.getListValue().size();
		switch (size) {
		case 3:
			rb2.setVisibility(View.VISIBLE);
			rb2.setText(bo.getListValue().get(2).getValue());
		case 2:
			rb1.setVisibility(View.VISIBLE);
			rb1.setText(bo.getListValue().get(1).getValue());
		case 1:
			rb0.setVisibility(View.VISIBLE);
			rb0.setText(bo.getListValue().get(0).getValue());
		default:
			break;
		}
		initButtonText();
		for (int i = 0; i < bo.getListValue().size(); i++) {
			String array_element = bo.getListValue().get(i).getValue();
			switch (i) {
			case 0:
				rb0.setText(bo.getListValue().get(i).getValue());
				break;
			case 1:
				rb1.setText(bo.getListValue().get(i).getValue());
				break;
			case 2:
				rb2.setText(bo.getListValue().get(i).getValue());
				break;
			default:
				break;
			}
		}
		if (bo.isEnableNull()) {
			tv.setText(bo.getTitle());
		} else {
			tv.setText("*" + bo.getTitle());
		}

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (rb0.getId() == checkedId) {
					bo.setCurrentChoose(0);
				} else if (rb1.getId() == checkedId) {
					bo.setCurrentChoose(1);
				} else if (rb2.getId() == checkedId) {
					bo.setCurrentChoose(2);
				}
			}
		});
	}

	private void initButtonText() {
		if (bo.getCurrentChoose() < bo.getListValue().size()
				&& bo.getCurrentChoose() >= 0) {
			switch (bo.getCurrentChoose()) {
			case 0:
				rb0.setChecked(true);
				break;
			case 1:
				rb1.setChecked(true);
				break;
			case 2:
				rb2.setChecked(true);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public AbsWidgetBo getAbsWidgetBo() {
		// TODO Auto-generated method stub
		return bo;
	}

	public void setNormal() {
	}

	public void setError() {
	}
}
