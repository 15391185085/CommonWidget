package com.ieds.gis.base.widget.spinner;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.dialog.MyToast;
import com.lidroid.xutils.util.ReflexUtil;

/**
 * 自定义下拉列表中ListView的Adapter
 * 
 * @update 2014-7-14 下午3:19:57<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public class SpinnerRadioListAdapter<T> extends BaseAdapter {
	private Context mContext;
	private List<T> list;
	private String variableName;

	public SpinnerRadioListAdapter(Context mContext, List<T> list,
			String variableName) {
		this.mContext = mContext;
		this.list = list;
		this.variableName = variableName;
	}

	public void setDataList(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView;
		if (convertView == null || convertView.getTag() == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.spinner_list_radio_view_item, null);
			holderView.tView = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}
		T t = list.get(position);
		// 通过key反射获取对应的参数
		try {
			Object o = ReflexUtil.getPrivateVariable(t, variableName);
			if (o != null) {
				holderView.tView.setText(o.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MyToast.showToast(e.getMessage());
		}
		return convertView;
	}

	private class HolderView {
		public TextView tView;
	}
}
