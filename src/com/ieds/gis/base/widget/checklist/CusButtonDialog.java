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
import com.lidroid.xutils.exception.NullArgumentException;

/**
 * 自定义下拉列表的dialog
 * 
 * @update 2014-7-14 下午3:19:20<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public abstract class CusButtonDialog extends BaseDialog implements OnItemClickListener {
	public CusButtonAdapter adapter;
	private TextView titleView;
	private DialogItemClickCallback callback;
	private Activity context;

	public CusButtonDialog(Activity context, List<String> strs,
			DialogItemClickCallback callback) throws NullArgumentException {
		super(context, R.layout.cus_list_dialog_view);
		this.callback = callback;
		this.context = context;
		ListView cusListView = (ListView) this.findViewById(R.id.cus_list_view);
		this.findViewById(R.id.btn_cancel).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						closeDialog();
						CusButtonDialog.this.dismiss();
					}
				});
		titleView = (TextView) this.findViewById(R.id.title);

		cusListView.setOnItemClickListener(this);
		adapter = new CusButtonAdapter(context, strs, "管理", false) {

			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
			}

		};
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

	public void closeDialog() {
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
