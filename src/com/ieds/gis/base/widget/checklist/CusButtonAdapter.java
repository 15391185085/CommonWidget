package com.ieds.gis.base.widget.checklist;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.lidroid.xutils.exception.NullArgumentException;

/**
 * 缺陷管理中缺陷列表的gridView的adapter
 * 
 * @update 2014-7-15 下午7:38:37<br>
 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
 * 
 */
public abstract class CusButtonAdapter extends BaseAdapter {
	private List<String> mList;
	private Context mContext;
	private boolean buttonShow;
	private String buttonText;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * @param mContext
	 * @param fieldList
	 * @param mList
	 * @param buttonText
	 * @param buttonBack
	 * @param buttonShow
	 *            // 按钮是否显示
	 * @throws NullArgumentException
	 */
	public CusButtonAdapter(Context mContext, List<String> mList,
			String buttonText, boolean buttonShow) throws NullArgumentException {
		super();
		if (mList == null || mContext == null) {
			throw new NullArgumentException();
		}
		this.mContext = mContext;
		this.mList = mList;
		this.buttonShow = buttonShow;
		this.buttonText = buttonText;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final HolderView holderView;
		if (convertView == null) {
			holderView = new HolderView();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_line_item, null);
			holderView.item1 = (TextView) convertView.findViewById(R.id.item1);
			holderView.check_box = (Button) convertView
					.findViewById(R.id.check_box);
			holderView.check_box.setText(buttonText);
			if (!buttonShow) {
				holderView.check_box.setVisibility(View.GONE);
			} else {
				holderView.check_box.setVisibility(View.VISIBLE);
			}
			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.check_box.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onItemClick(position);
			}
		});

		try {
			holderView.fillView(mList.get(position));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	public abstract void onItemClick(int position);

	class HolderView {
		public TextView item1;
		public Button check_box;
		@SuppressLint("ResourceAsColor")
		public void fillView(String o) throws Exception {
			item1.setText(o );
		}
	}

}
