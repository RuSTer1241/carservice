package car.simple.service.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by r.savuschuk on 9/18/2014.
 */
public class PreferenceEditor {
	Context activityContext;
	private SharedPreferences sPref;
	public static final String STAT_PREF = "STAT_PREF" ;
	private static final String STAT_CURTAB = "CURTAB";
	private static final String FRAGMENT_SEL = "CURFRAGMENT";
	private static final String MEASURE_UNIT = "MEASURE_UNIT";
	private static final String EAT_QUANTITY = "EAT_QUANTITY";
	private static final String EAT_TIMER = "EAT_TIMER";
	private static final String FIRST_LAUNCH = "FIRST_LAUNCH";
	private static final String DEFAULT_EVENTS = "DEFAULT_EVENTS";
	private static final String TEMPERATURE_POSITION = "TEMPERATURE_POSITION";

	public PreferenceEditor(Context context) {
		this.activityContext=context;
		sPref = activityContext.getSharedPreferences(STAT_PREF,activityContext.MODE_PRIVATE);
		saveAppFirstLaunch();
	}

	public void saveCurTab(int tab) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putInt(STAT_CURTAB, tab);
		ed.commit();
	}

	public int loadTabId() {
		return sPref.getInt(STAT_CURTAB, 0);
	}

	/**
	 * @param b false for ListItemFragment
	 * true for GraphFragment
	 */
	public void saveCurFragment(boolean b) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putBoolean(FRAGMENT_SEL, b);
		ed.commit();
	}

	public boolean getCurFragment() {
		return sPref.getBoolean(FRAGMENT_SEL, true);
	}
	/**
	 * @param b true for British,
	 * false for Metric
	 */
	public void saveMeasureUnit(boolean b) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putBoolean(MEASURE_UNIT, b);
		ed.commit();
	}
	/**
	 * return true for British,
	 * false for Metric
	 */
	public boolean getMeasureUnit() {
		return sPref.getBoolean(MEASURE_UNIT, true);
	}

	/**
	 * @param position -eat spinner position
	 */
	public void saveEatQuantity(int position) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putInt(EAT_QUANTITY, position);
		ed.commit();
	}
	/**
	 * return  spinner position
	 */
	public int getTemperPos() {
		return sPref.getInt(TEMPERATURE_POSITION, 26);
	}

	/**
	 * @param position -eat spinner position
	 */
	public void saveTemperPos(int position) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putInt(TEMPERATURE_POSITION, position);
		ed.commit();
	}
	/**
	 * return  spinner position
	 */
	public int getEatQuantity() {
		return sPref.getInt(EAT_QUANTITY, 0);
	}


	/**
	 * @param position - timer spinner position
	 */
	public void saveEatTimer(int position) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putInt(EAT_TIMER, position);
		ed.commit();
	}
	/**
	 * return  spinner position
	 */
	public int getTimerQuantity() {
		return sPref.getInt(EAT_TIMER, 0);
	}

	/**
	 * app launch counter
	 */
	public void saveAppFirstLaunch() {
		long count=getLaunch();
		SharedPreferences.Editor ed = sPref.edit();
		ed.putLong(FIRST_LAUNCH, ++count);
		ed.commit();
	}
	/**
	 * get app launch counter
	 */
	public long getLaunch() {
		return sPref.getLong(FIRST_LAUNCH, 0);
	}

	public void saveEvent(String value) {
		SharedPreferences.Editor ed = sPref.edit();
		ed.putString(DEFAULT_EVENTS, value);
		ed.commit();
	}

	public String getEvents() {
        return sPref.getString(DEFAULT_EVENTS, null);
	}


}