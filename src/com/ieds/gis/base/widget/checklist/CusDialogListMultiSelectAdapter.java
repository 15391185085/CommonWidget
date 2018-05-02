package com.ieds.gis.base.widget.checklist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ieds.gis.base.R;

/**
 * 自定义下拉列表中ListView的Adapter
 *
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * @update 2014-7-14 下午3:19:57<br>
 */
public class CusDialogListMultiSelectAdapter extends BaseAdapter implements OnItemClickListener {
    private Context mContext;
    private List<String> strs;
    private List<Integer> checkedPosition;

    public CusDialogListMultiSelectAdapter(Context mContext) {
        this.mContext = mContext;
        strs = new ArrayList<String>();
        checkedPosition = new ArrayList<Integer>();
    }

    public void setDatas(List<String> strs) {
        this.strs = strs;
        checkedPosition.clear();
        notifyDataSetChanged();
    } 
    public void selectAll(boolean isSelectAll) {
        checkedPosition.clear();
        if (isSelectAll)
            for (int i = 0; i < strs.size(); i++) {
                checkedPosition.add(i);
            }
        notifyDataSetChanged();
    }
    public List<Integer> getCheckedPosition(){
        return checkedPosition;
    }

    @Override
    public int getCount() {
        return strs.size() == 0? 1:strs.size();
    }

    @Override
    public Object getItem(int position) {
        return strs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new HolderView();
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.cus_list_dialog_view_item_checkbox, null);
            holder.textView = (TextView) convertView.findViewById(R.id.tv);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }

        holder.checkbox.setVisibility(View.VISIBLE);

        if (strs.size() != 0) {
            holder.textView.setText(strs.get(position));
            holder.checkbox.setChecked(checkedPosition.contains(position));
        }else{
            holder.textView.setText("没有查找到线路!");
            holder.checkbox.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (checkedPosition.contains(position)) {
            checkedPosition.remove((Integer) position);
        } else {
            checkedPosition.add(position);
        }
        notifyDataSetChanged();
    }

    private class HolderView {
        public TextView textView;
        public CheckBox checkbox;
    }
}
