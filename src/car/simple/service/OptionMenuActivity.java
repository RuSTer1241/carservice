package car.simple.service;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.advertizement.Advertizer;
import car.simple.service.model.PreferenceEditor;
import car.simple.service.utils.WLog;
import com.google.analytics.tracking.android.Tracker;

/**
 * Created by r.savuschuk on 9/5/2014.
 */
public class OptionMenuActivity extends Activity {
	private String LOG_TAG = "OptionMenuActivity";
	public PreferenceEditor prefEditor;
	View actionBarCustomView;
	ActionBar mActionBar;
	Tracker t;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefEditor = CarServiceApplication.getPrefEditor();
		CarServiceApplication.setActivityContext(this);
		WLog.d(LOG_TAG, "onCreate " + this.toString());

		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		actionBarCustomView = mInflater.inflate(R.layout.base_actionbar_layout, null);
		t =  CarServiceApplication.getTracker(CarServiceApplication.TrackerName.APP_TRACKER);

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.activity_actions, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		WLog.d(LOG_TAG, "onResume " + this.toString());
		Advertizer.getInstance(this).pauseTimer(false);//start timer
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onStop();
		Advertizer.getInstance(this).pauseTimer(true);//pause timer
		WLog.d(LOG_TAG, "onPause " + this.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WLog.d(LOG_TAG, "onDestroy " + this.toString());
	}
}