package com.ieds.gis.base.edit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ieds.gis.base.BaseActivity;
import com.ieds.gis.base.R;
import com.ieds.gis.base.dao.IDbUtils;
import com.ieds.gis.base.dialog.BaseDialog;
import com.ieds.gis.base.dialog.MyToast;
import com.ieds.gis.base.edit.bo.EditBo;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.WidgetEditBo;
import com.ieds.gis.base.widget.bo.WidgetRadioBo;
import com.ieds.gis.base.widget.bo.WidgetSpinnerBo;
import com.ieds.gis.base.widget.bo.WidgetTimeBo;
import com.ieds.gis.base.widget.edit.AbsWidget;
import com.ieds.gis.base.widget.edit.WidgetEdit;
import com.ieds.gis.base.widget.edit.WidgetRadio;
import com.ieds.gis.base.widget.edit.WidgetSpinner;
import com.ieds.gis.base.widget.edit.WidgetTime;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.SerializableUtil;

/**
 * 编辑图形属性的页面
 * 
 * @author lihx
 * 
 */
public abstract class EditFactoryActivity extends BaseActivity {

	private List<AbsWidgetBo> widgetBoList;
	private List<AbsWidget> widgetList;
	private LinearLayout mainLayout;
	private Button record;
	private Button fastRecord;
	private TextView title;
	private ImageView back;
	private ImageView rightIcon;
	private View editView, mainButtonLayer;
	private View textLayer;

	private TableFactory tableFactory;
	protected GisTableWidget gisTableWidget;

	/**
	 * 去往编辑的页面
	 * 
	 * @param act
	 * @param i
	 */
	public static void toEditActivity(Activity act, Intent i) {
		act.startActivityForResult(i, EditData.EDIT_BACK);
	}

	public static void toEditActivity(Activity act, Class gisActivityClass,
			EditBo eb) {
		toEditActivity(act, getEditIntent(act, gisActivityClass, eb));
	}

	public static Intent getEditIntent(Activity act, Class gisActivityClass,
			EditBo eb) {
		Intent i = new Intent(act, gisActivityClass);
		i.putExtra(EditData.SEND_EDIT_BO, eb);
		return i;
	}

	public abstract IDbUtils getDb() throws DbException;

	public abstract void saveOk() throws Exception;

	public abstract void saveCancel() throws Exception;

	private void saveMainObject() {
		try {
			if (gisTableWidget.checkSaveData(widgetList)) {
				Serializable saveObject = gisTableWidget.saveObject();
				getDb().replace(saveObject);
				saveOk();
				EditFactoryActivity.this.finish();
				MyToast.showToast("保存数据成功！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MyToast.showToast(e.getMessage());
		}
	}

	private void initGisTableWidget() throws DbException {
		for (Iterator iterator = widgetBoList.iterator(); iterator.hasNext();) {
			AbsWidgetBo awb = (AbsWidgetBo) iterator.next();
			AbsWidget aw = null;
			if (awb instanceof WidgetEditBo) {
				WidgetEditBo bo = (WidgetEditBo) awb;
				// 不需要验证空
				if (!tableFactory.isEditMode()) {
					bo.setEnableNull(true);
				}
				aw = new WidgetEdit(this, bo);
				widgetList.add(aw);
			} else if (awb instanceof WidgetRadioBo) {
				WidgetRadioBo bo = (WidgetRadioBo) awb;
				// 不需要验证空
				if (!tableFactory.isEditMode()) {
					bo.setEnableNull(true);
				}
				aw = new WidgetRadio(this, bo);
				widgetList.add(aw);
			} else if (awb instanceof WidgetSpinnerBo) {
				WidgetSpinnerBo bo = (WidgetSpinnerBo) awb;
				// 不需要验证空
				if (!tableFactory.isEditMode()) {
					bo.setEnableNull(true);
				}
				aw = new WidgetSpinner(this, bo);
				widgetList.add(aw);
			} else if (awb instanceof WidgetTimeBo) {
				WidgetTimeBo bo = (WidgetTimeBo) awb;
				// 不需要验证空
				if (!tableFactory.isEditMode()) {
					bo.setEnableNull(true);
				}
				aw = new WidgetTime(this, bo);
				widgetList.add(aw);
			}
			if (aw != null) {
				mainLayout.addView(aw.getView(), new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			} else {
				throw new DbException("没有对应字段的显示方案！");
			}
		}
		gisTableWidget = new GisTableWidget(tableFactory, widgetList,
				widgetBoList);
	}

	/**
	 * 初始化title栏右边按钮
	 * 
	 * @param drawable
	 *            图片的id
	 * @param click
	 */
	public void initTitleRightButton(int drawable, OnClickListener click) {
		rightIcon.setImageResource(drawable);
		rightIcon.setVisibility(View.VISIBLE);
		rightIcon.setOnClickListener(click);
	}

	private void initButtonLayer() {
		editView = LayoutInflater.from(this).inflate(R.layout.a_edit_button,
				null);
		mainButtonLayer = editView.findViewById(R.id.mainButtonLayer);

		mainLayout.addView(editView, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	public Button initButton1(String name, OnClickListener click) {
		Button btn = (Button) editView.findViewById(R.id.btn1);
		if (name != null && click != null) {
			btn.setVisibility(View.VISIBLE);
			mainButtonLayer.setVisibility(View.VISIBLE);
			btn.setOnClickListener(click);
			btn.setText(name);
		}
		return btn;
	}

	public Button initButton2(String name, OnClickListener click) {
		Button btn = (Button) editView.findViewById(R.id.btn2);
		if (name != null && click != null) {
			btn.setVisibility(View.VISIBLE);
			mainButtonLayer.setVisibility(View.VISIBLE);
			btn.setOnClickListener(click);
			btn.setText(name);
		}
		return btn;
	}

	public Button initButton3(String name, OnClickListener click) {
		Button btn = (Button) editView.findViewById(R.id.btn3);
		if (name != null && click != null) {
			btn.setVisibility(View.VISIBLE);
			mainButtonLayer.setVisibility(View.VISIBLE);
			btn.setOnClickListener(click);
			btn.setText(name);
		}
		return btn;
	}

	public Button initButton4(String name, OnClickListener click) {
		Button btn = (Button) editView.findViewById(R.id.btn4);
		if (name != null && click != null) {
			btn.setVisibility(View.VISIBLE);
			mainButtonLayer.setVisibility(View.VISIBLE);
			btn.setOnClickListener(click);
			btn.setText(name);
		}
		return btn;
	}

	public Button initButton5(String name, OnClickListener click) {
		Button btn = (Button) editView.findViewById(R.id.btn5);
		if (name != null && click != null) {
			btn.setVisibility(View.VISIBLE);
			mainButtonLayer.setVisibility(View.VISIBLE);
			btn.setOnClickListener(click);
			btn.setText(name);
		}
		return btn;
	}

	public void initTextView(String title, String name) {
		if (title != null && name != null) {
			View mainTextLayer = textLayer.findViewById(R.id.mainTextLayer);
			TextView tv1 = (TextView) textLayer.findViewById(R.id.text1);
			TextView tv2 = (TextView) textLayer.findViewById(R.id.edit1);
			tv1.setText(title);
			tv2.setText(name);
			mainTextLayer.setVisibility(View.VISIBLE);
		}
	}

	private void initTextLayer() {
		textLayer = LayoutInflater.from(this).inflate(R.layout.a_layout_text,
				null);
		mainLayout.addView(textLayer, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			EditBo eb = (EditBo) this.getIntent().getSerializableExtra(
					EditData.SEND_EDIT_BO);
			if (eb.getDynamicList() != null) {
				tableFactory = TableFactory.getInstance(getDb(),
						eb.getMainObj(), eb.isEditMode(), eb.getDynamicList());
			} else {
				tableFactory = TableFactory.getInstance(getDb(),
						eb.getMainObj(), eb.isEditMode());
			}
			initView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MyToast.showToast(e.getMessage());
		}

	}

	/**
	 * @param offlineEditorFragment
	 */
	public void back() {
		// 是否退出
		if (tableFactory.isEditMode()) {
			BaseDialog.getDialog(EditFactoryActivity.this,
					R.string.dialog_tips, "是否保存记录？", "保存",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							saveMainObject();

						}

					}, "不保存", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								saveCancel();
								EditFactoryActivity.this.finish();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								MyToast.showToast(e.getMessage());
							}
						}
					}).show();
		} else {
			EditFactoryActivity.this.finish();
		}
	}

	public void initView() throws DbException {
		widgetBoList = tableFactory.getList();
		widgetList = new ArrayList<AbsWidget>();
		this.setContentView(R.layout.a_layout);
		mainLayout = (LinearLayout) this.findViewById(R.id.layout);
		initButtonLayer();
		initTextLayer();
		record = (Button) this.findViewById(R.id.record);
		fastRecord = (Button) this.findViewById(R.id.fastRecord);
		title = (TextView) this.findViewById(R.id.main_txt);
		back = (ImageView) this.findViewById(R.id.ivfanhui);
		rightIcon = (ImageView) this.findViewById(R.id.rightIcon);
		title.setText(tableFactory.getGdbTable().getTable_label());
		if (tableFactory.isEditMode()) {
			record.setVisibility(View.VISIBLE);
			fastRecord.setVisibility(View.VISIBLE);
		} else {
			record.setVisibility(View.GONE);
			fastRecord.setVisibility(View.GONE);
		}
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				back();
			}

		});
		initGisTableWidget();
		fastRecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					for (Iterator iterator = widgetList.iterator(); iterator
							.hasNext();) {
						AbsWidget type = (AbsWidget) iterator.next();
						type.initSytleText();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MyToast.showToast(e.getMessage());
				}
			}

		});

		record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveMainObject();
			}

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 点击退出按钮
			back();
		}
		return super.onKeyDown(keyCode, event);
	}

	// 为了防止activity被销毁保存生成的pk_id
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (outState != null) {
			try {
				if (tableFactory != null) {
					outState.putString(EditData.SAVE_TABLE_FACTORY,
							SerializableUtil.SerToBase64(tableFactory));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyToast.showToast(e.getMessage());
			}
		}
	}

	/**
	 * 执行在onCreate()方法之后
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			try {
				if (savedInstanceState.getString(EditData.SAVE_TABLE_FACTORY) != null) {
					tableFactory = (TableFactory) SerializableUtil
							.base64ToSer(savedInstanceState
									.getString(EditData.SAVE_TABLE_FACTORY));
					if (tableFactory != null) { // 还原上次的记录数据
						initView();
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				MyToast.showToast(e1.getMessage());
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
