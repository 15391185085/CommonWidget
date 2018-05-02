package com.ieds.gis.base.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.DateUtil;
import com.lidroid.xutils.util.LogUtils;

public class JsonUtil {

	private static final String CHECK_JSON_NULL = "检查JSON数据失败，所用参数不能为空！";
	private static final String JSON_EXC = "JSON数据转换异常！";

	public static <T> T getJson(Class<T> cls, String json)
			throws NullArgumentException {
		try {
			Gson gsonDown = new GsonBuilder()
					.setDateFormat(DateUtil.FORMAT_ONE).disableHtmlEscaping()
					.create();
			if (json == null) {
				throw new NullArgumentException(CHECK_JSON_NULL);
			}
			LogUtils.d(json);
			return gsonDown.fromJson(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullArgumentException(JSON_EXC, e);
		}
	}

	public static <T> String getString(T t) throws NullArgumentException {
		try {
			Gson gsonUp = new GsonBuilder().setDateFormat(DateUtil.FORMAT_ONE)
					.disableHtmlEscaping().create();
			if (t == null) {
				throw new NullArgumentException(CHECK_JSON_NULL);
			}
			String gsonString = gsonUp.toJson(t);
			LogUtils.d(gsonString);
			return gsonString;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullArgumentException(JSON_EXC, e);
		}
	}
}
