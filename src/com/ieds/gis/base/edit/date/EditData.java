package com.ieds.gis.base.edit.date;


/**
 * 全局静态类
 */
public class EditData {

	/** 是 */
	public static final int STATIC_YES = 1;
	/** 否 */
	public static final int STATIC_NO = 0;

	public static final int TYPE_EDIT = 0;
	public static final int TYPE_SPINNER = 1;
	public static final int TYPE_RADIO = 2;
	public static final int TYPE_TIME = 3;

	/** 不保留录入字段 */
	public static final int SYTLE_NORMAL = 0;
	/** 输入字段自动完成 */
	public static final int SYTLE_AUTO = 1;
	/** 输入字段在自动完成的基础上可以对数字加减 */
	public static final int SYTLE_ADD_SUB = 2;
	/** 输入整数 */
	public static final int SYTLE_NORMAL_NUMBER = 3;
	/** 输入数字，两位小数 */
	public static final int SYTLE_DECIMAL = 4;
	public static final int SYTLE_NORMAL_ADD_SUB = 5;
	/**
	 * 在EditFactoryActivity中保护TABLE_FACTORY
	 */
	public static final String SAVE_TABLE_FACTORY = "SAVE_TABLE_FACTORY";
	
	/**
	 * 发送打开EditFactoryActivity
	 */
	public static final String SEND_EDIT_BO = "SEND_TABLE_FACTORY";

	/**
	 * 返回EditFactoryActivity的回调
	 */
	public static final int EDIT_BACK = 0x1001;

}
