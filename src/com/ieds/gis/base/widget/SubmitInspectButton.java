package com.ieds.gis.base.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.listener.OnSubmitStyleListener;
import com.ieds.gis.base.widget.edit.AbsWidget;

public class SubmitInspectButton extends Button {
	public static final String CHECK_TRUE = "系统正在处理中，请稍后！";
	public static final String CHECK_ERROR = "您填写的数据不正常，请先检查数据!";
	private static final int ERROR_DRAWABLE = R.drawable.textfield_error;
	private Context c;
	private List<TextView> list = new ArrayList<TextView>();

	public void clear() {
		list.clear();
	}


	public void addView(final TextView v) {
		
		v.addTextChangedListener(new TextWatcher() {
			private Drawable bg = v.getBackground();

			@Override
			public void onTextChanged(CharSequence m, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (v.getText().toString().equals("")) {
					setNormal(v, bg);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		this.list.add(v);
	}

	public void removeView(TextView v) {
		this.list.remove(v);
	}

	/**
	 * 检查输入内容合法性
	 * 
	 * @update 2014-7-5 上午11:08:29<br>
	 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
	 * @throws Exception 
	 */
	public void checkSubmitStyle(OnSubmitStyleListener onSubmitStyleListener) throws Exception {
		boolean isSuccess = true;
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TextView v = (TextView) iterator.next();
				if (AbsWidget.isErrorValue(v.getText().toString())) {
					setError(v);
					isSuccess = false;
				}
			}
		}
		if (isSuccess) {
			onSubmitStyleListener.onSuccess(CHECK_TRUE);
		} else {
			onSubmitStyleListener.onFailure(CHECK_ERROR);
		}
	}

	private void setNormal(TextView v, Drawable bg) {
		v.setBackgroundDrawable(bg);
	}

	private void setError(TextView v) {
		v.setBackgroundResource(ERROR_DRAWABLE);

	}

	public SubmitInspectButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		c = context;
	}

	public SubmitInspectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SubmitInspectButton(Context context) {
		super(context);
		init(context);
	}

}
