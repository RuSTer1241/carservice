package com.car.service.alarms;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import com.car.service.MyActivity;
import com.car.service.utils.WLog;

/**
 * Created by r.savuschuk on 10/30/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {
	private final String TAG = this.getClass().getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
		kl.disableKeyguard();
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
			PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
		wakeLock.acquire();

		// Put here YOUR code.
		//Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example
		Intent intent1 = new Intent(context, MyActivity.class);
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent1);
		WLog.d(TAG, "OnReceive " + this.toString());
		wakeLock.release();
		AlarmProcessor alarmProc = new AlarmProcessor(context);
		alarmProc.handleAlarm();
	}
}
