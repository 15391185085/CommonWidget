package com.ieds.gis.base.util;

import android.content.Context;

import com.ieds.gis.base.BaseApp;
import com.lidroid.xutils.util.PreferenceUtil;

/**
 * 
 * @see:
 * @since:
 */
public class SettingUtil extends PreferenceUtil {
	private static final String REQUEST_NUMBER = "CONFIG_NUMBER";
	private static final String PORT = "port";
	private static final String IP = "ip";
	private static final String SCALE = "scale";
	// 用来显示初始位置
	private static final String POINT_Y = "pointY";
	private static final String POINT_X = "pointX";
	// 配置文件名称
	private static SettingUtil instance;

	private SettingUtil(Context app) {
		super("com.ieds.gis.base.util.SettingUtil", app);
	}

	/**
	 * 存入屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:27<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @param ip
	 */
	public void setScale(String scale) {
		putString(SCALE, scale);
	}

	/**
	 * 屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:53<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @return
	 */
	public String getScale() {
		return getString(SCALE, null);
	}

	/**
	 * 存入Ip
	 * 
	 * @update 2014-7-24 上午9:53:27<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		putString(IP, ip);
	}

	/**
	 * 存入端口号
	 * 
	 * @update 2014-7-24 上午9:55:55<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @param port
	 */
	public void setPort(String port) {
		putString(PORT, port);
	}

	/**
	 * 得到Ip
	 * 
	 * @update 2014-7-24 上午9:53:53<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @return
	 */
	public String getIp() {
		return getString(IP, "10.10.128.90");
	}

	/**
	 * 得到端口号
	 * 
	 * @update 2014-7-24 上午9:56:13<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @return
	 */
	public String getPort() {
		return getString(PORT, "8080");
	}

	public synchronized static SettingUtil getInstance() {
		if (instance == null) {
			instance = new SettingUtil(BaseApp.getmContext());
		}
		return instance;
	}

	public void setRequest_number(int arg) {
		putInt(REQUEST_NUMBER, arg);
	}

	public int getRequest_number() {
		return getInt(REQUEST_NUMBER, 2);
	}

	/**
	 * 存入屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:27<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @param ip
	 */
	public void setPointX(String point) {
		putString(POINT_X, point);
	}

	/**
	 * 屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:53<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @return
	 */
	public String getPointX() {
		return getString(POINT_X, null);
	}

	/**
	 * 存入屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:27<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @param ip
	 */
	public void setPointY(String point) {
		putString(POINT_Y, point);
	}

	/**
	 * 屏幕位置
	 * 
	 * @update 2014-7-24 上午9:53:53<br>
	 * @author <a href="mailto:yechuangxin@gmail.com">叶创新</a>
	 * 
	 * @return
	 */
	public String getPointY() {
		return getString(POINT_Y, null);
	}
}
