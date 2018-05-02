package com.ieds.gis.base.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.util.ProgressTask;
import com.ieds.gis.base.widget.SubmitInspectButton;

/**
 * 还原出厂设置
 * 
 * @update 2014-9-26 上午9:33:29<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public abstract class RestoreDialog extends BaseDialog {
	private SubmitInspectButton btn_confirm;
	private EditText etRestore;
	private TextView tvRestore;

	public abstract void onWriteThread() throws Exception;

	public abstract void onWriteFinish();

	public void addTvRestore(String text) {
		tvRestore.setText(tvRestore.getText() + "\n" + text);
	}

	/**
	 * 
	 * @param context
	 * @param checkText
	 *            核对数据
	 */
	public RestoreDialog(final Activity context, final String title,
			final String checkText) {
		super(context, R.layout.dialog_restore);

		setCancelable(false);
		btn_confirm = (SubmitInspectButton) this.findViewById(R.id.btn_confirm);
		((TextView) this.findViewById(R.id.title)).setText(title);
		etRestore = (EditText) this.findViewById(R.id.etRestore);
		tvRestore = (TextView) this.findViewById(R.id.tvRestore);

		tvRestore.setText("请输入（" + checkText + "）括号中的内容来完成操作！");
		tvRestore.setVisibility(View.VISIBLE);
		btn_confirm.addView(etRestore);
		btn_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etRestore.getText().toString().equals(checkText)) {
					RestoreDialog.this.cancel();
					ProgressTask pt = new ProgressTask(context, false, true,
							true, checkText + "中，请稍后...") {

						@Override
						public boolean onRunning() throws Exception {
							onWriteThread();
							return true;
						}

						@Override
						public void onSucceed() {
							onWriteFinish();
						}

						@Override
						public void onFail(String errorInfo) {
						}
					};
				} else {
					MyToast.showToast("输入错误，请重新输入！");
					etRestore.setText("");
				}

			}
		});
		((Button) this.findViewById(R.id.btn_cancel))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						RestoreDialog.this.cancel();
					}
				});
	}
}
