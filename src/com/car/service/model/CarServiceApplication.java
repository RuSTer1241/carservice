package com.car.service.model;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import com.car.service.R;
import com.car.service.EventManager;
import com.car.service.advertizement.Advertizer;
import com.car.service.database.DbEngine;
import com.car.service.utils.WLog;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

import java.util.HashMap;

/**
 * Created by r.savuschuk on 8/7/14.
 */
public class CarServiceApplication extends Application {
	private static Context applicationContext;
	private static Context activityContext;
	private static DbEngine dbEngine;
	private static long fromTime;
	private static long toTime;
	private static PreferenceEditor prefEditor;
	private static MediaPlayer player;
	private static EventManager eventManager;
	private static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();


	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = getApplicationContext();
		dbEngine = new DbEngine(applicationContext);
		prefEditor = new PreferenceEditor(applicationContext);
		Advertizer.getInstance(applicationContext);
		player = MediaPlayer.create(applicationContext, R.raw.ship_bell);
		eventManager=new EventManager(prefEditor.getLaunch());


	}

	public static Context getActivityContext() {
		return activityContext;
	}

	public static void setActivityContext(final Context activityContext) {
		CarServiceApplication.activityContext = activityContext;
	}

	public static PreferenceEditor getPrefEditor() {
		return prefEditor;
	}
	public static EventManager getEventManager() {
		return eventManager;
	}


	public static DbEngine getDbEngine() {
		return dbEngine;
	}

	public static Context getAppContext() {
		return applicationContext;
	}

	public static long getFromTime() {
		WLog.v("TIME", "  " + Thread.currentThread().getName().toString());
		return fromTime;
	}

	public static void setFromTime(final long fromTime) {
		WLog.v("TIME", "  " + Thread.currentThread().getName().toString());
		CarServiceApplication.fromTime = fromTime;
	}

	public static long getToTime() {
		WLog.v("TIME", "  " + Thread.currentThread().getName().toString());
		return toTime;
	}

	public static void setToTime(final long toTime) {
		WLog.v("TIME", "  " + Thread.currentThread().getName().toString());
		CarServiceApplication.toTime = toTime;
	}

	public static MediaPlayer getPlayer() {
		return player;
	}

	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 *
	 * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
	 * storing them all in Application object helps ensure that they are created only once per
	 * application instance.
	 */
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	}

	public static synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(CarServiceApplication.getAppContext());
			Tracker t =  analytics.getTracker("UA-57575623-1");
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}
}
