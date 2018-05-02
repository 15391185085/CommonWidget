package com.ieds.gis.base.widget.time;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.view.View;

import com.ieds.gis.base.R;

class WheelMain {

	public static final String DATE_FORM = "yyyy-MM-dd HH:mm:ss";

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	private static int START_YEAR = 1990, END_YEAR = 2100;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelMain(View view, int flag) {
		super();
		this.view = view;
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_mins = (WheelView) view.findViewById(R.id.minute);
		switch (flag) {
		case 0:// 只显示日期
			wv_year.setVisibility(View.VISIBLE);
			wv_month.setVisibility(View.VISIBLE);
			wv_day.setVisibility(View.VISIBLE);
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			break;
		case 1:// 显示日期和时间
			wv_year.setVisibility(View.VISIBLE);
			wv_month.setVisibility(View.VISIBLE);
			wv_day.setVisibility(View.VISIBLE);
			wv_hours.setVisibility(View.VISIBLE);
			wv_mins.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		initDateTimePicker();

	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 时
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);
		wv_hours.setLabel("时");
		wv_hours.setCyclic(true);

		// 分
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);
		wv_mins.setLabel("分");
		wv_mins.setCyclic(true);

		// 日
		wv_day.setCyclic(true);

		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
	}

	public void initTime(int year, int month, int day, int hour, int minute) {
		wv_year.setCurrentItem(year-START_YEAR);
		wv_month.setCurrentItem(month);
		wv_day.setCurrentItem(day - 1);
		wv_hours.setCurrentItem(hour);
		wv_mins.setCurrentItem(minute);
	}

	public int getYear() {
		return wv_year.getCurrentItem() + START_YEAR;
	}

	public int getMonth() {
		return wv_month.getCurrentItem() + 1;
	}

	public int getDay() {
		return wv_day.getCurrentItem() + 1;
	}

	public int getCurrentHour() {
		return wv_hours.getCurrentItem();
	}

	public int getCurrentMinute() {
		return wv_mins.getCurrentItem();
	}


	/**
	 * 得到的日期精确到秒
	 * 
	 * @update 2014-7-11 上午10:55:55<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @return
	 */
	public String getDateToSecond() {
		StringBuffer sb = new StringBuffer();
		String year = "" + (wv_year.getCurrentItem() + START_YEAR);
		String month = "" + (wv_month.getCurrentItem() + 1);
		month = getDoubleLength(month);
		String day = ""+ (wv_day.getCurrentItem() + 1);
		day = getDoubleLength(day);

		String hours = "" + (wv_hours.getCurrentItem());
		hours = getDoubleLength(hours);

		String mins = "" + (wv_mins.getCurrentItem());
		mins = getDoubleLength(mins);
		String seconds = "00";
		sb.append(year).append("-")
				.append(month).append("-")
				.append(day).append(" ")
				.append(hours).append(":")
				.append(mins).append(":")
				.append(seconds);
		return sb.toString();
	}
	/**
	 * 得到的日期精确到天
	 * 
	 * @update 2014-7-11 上午10:55:55<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * 
	 * @return
	 */
	public String getDateToDay() {
		StringBuffer sb = new StringBuffer();
		String year = "" + (wv_year.getCurrentItem() + START_YEAR);
		String month = "" + (wv_month.getCurrentItem() + 1);
		month = getDoubleLength(month);
		String day = ""+ (wv_day.getCurrentItem() + 1);
		day = getDoubleLength(day);
		String hours = "00";
		String mins = "00";
		String seconds = "00";
		sb.append(year).append("-")
				.append(month).append("-")
				.append(day).append(" ")
				.append(hours).append(":")
				.append(mins).append(":")
				.append(seconds);
		return sb.toString();
	}
	private static String getDoubleLength(String str) {
		if(str == null){
			str = "00";
		} if(str.length()==1){
			str = "0" + str;
		}else if(str.trim().length()==0){
			str = "00";
		}
		return str;
	}

}
