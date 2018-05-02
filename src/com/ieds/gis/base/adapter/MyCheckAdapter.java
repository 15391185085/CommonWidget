package com.ieds.gis.base.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.NullArgumentException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 支持多选的adapter
 * @author lihx
 *
 * @param <T>
 */
public class MyCheckAdapter<T> extends BaseAdapter {
	protected List<T> selectedList = new ArrayList<T>();// 存放所有被选中的数据
	protected OnSelectedListener callback;
	protected List<T> mList;
	protected Context mContext;
	public MyCheckAdapter(Context mContext, List<T> mList, OnSelectedListener callback) throws NullArgumentException {
		if (mList == null || mContext == null) {
			throw new NullArgumentException();
		}
		this.mContext = mContext;
		this.mList = mList;
		this.callback = callback;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查看选中数量的监听
	 * @author lihx
	 *
	 */
	public interface OnSelectedListener {
		/**
		 * 
		 * @param count 选中的数量
		 */
		public void callback(int count);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * @update 2014-11-4 下午6:30:26<br>
	 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public List<T> getSelectdList() {
		return selectedList;
	}

	/**
	 * 移除一个选择项
	 * 
	 * @param bo
	 */
	public void subSelect(T s) {
		selectedList.remove(s);
		if (callback != null)
			callback.callback(selectedList.size());
		notifyDataSetChanged();
	}

	/**
	 * 加一个选择项
	 * 
	 * @param bo
	 */
	public void addSelect(T bo) {
		selectedList.add(bo);
		if (callback != null)
			callback.callback(selectedList.size());
		notifyDataSetChanged();
	}

	/**
	 * 全不选
	 */
	public void noAllSelect() {
		selectedList.clear();
		if (callback != null)
			callback.callback(selectedList.size());
		notifyDataSetChanged();
	}

	/**
	 * 全选
	 */
	public void allSelect() {
		selectedList.clear();
		if (mList != null) {
			selectedList.addAll(mList);
		}
		if (callback != null)
			callback.callback(selectedList.size());
		notifyDataSetChanged();
	}

	/**
	 * 删除选中
	 * 
	 * @update 2014-7-16 上午10:44:58<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @param list
	 */
	public void removeSelected() {
		mList.removeAll(selectedList);
		selectedList.clear();
		if (callback != null)
			callback.callback(selectedList.size());
		notifyDataSetChanged();
	}

	/**
	 * 替换全部
	 * 
	 * @param mList
	 */
	public void replaceAll(List<T> mList) {
		this.selectedList.clear();
		this.mList = mList;
		notifyDataSetChanged();
	}

	/**
	 * 加集合
	 * 
	 * @param mList
	 */
	public void addAll(List<T> mList) {
		this.selectedList.clear();
		this.mList.addAll(mList);
		notifyDataSetChanged();
	}

	/**
	 * 加
	 * 
	 * @param mList
	 */
	public void add(T t) {
		this.selectedList.clear();
		this.mList.add(t);
		notifyDataSetChanged();
	}
	/**
	 * 移出集合
	 * 
	 * @param mList
	 */
	public void removeAll(List<T> mList) {
		this.selectedList.clear();
		this.mList.removeAll(mList);
		notifyDataSetChanged();
	}

	/**
	 * 移出
	 * 
	 * @param mList
	 */
	public void remove(T t) {
		this.selectedList.clear();
		this.mList.remove(t);
		notifyDataSetChanged();
	}

	public int getSelectCount() {
		return selectedList.size();
	}

}
