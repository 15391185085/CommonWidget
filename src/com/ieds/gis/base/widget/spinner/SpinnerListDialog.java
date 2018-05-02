package com.ieds.gis.base.widget.spinner;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.bo.VariableBo;
import com.ieds.gis.base.dialog.BaseDialog;

/**
 * 自定义下拉列表的dialog
 * 
 * @update 2014-7-14 下午3:19:20<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public class SpinnerListDialog<T> extends BaseDialog implements
		OnItemClickListener {
	public SpinnerRadioListAdapter<T> adapter;
	private TextView titleView;
	private OnSpinnerItemClickListener<T> callback;

	public SpinnerListDialog(Activity context, List<T> list,
			VariableBo vb, OnSpinnerItemClickListener<T> callback) {
		super(context, R.layout.spinner_list_radio_view);
		this.callback = callback;
		ListView cusListView = (ListView) this.findViewById(R.id.cus_list_view);
		titleView = (TextView) this.findViewById(R.id.title);
		titleView.setText(vb.getVariableLabel());
		cusListView.setOnItemClickListener(this);
		adapter = new SpinnerRadioListAdapter<T>(context, list, vb.getVariableName());
		cusListView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		dismiss();
		if (callback != null) {
			callback.onClickItem(adapter.getItem(position));
		}
	}

	/**
	 * dialog切换显示
	 * 
	 * @update 2014-7-14 下午2:57:41<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 */
	public void toggle() {
		if (isShowing()) {
			dismiss();
		} else {
			show();
		}
	}

	/**
	 * 设置dialog显示并传入数据
	 * 
	 * @update 2014-7-14 下午3:20:48<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @param list
	 */
	public void show(List<T> list) {
		show();
		adapter.setDataList(list);
	}

	/**
	 * 设置dialog标题
	 * 
	 * @update 2014-7-14 下午3:21:15<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		titleView.setText(title);
	}
}
