package com.car.service.alarms;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.car.service.utils.WLog;

/**
 * class for manage alarms
 * This class set alarms in android alarm manager class, and receive alarms from android AlarmManager.
 * Created by r.savuschuk on 10/6/2014.
 */
public class AlarmController {
	private final String TAG = this.getClass().getSimpleName();

	private Activity activity;
	private static volatile AlarmController theOnlyInstance;



	public AlarmController(Activity context) {
		this.activity = context;
	}

	/**
	 * the realization of Singleton getInstance method.
	 * @return the ONLY instance of singleton
	 */
	public static AlarmController getInstance(Activity activity) {
		if (theOnlyInstance == null) {
			synchronized (AlarmController.class) {
				if (theOnlyInstance == null) {
					theOnlyInstance = new AlarmController(activity);
				}
			}
		}
		return theOnlyInstance;
	}



	public void setAlarm(long time) {
		AlarmManager am = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(activity, AlarmReceiver.class);
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//PendingIntent pia = PendingIntent.getActivity(context, 1,i, PendingIntent.FLAG_CANCEL_CURRENT);
		PendingIntent pi = PendingIntent.getBroadcast(activity, 1, i, PendingIntent.FLAG_CANCEL_CURRENT);
		//todo it is not exact wake up time from 19 api. need to investigate.
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pi); // Millisec * Second * Minute
		WLog.d(TAG, "setAlarm" + this.toString());
	}

	public void сancelAlarm() {
		Intent intent = new Intent(activity, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(activity, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		WLog.d(TAG, "сancelAlarm" + this.toString());
	}
}
