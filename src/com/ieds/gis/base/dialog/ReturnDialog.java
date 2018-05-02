package com.ieds.gis.base.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.widget.SubmitInspectButton;

/**
 * 返回输入值
 * 
 * @update 2014-9-26 上午9:33:29<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public abstract class ReturnDialog extends BaseDialog {
	private SubmitInspectButton btn_confirm;
	private EditText etRestore;
	private TextView tvRestore;

	public abstract void onWriteTrue(String str);

	public ReturnDialog(final Activity context, final String title) {
		this(context, title, "");
	}

	/**
	 * 
	 * @param context
	 * @param title
	 * @param isNum
	 *            只能输入数字
	 */
	public ReturnDialog(final Activity context, final String title,
			final String editText) {
		super(context, R.layout.dialog_restore);
		setCancelable(false);
		btn_confirm = (SubmitInspectButton) this.findViewById(R.id.btn_confirm);
		((TextView) this.findViewById(R.id.title)).setText(title);
		etRestore = (EditText) this.findViewById(R.id.etRestore);
		etRestore.setText(editText);
		tvRestore = (TextView) this.findViewById(R.id.tvRestore);
		btn_confirm.addView(etRestore);
		btn_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(etRestore.getText())) {
					onWriteTrue(etRestore.getText().toString());
					ReturnDialog.this.cancel();
				} else {
					MyToast.showToast("输入不能是空！");
				}

			}
		});
		((Button) this.findViewById(R.id.btn_cancel))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ReturnDialog.this.cancel();
					}
				});
	}

}
