package com.ieds.gis.base.widget.checklist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ieds.gis.base.R;
/**自定义下拉列表中ListView的Adapter
 * @update 2014-7-14 下午3:19:57<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 *
 */
public class CusDialogListAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> strs;
	private boolean hasCheckBox;

	public CusDialogListAdapter(Context mContext, List<String> strs) {
		this.mContext = mContext;
		this.strs = strs;
	}

	public void setDatas(List<String> strs) {
		this.strs = strs;
		notifyDataSetChanged();
	}

	public void setHasCheckBox(boolean hasCheckBox) {
		this.hasCheckBox = hasCheckBox;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strs==null?0:strs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView;
		if(convertView==null||convertView.getTag()==null){
		holderView=new HolderView();
		convertView=LayoutInflater.from(mContext).inflate(R.layout.cus_list_dialog_view_item, null);
		holderView.tView=(TextView) convertView.findViewById(R.id.tv);
		convertView.setTag(holderView);
		}else{
			holderView=(HolderView) convertView.getTag();
		}
		holderView.tView.setText(strs.get(position));
		return convertView;
	}
	private class HolderView{
		public TextView tView;
	}
}
