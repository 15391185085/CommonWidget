package com.ieds.gis.base.edit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.view.ViewGroup.LayoutParams;

import com.ieds.gis.base.dialog.MyToast;
import com.ieds.gis.base.edit.TableFactory.MyField;
import com.ieds.gis.base.widget.SubmitInspectButton;
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
import com.lidroid.xutils.db.table.MyColumn;
import com.lidroid.xutils.db.table.MyId;
import com.lidroid.xutils.db.table.MyTable;
import com.lidroid.xutils.exception.DbException;

/**
 * 设备对应的控件
 * 
 * @author lihx
 * 
 */
public class GisTableWidget {
	private TableFactory tableFactory;
	private List<AbsWidget> widgetList;
	private List<AbsWidgetBo> widgetBoList;

	public GisTableWidget(TableFactory tableFactory,
			List<AbsWidget> widgetList, List<AbsWidgetBo> widgetBoList) {
		super();
		this.tableFactory = tableFactory;
		this.widgetList = widgetList;
		this.widgetBoList = widgetBoList;
	}

	/**
	 * 保存设备的关联数据，并且更新录入数据
	 * 
	 * @param gisTW
	 * @param onSaveListener
	 * @throws DbException
	 */
	public Serializable saveObject() throws Exception {
		Serializable mainObject = tableFactory.getMainObj();
		if (widgetList != null && widgetList.size() > 0) {
			HashMap<String, String> hm = new HashMap<String, String>();
			List<MyField> idFieldList = tableFactory.getMyFieldList();

			for (int i = 0; i < idFieldList.size(); i++) {
				hm.put(idFieldList.get(i).getFieldName(), idFieldList.get(i)
						.getFieldValue());
			}

			for (int i = 0; i < widgetList.size(); i++) {
				AbsWidget aw = widgetList.get(i);
				AbsWidgetBo v = widgetBoList.get(i);
				hm.put(v.getSqlField(), v.getSqlValue());
			}
			mainObject = getEntity(hm, mainObject);
		}
		return mainObject;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getEntity(HashMap<String, String> cursor, T oldEntity)
			throws DbException {
		if (cursor == null) {
			return null;
		}

		MyTable table = MyTable.get(oldEntity.getClass());
		Set s = cursor.keySet();
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			MyColumn column = table.columnMap.get(key);
			if (column != null) {
				column.setValue2Entity(oldEntity, cursor.get(key));
			} else {
				List<MyId> idList = table.getId();
				for (Iterator iterator2 = idList.iterator(); iterator2
						.hasNext();) {
					MyId id = (MyId) iterator2.next();
					if (key.equals(id.getColumnName())) {
						id.setValue2Entity(oldEntity, cursor.get(key));
					}
				}

			}
		}
		return oldEntity;
	}

	/**
	 * @param offlineEditorFragment
	 * @throws DbException
	 */
	public static boolean checkSaveData(List<AbsWidget> widgetList)
			throws DbException {
		// TODO Auto-generated method stub
		boolean isSuccess = true;
		if (widgetList != null && widgetList.size() > 0) {
			for (int i = 0; i < widgetList.size(); i++) {
				AbsWidget v = widgetList.get(i);
				if (v.isNull()) {
					v.setError();
					isSuccess = false;
				} else {
					v.setNormal();
					v.setSave();
				}

			}
		}
		if (!isSuccess) {
			MyToast.showToast(SubmitInspectButton.CHECK_ERROR);
		}
		return isSuccess;
	}

}
