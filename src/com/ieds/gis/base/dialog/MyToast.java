package com.ieds.gis.base.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.ieds.gis.base.BaseApp;
import com.ieds.gis.base.R;

/**
 * 名称：MyDialog<br>
 * 描述：对话框<br>
 * 最近修改时间：2013-3-10<br>
 * 
 * @author 李昊翔 2013-3-21
 */
public class MyToast {

	private static final String NULL = "未知提醒！";

	public static void showToast(int resId) {
		showToast(BaseApp.getmContext().getText(resId).toString());
	}

	public static void showToast(final CharSequence text) {
		BaseApp.getmContext().runOnUiThread(new Runnable() {

			public void run() {
				String name = "";
				if (!TextUtils.isEmpty(text)) {
					name = text.toString();
				} else {
					name = NULL;
				}
				Toast.makeText(BaseApp.getmContext(), name, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	public static void showDialog(final Activity act, final CharSequence text) {
		if (act != null) {
			act.runOnUiThread(new Runnable() {

				public void run() {
					String name = "";
					if (!TextUtils.isEmpty(text)) {
						name = text.toString();
					} else {
						name = NULL;
					}
					BaseDialog dialog = BaseDialog.getDialog(act,
							R.string.dialog_tips, name, "确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					dialog.show();
					dialog.setCancelable(false);
				}
			});
		} else {
			showToast(text);
		}
	}
}
