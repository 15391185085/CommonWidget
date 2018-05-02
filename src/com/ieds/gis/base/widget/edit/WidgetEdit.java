package com.ieds.gis.base.widget.edit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.util.SettingUtil;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.WidgetEditBo;

public class WidgetEdit extends AbsWidget {
	// 是否可用
	private TextView tv;
	private EditText autoTextView;
	private WidgetEditBo bo;
	private View view;
	private Drawable bg;
	private Context context;
	private EditTextValue editValue;
	private ArrayAdapter adapter;
	private LinearLayout sub_add;
	private ImageButton sub;
	private ImageButton add;

	public EditText getAutoTextView() {
		return autoTextView;
	}

	public WidgetEdit(Context context, final WidgetEditBo bo) {
		view = WidgetFather.get(context, EditData.TYPE_EDIT);
		tv = (TextView) view.findViewById(R.id.text1);
		autoTextView = (EditText) view.findViewById(R.id.edit1);
		sub_add = (LinearLayout) view.findViewById(R.id.sub_add);
		sub = (ImageButton) view.findViewById(R.id.sub);
		add = (ImageButton) view.findViewById(R.id.add);
		this.bo = bo;
		this.context = context;
		initStyle(context);
		initView();
	}

	public void setNameEMS(int ems) {
		tv.setEms(ems);
	}

	public View getView() {
		return view;
	}

	private void initView() {
		bg = autoTextView.getBackground();
		autoTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence m, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String newS = s.toString();
				if (bo.getEditStyle() == EditData.SYTLE_DECIMAL
						&& WidgetEdit.isErrorDotValue(s.toString(),
								bo.getMaxNum(), false)) {
					int index = newS.indexOf(".");
					if (index == 0) {
						newS = newS.substring(1, newS.length());
					} else {
						newS = newS.substring(0, newS.length() - 1);
					}
					autoTextView.setText(newS);
					autoTextView.setSelection(autoTextView.getText().length());
				}
				bo.setSqlValue(autoTextView.getText().toString());
			}
		});
		autoTextView.setText(bo.getSqlValue());
		autoTextView.setEnabled(bo.isEnable());
		sub.setEnabled(bo.isEnable());
		add.setEnabled(bo.isEnable());
		if (bo.isEnable() && bo.getIs_oneline() == EditData.STATIC_YES) {
			autoTextView.setSingleLine();
		}
		if (!bo.isEnable()) {
			sub_add.setVisibility(View.GONE);
			autoTextView.setFocusable(false);
		}
		if (bo.getMaxNum() >= 0) {
			switch (bo.getEditStyle()) {
			case EditData.SYTLE_AUTO:
				InputFilter[] lfs1 = { new LengthFilter(bo.getMaxNum() / 2) };
				autoTextView.setFilters(lfs1);
				break;
			case EditData.SYTLE_ADD_SUB:
				InputFilter[] lfs2 = { new LengthFilter(bo.getMaxNum() / 2) };
				autoTextView.setFilters(lfs2);
				break;
			case EditData.SYTLE_NORMAL_NUMBER:
				if (bo.getMaxNum() > 7) {
					bo.setMaxNum(7);
				}
				InputFilter[] lfs5 = { new LengthFilter(bo.getMaxNum() / 1) };
				autoTextView.setFilters(lfs5);
				break;
			case EditData.SYTLE_NORMAL_ADD_SUB:
				InputFilter[] lfs3 = { new LengthFilter(bo.getMaxNum() / 1) };
				autoTextView.setFilters(lfs3);
				break;
			case EditData.SYTLE_DECIMAL:
				// +1 是因为oracle不计算小数点长度
				if (bo.getMaxNum() > 9) {
					bo.setMaxNum(9);
				}
				InputFilter[] lfs4 = { new LengthFilter(bo.getMaxNum() / 1 + 1) };
				autoTextView.setFilters(lfs4);
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
	}

	@Override
	public AbsWidgetBo getAbsWidgetBo() {
		// TODO Auto-generated method stub
		return bo;
	}

	private class EditTextValue {
		private List<String> maxL = new ArrayList<String>();

		public List<String> getMaxL() {
			return maxL;
		}

	}

	public void setNormal() {
		autoTextView.setBackgroundDrawable(bg);
	}

	@Override
	public void setSave() {
		SettingUtil.getInstance().putString(bo.getId(),
				autoTextView.getText().toString());
	}

	/**
	 * 初始化edittext类型
	 * 
	 * @param context
	 */
	public void initStyle(final Context context) {
		switch (bo.getEditStyle()) {
		case EditData.SYTLE_AUTO:
			break;
		case EditData.SYTLE_ADD_SUB:
			visSubAdd();
			break;
		case EditData.SYTLE_NORMAL_NUMBER:
			autoTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
			break;
		case EditData.SYTLE_DECIMAL:
			autoTextView.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
			break;
		case EditData.SYTLE_NORMAL_ADD_SUB:
			autoTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
			visSubAdd();
			break;

		default:
			break;
		}
	}

	private void visSubAdd() {
		sub_add.setVisibility(View.VISIBLE);
		sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				autoTextView.setText(subOneString(autoTextView.getText()
						.toString()));
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				autoTextView.setText(addOneString(autoTextView.getText()
						.toString()));

			}
		});
	}

	/**
	 * 
	 */
	public void initSytleText() {
		String su = SettingUtil.getInstance().getString(bo.getId(), "");
		if (TextUtils.isEmpty(autoTextView.getText()) && bo.isEnable()) {
			// 只有未赋值的时候可以初始化
			bo.setSqlValue(su);
			autoTextView.setText(bo.getSqlValue());
		}
	}

	public void setError() {
		autoTextView.setBackgroundResource(ERROR_DRAWABLE);
	}
}
