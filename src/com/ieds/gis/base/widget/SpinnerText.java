package com.ieds.gis.base.widget;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ieds.gis.base.bo.VariableBo;
import com.ieds.gis.base.dialog.BaseDialog;
import com.ieds.gis.base.dialog.MyToast;
import com.ieds.gis.base.widget.spinner.OnSpinnerItemClickListener;
import com.ieds.gis.base.widget.spinner.SpinnerCheckboxListDialog;
import com.ieds.gis.base.widget.spinner.SpinnerListDialog;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.ReflexUtil;
import com.lidroid.xutils.util.StringUtil;

/**
 * 重做了下拉列表可以支持多选
 * 
 * @update 2015-4-20 下午3:33:28<br>
 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
 * 
 * @param <T>
 */
public class SpinnerText<T> extends EditText {
	/**
	 * 多选时显示的分隔符
	 */
	private static final String SEPARATE = " ";
	public static final int RADIO = 0;// flag 单选
	public static final int CHECKBOX = 1;// flag 多选
	private Activity context;
	private BaseDialog cusListDialog;
	private OnSpinnerItemClickListener<T> itemCallback;
	private VariableBo vb;

	public SpinnerText(Activity context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	/**
	 * 
	 * @param chooseButton
	 *            选择按钮
	 * @param list
	 *            下拉列表所存放的相关数据
	 * @param variableName
	 *            对象T要显示在下拉列表中的对应变量
	 * @param title
	 *            下拉列表的名字
	 * @param itemCallback
	 * @throws NullArgumentException
	 */
	public void initView(Button chooseButton, List<T> list, VariableBo vb,
			OnSpinnerItemClickListener<T> itemCallback, int flag)
			throws NullArgumentException {
		this.vb = vb;
		this.itemCallback = itemCallback;
		if (list != null) {
			switch (flag) {
			case RADIO:
				cusListDialog = new SpinnerListDialog<T>(context, list, vb,
						callback);
				break;
			case CHECKBOX:
				cusListDialog = new SpinnerCheckboxListDialog<T>(context, list,
						vb, callback);
				break;
			default:
				return;
			}

			chooseButton.setOnClickListener(new View.OnClickListener() {
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
			chooseButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MyToast.showToast("缺少数据！");
				}
			});
		}

	}

	private OnSpinnerItemClickListener<T> callback = new OnSpinnerItemClickListener<T>() {

		@Override
		public void onClickItem(T t) {
			// 通过key反射获取对应的参数
			try {
				Object o = ReflexUtil.getPrivateVariable(t,
						vb.getVariableName());
				if (o != null) {
					SpinnerText.this.setText(o.toString());
				}
				if (itemCallback != null) {
					itemCallback.onClickItem(t);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onClickList(List<T> tList) {
			try {
				StringBuilder sb = new StringBuilder();
				for (Iterator iterator = tList.iterator(); iterator.hasNext();) {
					T t = (T) iterator.next();
					// 通过key反射获取对应的参数
					Object o = ReflexUtil.getPrivateVariable(t,
							vb.getVariableName());
					if (o != null) {
						sb.append(o.toString() + SEPARATE);
					}
				}
				SpinnerText.this.setText(StringUtil.deleteLastCharacter(sb));
				if (itemCallback != null) {
					itemCallback.onClickList(tList);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	public String getChooseVariable(String VariableName, List<T> tList) {
		try {
			StringBuilder sb = new StringBuilder();
			if (tList != null) {
				for (Iterator iterator = tList.iterator(); iterator.hasNext();) {
					T t = (T) iterator.next();
					Object o = ReflexUtil.getPrivateVariable(t, VariableName);
					if (o != null) {
						sb.append(o + SpinnerText.SEPARATE);
					}
				}
				return StringUtil.deleteLastCharacter(sb);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getChooseVariable(String VariableName, T t) {
		try {
			if (t != null) {
				Object o = ReflexUtil.getPrivateVariable(t, VariableName);
				return o.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
