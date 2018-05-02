package com.ieds.gis.base.widget.checklist;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.dialog.BaseDialog;

/**
 * 自定义下拉列表的dialog
 * 
 * @update 2014-7-14 下午3:19:20<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public abstract class CusListDialog extends BaseDialog implements OnItemClickListener {
	public CusDialogListAdapter adapter;
	private TextView titleView;
	private DialogItemClickCallback callback;
	private Activity context;

	public CusListDialog(Activity context, List<String> strs,
			DialogItemClickCallback callback) {
		super(context, R.layout.cus_list_dialog_view);
		this.callback = callback;
		this.context = context;
		ListView cusListView = (ListView) this.findViewById(R.id.cus_list_view);
		this.findViewById(R.id.btn_cancel).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						closeDialog();
						CusListDialog.this.dismiss();
					}
				});
		titleView = (TextView) this.findViewById(R.id.title);

		cusListView.setOnItemClickListener(this);
		adapter = new CusDialogListAdapter(context, strs);
		cusListView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		dismiss();
		if (callback != null) {
			callback.callback((String) adapter.getItem(position), position);
		}
	}

	public abstract void closeDialog();

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
	 * @param strs
	 */
	public void show(List<String> strs) {
		show();
		adapter.setDatas(strs);
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
