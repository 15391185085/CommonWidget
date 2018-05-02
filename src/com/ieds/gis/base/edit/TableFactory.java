package com.ieds.gis.base.edit;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Id;

import android.text.TextUtils;

import com.ieds.gis.base.dao.ISelector;
import com.ieds.gis.base.dao.IDbUtils;
import com.ieds.gis.base.edit.date.EditData;
import com.ieds.gis.base.po.GDB_Attr;
import com.ieds.gis.base.po.GDB_Table;
import com.ieds.gis.base.widget.bo.AbsWidgetBo;
import com.ieds.gis.base.widget.bo.FieldValue;
import com.ieds.gis.base.widget.bo.WidgetEditBo;
import com.ieds.gis.base.widget.bo.WidgetRadioBo;
import com.ieds.gis.base.widget.bo.WidgetSpinnerBo;
import com.ieds.gis.base.widget.bo.WidgetTimeBo;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.DateUtil;
import com.lidroid.xutils.util.ReflexUtil;

/**
 * 设备对应的视图属性
 * 
 * @author lihx
 * 
 */
public class TableFactory implements Serializable {

	private Serializable mainObj;
	// 业务id，新增业务时赋值
	private List<MyField> myFieldList;
	// 是否是编辑模式（浏览模式，编辑模式）
	private boolean isEditMode;
	// 表对应的项属性
	private List<WidgetEditBo> elist;
	// 表对应的项属性
	private List<WidgetRadioBo> rlist;
	// 表对应的项属性
	private List<WidgetSpinnerBo> slist;
	// 表对应的项属性
	private List<WidgetTimeBo> tlist;
	private GDB_Table gdbTable; // 对应的条目

	public Serializable getMainObj() {
		return mainObj;
	}

	public IDbUtils db;

	public static class MyField implements Serializable {

		private String fieldName;
		private String fieldValue;

		public String getFieldName() {
			return fieldName;
		}

		public String getFieldValue() {
			return fieldValue;
		}

		public void setFieldValue(String fieldValue) {
			this.fieldValue = fieldValue;
		}

		public MyField(String fieldName) {
			super();
			this.fieldName = fieldName;
		}

	}

	public List<MyField> getMyFieldList() {
		return myFieldList;
	}

	public GDB_Table getGdbTable() {
		return gdbTable;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	/**
	 * 初始化控件的显示值
	 * 
	 * @throws DbException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NullArgumentException
	 */
	public void initFieldValue() throws DbException, IllegalAccessException,
			IllegalArgumentException, NullArgumentException {
		for (Iterator iterator = myFieldList.iterator(); iterator.hasNext();) {
			MyField type = (MyField) iterator.next();
			try {
				type.setFieldValue((ReflexUtil.getPrivateVariable(
						mainObj.getClass(), mainObj, type.getFieldName()))
						.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new NullArgumentException("没有发现该字段，表="
						+ mainObj.getClass().getSimpleName() + "，字段="
						+ type.getFieldName());
			}
		}
	}

	public List<AbsWidgetBo> getList() {
		List<AbsWidgetBo> wblist = new ArrayList<AbsWidgetBo>();
		if (elist != null) {
			for (Iterator iterator = elist.iterator(); iterator.hasNext();) {
				AbsWidgetBo type = (AbsWidgetBo) iterator.next();
				wblist.add(type);
			}

		}
		if (slist != null) {
			for (Iterator iterator = slist.iterator(); iterator.hasNext();) {
				AbsWidgetBo type = (AbsWidgetBo) iterator.next();
				wblist.add(type);
			}
		}
		if (rlist != null) {
			for (Iterator iterator = rlist.iterator(); iterator.hasNext();) {
				AbsWidgetBo type = (AbsWidgetBo) iterator.next();
				wblist.add(type);
			}
		}
		if (tlist != null) {
			for (Iterator iterator = tlist.iterator(); iterator.hasNext();) {
				AbsWidgetBo type = (AbsWidgetBo) iterator.next();
				wblist.add(type);
			}
		}
		return wblist;
	}

	/**
	 * 得到实体对象
	 * 
	 * @param mainObj
	 * @param db
	 * @param dynamicList
	 *            需要动态生成的控件
	 * @return
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	static TableFactory getInstance(final IDbUtils db,
			Serializable mainObj, boolean isEditMode,
			List<AbsWidgetBo> dynamicList) throws Exception {
		TableFactory tf = new TableFactory(db, isEditMode, mainObj);
		List<AbsWidgetBo> awbList = tf.getAllAttrList(dynamicList);
		tf.addDynamicList(awbList);
		tf.initFieldValue();
		return tf;
	}

	/**
	 * 给GDB_Attr表中映射的控件赋值
	 */
	private List<AbsWidgetBo> getAllAttrList(List<AbsWidgetBo> dynamicList)
			throws HttpException, DbException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException,
			NullArgumentException {
		List<AbsWidgetBo> awbList = getAttrTableList();
		if (dynamicList != null) {
			awbList.addAll(dynamicList);
		}
		for (Iterator iterator = awbList.iterator(); iterator.hasNext();) {
			AbsWidgetBo awb = (AbsWidgetBo) iterator.next();
			String fieldName = awb.getSqlField();
			// 根据方法名反射出它的值
			Object o = ReflexUtil.getPrivateVariable(mainObj, fieldName);
			if (o != null) {
				if (o instanceof Date) {
					awb.setSqlValue(DateUtil.sqlDateToString((Date) o));
				} else {
					awb.setSqlValue(o.toString());
				}
			}
		}
		return awbList;
	}

	/**
	 * 
	 * @param mainObj
	 * @param db
	 * @return
	 * @throws DbException
	 * @throws ClassNotFoundException
	 * @throws NullArgumentException
	 */
	static TableFactory getInstance(IDbUtils db, Serializable mainObj,
			boolean isEditMode) throws Exception, ClassNotFoundException {
		return getInstance(db, mainObj, isEditMode, null);
	}

	/**
	 * @param subType
	 *            根据subType过滤设备,null:不做过滤
	 * @param awblist
	 * @param gdbArrt
	 * @param fvList
	 * @param enableNull
	 * @param isEditEnable
	 */
	private void addWidgetItem(List<AbsWidgetBo> awblist, GDB_Attr gdbArrt,
			ArrayList<FieldValue> fvList, boolean enableNull,
			boolean isEditEnable) {
		String id = gdbArrt.getTable_name() + gdbArrt.getField_key();
		// 自动控件是可编辑的
		switch (gdbArrt.getShow_style()) {
		case EditData.TYPE_EDIT:
			awblist.add(new WidgetEditBo(id, enableNull, gdbArrt
					.getAttr_label(), gdbArrt.getField_key(), isEditEnable,
					gdbArrt.getMax_value(), Integer.valueOf(gdbArrt
							.getEdit_style()), gdbArrt.getIs_oneline()));
			break;
		case EditData.TYPE_SPINNER:
			awblist.add(new WidgetSpinnerBo(id, enableNull, gdbArrt
					.getAttr_label(), gdbArrt.getField_key(), isEditEnable,
					fvList));
			break;
		case EditData.TYPE_RADIO:
			awblist.add(new WidgetRadioBo(id, enableNull, gdbArrt
					.getAttr_label(), gdbArrt.getField_key(), isEditEnable,
					fvList));
			break;
		case EditData.TYPE_TIME:
			awblist.add(new WidgetTimeBo(id, enableNull, gdbArrt
					.getAttr_label(), gdbArrt.getField_key(), isEditEnable));
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 组合得到GDB_Attr表中映射的控件
	 * 
	 * @param threeBoList
	 *            第三方传入的控件
	 * @return
	 * @throws DbException
	 * @throws ClassNotFoundException
	 * @throws NullArgumentException
	 */
	private List<AbsWidgetBo> getAttrTableList() throws DbException,
			ClassNotFoundException, NullArgumentException {
		List<AbsWidgetBo> awblist = new ArrayList<AbsWidgetBo>();
		ISelector sAEM = db.from(GDB_Attr.class);
		sAEM.where("table_name", "=", gdbTable.getTable_name());
		sAEM.and("isvisible", "=", EditData.STATIC_YES);
		sAEM.orderBy("order_num + 0");
		List<GDB_Attr> amList = db.findAll(sAEM);
		for (Iterator iterator2 = amList.iterator(); iterator2.hasNext();) {
			GDB_Attr gdbArrt = (GDB_Attr) iterator2.next();
			ArrayList<FieldValue> fvList = new ArrayList<FieldValue>();

			if (!TextUtils.isEmpty(gdbArrt.getData_show())
					&& !TextUtils.isEmpty(gdbArrt.getData_insert())) {

				if (gdbArrt.getData_show().toLowerCase().indexOf("select") != -1) {
					putSelectFieldValueList(gdbArrt, fvList);
				} else {
					String shows[] = gdbArrt.getData_show().split(",");
					String inserts[] = gdbArrt.getData_insert().split(",");
					for (int i = 0; i < shows.length; i++) {
						fvList.add(new FieldValue(shows[i], inserts[i]));
					}
				}
			}

			boolean enableNull = false;
			if (gdbArrt.getEnable_null() == EditData.STATIC_YES) {
				enableNull = true;
			}

			if (isEditMode) { // 新增或编辑业务
				// 只显示可编辑的控件
				if (gdbArrt.getIs_edit() == EditData.STATIC_YES) {
					addWidgetItem(awblist, gdbArrt, fvList, enableNull, true);
				} else {
					addWidgetItem(awblist, gdbArrt, fvList, enableNull, false);
				}

			} else { // 查询业务
				// 显示所有控件并且为查询状态
				addWidgetItem(awblist, gdbArrt, fvList, enableNull, false);
			}

		}
		return awblist;
	}

	/**
	 * @param GDB_Arrt
	 * @param fvList
	 */
	public void putSelectFieldValueList(GDB_Attr GDB_Arrt,
			ArrayList<FieldValue> fvList) throws DbException {
		String show = GDB_Arrt.getData_show();
		String insert = GDB_Arrt.getData_insert();
		setFV(fvList, show, insert);
	}

	/**
	 * @param fvList
	 * @param show
	 * @param insert
	 * @throws DbException
	 */
	public void setFV(ArrayList<FieldValue> fvList, String show, String insert)
			throws DbException {
		List<String> showList = new ArrayList<String>();
		List<List<String>> showLL = db.execListQuery(show);
		for (Iterator iterator = showLL.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			showList.add(list.get(0));
		}
		List<String> insertList = new ArrayList<String>();
		List<List<String>> insertLL = db.execListQuery(show);
		for (Iterator iterator = insertLL.iterator(); iterator.hasNext();) {
			List<String> list = (List<String>) iterator.next();
			insertList.add(list.get(0));
		}
		for (int i = 0; i < showList.size(); i++) {
			fvList.add(new FieldValue(showList.get(i), insertList.get(i)));
		}
	}

	/**
	 * 
	 * @param gdbTable
	 * @param dynamicList
	 *            需要动态生成的控件
	 * @param isEditMode
	 * @param packageName
	 *            待显示业务表所在包名
	 * @throws NullArgumentException
	 * @throws ClassNotFoundException
	 */
	private TableFactory(IDbUtils db, boolean isEditMode,
			Serializable mainObj) throws Exception {
		super();
		this.db = db;
		GDB_Table gdb = new GDB_Table();
		gdb.setTable_name(mainObj.getClass().getSimpleName());
		gdb = db.findFirstById(gdb);
		this.mainObj = mainObj;
		this.isEditMode = isEditMode;
		this.gdbTable = gdb;
		myFieldList = new ArrayList<MyField>();
		Field[] fields = mainObj.getClass().getDeclaredFields();
		if (fields != null) {

			for (Field field : fields) {
				if (field.getAnnotation(Id.class) != null) {
					myFieldList.add(new MyField(field.getName()));
				}
			}
		}
		elist = new ArrayList<WidgetEditBo>();
		rlist = new ArrayList<WidgetRadioBo>();
		slist = new ArrayList<WidgetSpinnerBo>();
		tlist = new ArrayList<WidgetTimeBo>();
	}

	/**
	 * 加载动态列表
	 * 
	 * @param list
	 */
	public void addDynamicList(List<AbsWidgetBo> list) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			AbsWidgetBo awb = (AbsWidgetBo) iterator.next();
			if (awb instanceof WidgetEditBo) {
				elist.add((WidgetEditBo) awb);

			} else if (awb instanceof WidgetRadioBo) {
				rlist.add((WidgetRadioBo) awb);

			} else if (awb instanceof WidgetSpinnerBo) {
				slist.add((WidgetSpinnerBo) awb);

			} else if (awb instanceof WidgetTimeBo) {
				tlist.add((WidgetTimeBo) awb);
			}
		}
	}
}
