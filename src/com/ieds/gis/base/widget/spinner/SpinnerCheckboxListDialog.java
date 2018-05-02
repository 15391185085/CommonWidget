package com.ieds.gis.base.widget.spinner;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.bo.VariableBo;
import com.ieds.gis.base.dialog.BaseDialog;
import com.lidroid.xutils.exception.NullArgumentException;

/**
 * 自定义下拉列表的dialog
 * 
 * @update 2014-7-14 下午3:19:20<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public class SpinnerCheckboxListDialog<T> extends BaseDialog{
	public SpinnerCheckboxListAdapter<T> adapter;
	private TextView titleView;
	private Button btnChoose;
	

	public void setBtnChoose(String btnName) {
		this.btnChoose.setText(btnName);
	}


	public SpinnerCheckboxListDialog(Activity context, List<T> list,
			VariableBo vb, final OnSpinnerItemClickListener<T> spinnerItemClick) throws NullArgumentException {
		super(context, R.layout.spinner_list_checkbox_view);
		if (list == null || vb == null || context == null) {
			throw new NullArgumentException();
		}
		ListView cusListView = (ListView) this.findViewById(R.id.cus_list_view);
		titleView = (TextView) this.findViewById(R.id.title);
		titleView.setText(vb.getVariableLabel());
		btnChoose = (Button) this.findViewById(R.id.btnChoose);
		btnChoose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if (spinnerItemClick != null) {
					spinnerItemClick.onClickList(adapter.getSelectdList());
				}
			}
		});
		adapter = new SpinnerCheckboxListAdapter<T>(context, list, vb.getVariableName());
		cusListView.setAdapter(adapter);
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

}
