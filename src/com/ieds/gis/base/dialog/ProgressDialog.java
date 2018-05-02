package com.ieds.gis.base.dialog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.ieds.gis.base.R;
import com.ieds.gis.base.view.NumberProgressBar;
import com.ieds.gis.base.view.OnProgressBarListener;

public class ProgressDialog extends BaseDialog {
	private Timer timer;

	private NumberProgressBar bnp;
	private TextView progressText;
	private Context mContext;

	public ProgressDialog(Activity context, String message) {
		super(context, R.layout.common_dialog_progress);
		this.mContext = context;
		initViews(context, message);
		bnp.setProgress(0);
		setCancelable(false);
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		closeBar();
	}

	public void closeBar() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * 
	 */
	public void startBar() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				((Activity) mContext).runOnUiThread(new Runnable() {
					@Override
					public void run() {

						bnp.incrementProgressBy(1);
					}
				});
			}
		}, 1000, 200);
	}

	/**
	 * @param context
	 * @param title
	 */
	public void initViews(Context context, String title) {
		bnp = (NumberProgressBar) findViewById(R.id.progressBar);
		progressText = (TextView) findViewById(R.id.progressText);
		progressText.setText(title);
		bnp.setOnProgressBarListener(new OnProgressBarListener() {

			@Override
			public void onProgressChange(int current, int max) {
				if (current == max) {
					bnp.setProgress(0);
				}
			}
		});
	}

	public void setName(String name) {
		progressText.setText(name);
	}

	public void setBnpVisibility(int visibility) {
		bnp.setVisibility(visibility);
	}

}
