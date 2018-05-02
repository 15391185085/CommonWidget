package com.ieds.gis.base.widget.spinner;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.adapter.MyCheckAdapter;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.ReflexUtil;

/**
 * 自定义下拉列表中ListView的Adapter
 * 
 * @update 2014-7-14 下午3:19:57<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public class SpinnerCheckboxListAdapter<T> extends MyCheckAdapter {
	private String variableName;

	public SpinnerCheckboxListAdapter(Context mContext, List<T> mList,
			OnSelectedListener callback, String variableName)
			throws NullArgumentException {
		super(mContext, mList, callback);
		this.variableName = variableName;
	}

	public SpinnerCheckboxListAdapter(Context mContext, List<T> list,
			String variableName) throws NullArgumentException {
		super(mContext, list, null);
		this.variableName = variableName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView;
		if (convertView == null || convertView.getTag() == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.spinner_list_checkbox_view_item, null);
			holderView = new HolderView(convertView);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		holderView.fillView(position, convertView);
		return convertView;
	}

	class HolderView {
		public CheckBox checkBox;
		public TextView tView;
		public View llCheckboxView;

		public HolderView(View convertView) {
			tView = (TextView) convertView.findViewById(R.id.tv);

			checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
			llCheckboxView = convertView.findViewById(R.id.check_item);
		}

		public void fillView(int position, View convertView) {
			try {
				final T t = (T) mList.get(position);
				// 通过key反射获取对应的参数
				Object o = ReflexUtil.getPrivateVariable(t, variableName);
				if (o != null) {
					tView.setText(o.toString());
				}
				if (selectedList.contains(t)) {
					checkBox.setChecked(true);
				} else {
					checkBox.setChecked(false);
				}
				llCheckboxView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (checkBox.isChecked()) {
							checkBox.setChecked(false);
							subSelect(t);
						} else {
							checkBox.setChecked(true);
							addSelect(t);
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
