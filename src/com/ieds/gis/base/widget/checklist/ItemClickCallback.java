package com.ieds.gis.base.widget.checklist;

public interface ItemClickCallback {
	/**
	 * 点击按钮的监听
	 * @update 2014-7-26 下午4:04:54<br>
	 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
	 * 
	 * @param str 返回的被点击的数
	 * @param position 被点击的位置
	 */
    void callback(String str, int position);

}