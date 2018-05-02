package com.ieds.gis.base.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.ieds.gis.base.R;
import com.ieds.gis.base.dialog.BaseDialog;
import com.ieds.gis.base.dialog.MyToast;
import com.ieds.gis.base.dialog.ProgressDialog;

/**
 * 只有成功时需要toast，失败时可以用dialog提示，否则需要自定义提示
 * 
 * @update 2014-9-16 上午9:22:52<br>
 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
 * 
 */
public abstract class ProgressTask extends AsyncTask<Object, Integer, Boolean> {
	private static final String TASK_ERROR = "空指针异常，业务处理失败！";
	private static final String TAG = "ProgressTask";
	private BaseDialog warnDialog; // 提醒dialog，用于触发前的提醒
	private BaseDialog errorDialog;
	private Activity mContext;
	private volatile String errorInfo = TASK_ERROR;

	private boolean mStartAlert;// 弹出开始提示框样式
	private boolean mMiddleAlert;// 弹出等待框样式
	private boolean mEndAlert;// 弹出结束提示样式
	private String mAlertMessage = "数据处理中...请稍后...";
	private String mMessage = "提示";
	private boolean stopAlertClose; // true:线程结束时延长等待窗口，直到closeAlert被调用
	private ProgressDialog pd;

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		if (!TextUtils.isEmpty(errorInfo)) {
			this.errorInfo = errorInfo;
		}
	}

	/**
	 * 有加载中的字幕
	 * 
	 * @param context
	 * @param message
	 *            描述dialog
	 */
	public ProgressTask(Activity context, String message) {
		this(context, false, true, false, message);
	}

	/**
	 * 不带回掉，不带提示, 不带加载中的字幕
	 * 
	 * @param context
	 */
	public ProgressTask(Activity context) {
		this(context, false, false, false, null);
	}

	public ProgressTask(Activity context, boolean sAlert, boolean mAlert,
			boolean eAlert, String sAlertMessage, String mAlertMessage) {
		super();
		if (!TextUtils.isEmpty(mAlertMessage)) {
			this.mAlertMessage = mAlertMessage;
		}
		if (!TextUtils.isEmpty(sAlertMessage)) {
			this.mMessage = sAlertMessage;
		}
		this.mContext = context;
		mStartAlert = sAlert;// 弹出开始提示框样式
		mMiddleAlert = mAlert;// 弹出等待框样式
		mEndAlert = eAlert;// 弹出结束提示样式 this.mIsAlert = isAlert;
		if (mStartAlert && mMiddleAlert) {
			// 弹窗以后，确认执行
			String yesBut = "确定";
			if (mMessage.contains("是否下载")) {
				yesBut = "下载";
			} else if (mMessage.contains("是否上传")) {
				yesBut = "上传";
			} else if (mMessage.contains("是否更新")) {
				yesBut = "更新";
			} else if (mMessage.contains("是否删除")) {
				yesBut = "删除";
			} else if (mMessage.contains("是否完结")) {
				yesBut = "完结";
			} else if (mMessage.contains("是否强制完结")) {
				yesBut = "完结";
			}
			warnDialog = BaseDialog.getDialog(mContext, R.string.dialog_tips,
					mMessage, yesBut, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							startExecute();
						}
					}, "取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							negative();
						}
					});
			warnDialog.show();
			warnDialog.setCancelable(false);
		} else if (!mStartAlert && mMiddleAlert) {
			// 直接执行弹出等待条
			startExecute();
		} else {
			// 直接执行，不弹出等待条
			this.execute();
		}
	}

	/**
	 * @param mContext
	 * @param isAlert
	 *            true:开始提示
	 * @param mAlert
	 *            true:等待提示
	 * @param eAlert
	 *            true:结束提示
	 * @param title
	 *            等待窗口显示的文字
	 * @param param
	 *            运行进程时传递的参数
	 */
	public ProgressTask(Activity context, boolean sAlert, boolean mAlert,
			boolean eAlert, String sAlertMessage) {
		this(context, sAlert, mAlert, eAlert, sAlertMessage, null);
	}

	public void stopAlertClose() {
		this.stopAlertClose = true;
	}

	/**
	 * stopAlertClose为true时需要用该方法来关闭
	 * 
	 * @param result
	 */
	public void closeAlert(final Boolean result) {
		if (pd != null) {
			pd.cancel();
			pd.closeBar();
		}
		if (mEndAlert) {
			// 结束数弹出提示框
			postEndDialog(result);
		} else {
			if (result) {
				onSucceed();
			} else {
				onFail(errorInfo);
			}
		}

	}

	public void negative() {

	}

	/**
	 * 
	 */
	public void startExecute() {
		pd = new ProgressDialog(mContext, mAlertMessage);
		pd.show();
		pd.startBar();
		this.execute();
	}

	private double size;

	/**
	 * 统计尺寸
	 * 
	 * @param size
	 */
	public double getSize() {
		return size;
	}

	/**
	 * 统计尺寸
	 * 
	 * @param size
	 */
	public void setSize(double size) {
		this.size += size;
	}

	public void setProgressText(final String name) {
		((Activity) mContext).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (pd != null) {
					pd.setName(name);
				}
			}
		});

	}

	private void postEndDialog(Boolean isSucceed) {
		if (isSucceed) {
			onSucceed();
			MyToast.showToast("执行成功！");
		} else {
			errorDialog = BaseDialog.getDialog(mContext, R.string.dialog_tips,
					errorInfo, "确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							onFail(errorInfo);
						}
					});
			errorDialog.show();
			errorDialog.setCancelable(false);
		}
	}

	@Override
	protected Boolean doInBackground(Object... str) {
		try {
			return onRunning();
		} catch (Exception e) {
			e.printStackTrace();
			errorInfo = e.getMessage();
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		if (!stopAlertClose) {
			closeAlert(result);
		}
	}

	/**
	 * 处理事物
	 * 
	 * @return
	 */
	public abstract boolean onRunning() throws Exception;

	/**
	 * 主线程：根据子现实返回值，处理事物成功
	 */
	public abstract void onSucceed();

	/**
	 * 主线程：根据子现实返回值，处理事物失败
	 */
	public abstract void onFail(String errorInfo);
}
