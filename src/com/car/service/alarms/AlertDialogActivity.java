package com.car.service.alarms;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.car.service.R;
import com.car.service.model.CarServiceApplication;
import com.car.service.utils.WLog;

/**
 * Created by r.savuschuk on 10/8/2014.
 */
public class AlertDialogActivity extends Activity {
	private String LOG_TAG="AlertDialogActivity";
	Button alert_ok;
    MediaPlayer mPlayer;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WLog.d(LOG_TAG, "onCreate " + this.toString());
        setContentView(R.layout.alert_dialog);
		alert_ok=(Button)findViewById(R.id.alert_ok);
		alert_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				userAction();
			}
		});

	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();

	}

	private void userAction(){
		mPlayer = CarServiceApplication.getPlayer();
		mPlayer.pause();
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
		WLog.d(LOG_TAG, "onPause " + this.toString());
	}

	@Override
	protected void onStop() {
		super.onStop();
		WLog.d(LOG_TAG, "onStop " + this.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WLog.d(LOG_TAG, "onDestroy " + this.toString());
	}
}
