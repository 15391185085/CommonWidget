package com.ieds.gis.base.widget.checklist;

import java.util.List;

import com.ieds.gis.base.dialog.MyToast;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SpinnerEditButton extends Button {

	private Context context;
	private CusListDialog cusListDialog;
	private ItemClickCallback itemCallback;

	public SpinnerEditButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	/**
	 * 
	 * @update 2014-7-14 下午7:25:44<br>
	 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
	 * 
	 * @param chooseButton
	 *            选择按钮
	 * @param strs
	 *            要显示的下拉数据
	 * @param title
	 */
	public void initView(List<String> strs, String title,
			ItemClickCallback itemCallback) {
		this.itemCallback = itemCallback;
		if (strs != null && context instanceof Activity) {
			cusListDialog = new CusListDialog((Activity) context, strs,
					callback) {

				@Override
				public void closeDialog() {
					// TODO Auto-generated method stub

				}

			};
			setTitle(title);
			this.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (cusListDialog != null) {
						cusListDialog.show();
					}

				}
			});
		} else {
			cusListDialog = null;
			this.setText("");
			this.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MyToast.showToast("缺少数据！");
				}
			});
		}

	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		if (cusListDialog != null) {
			cusListDialog.setTitle(title);
		}
	}

	private DialogItemClickCallback callback = new DialogItemClickCallback() {

		@Override
		public void callback(String str, int position) {
			// TODO Auto-generated method stub
			SpinnerEditButton.this.setText(str);
			if (itemCallback != null) {
				itemCallback.callback(str, position);
			}
		}

	};

}
