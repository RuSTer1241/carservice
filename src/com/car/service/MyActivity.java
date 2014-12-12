package com.car.service;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.car.service.actiondialog.EventDialog;
import com.car.service.database.DbEngine;
import com.car.service.model.CarServiceApplication;
import com.google.android.gms.analytics.HitBuilders;

public class MyActivity extends OptionMenuActivity {

	DbEngine engine;
	ImageView techServBut;
	ImageView adminServBut;
	ImageView fuelBut;
	ImageView temperatureBut;
	ImageView weightBut;
	ImageView commentBut;
	DialogFragment newFragment;
	ActionBarController actionBarController;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		engine = CarServiceApplication.getDbEngine();
		techServBut = (ImageView) findViewById(R.id.techserv_but);
		adminServBut = (ImageView) findViewById(R.id.admin_but);
		fuelBut = (ImageView) findViewById(R.id.fuel_but);
		commentBut = (ImageView) findViewById(R.id.comment_but);

		techServBut.setOnClickListener(techServButOnClickListener);
		adminServBut.setOnClickListener(adminButOnClickListener);
		fuelBut.setOnClickListener(fuelButOnClickListener);
		commentBut.setOnClickListener(commentButOnClickListener);

		actionBarController=new ActionBarController(this,actionBarCustomView);
		actionBarController.initActionViewMyActivity();
		mActionBar.setCustomView(actionBarCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);



	}

	@Override
	protected void onResume() {
		super.onResume();
		t.send(new HitBuilders.EventBuilder()
			.setCategory("open activity")
			.setAction(this.getClass().getSimpleName())
			.setValue(1)
			.build());
	}

	View.OnClickListener techServButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new EventDialog(0);
			newFragment.show(ft, "Eat Dialog");
		}
	};
	View.OnClickListener adminButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			//newFragment = new KaDialog();
			newFragment = new EventDialog(1);
			newFragment.show(ft, "Ka Dialog");
		}
	};
	View.OnClickListener fuelButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			//newFragment = new PeDialog();
			newFragment = new EventDialog(2);
			newFragment.show(ft, "Pee Dialog");
		}
	};
	View.OnClickListener commentButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new EventDialog(5);
			newFragment.show(ft, "Weight Dialog");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
//		menu.getItem(0).setVisible(false);//chart/line  button
	//	menu.getItem(1).setVisible(true);//share button
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.statistic:
//				Intent intent = new Intent(MyActivity.this, Statistic.class);
//				startActivity(intent);
//				break;
//			case R.id.share:
//				Utils.share(this);
//				break;
//			default:
//				break;
//		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
