package com.ieds.gis.base.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * 滑动切换栏
 * @author lihx
 *
 */
public class ZxzcAdapter extends FragmentStatePagerAdapter {

	List<Fragment> list;

	public ZxzcAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {

		return list.size();
	}

}
