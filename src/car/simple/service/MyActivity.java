package car.simple.service;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.actiondialog.EventDialog;
import car.simple.service.database.DbEngine;
import com.google.android.gms.analytics.HitBuilders;

public class MyActivity extends OptionMenuActivity {

	DbEngine engine;
	ImageView eatBut;
	ImageView kBut;
	ImageView peeBut;
	ImageView temperatureBut;
	ImageView weightBut;
	ImageView arrowBut;
	DialogFragment newFragment;
	ActionBarController actionBarController;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		engine = CarServiceApplication.getDbEngine();
		eatBut = (ImageView) findViewById(R.id.eat_but);
		kBut = (ImageView) findViewById(R.id.crap_but);
		peeBut = (ImageView) findViewById(R.id.pee_but);
		temperatureBut = (ImageView) findViewById(R.id.temperature_but);
		arrowBut = (ImageView) findViewById(R.id.arrow_but);
		weightBut = (ImageView) findViewById(R.id.weight_but);
		eatBut.setOnClickListener(EatButOnClickListener);
		kBut.setOnClickListener(KButOnClickListener);
		peeBut.setOnClickListener(PButOnClickListener);
		temperatureBut.setOnClickListener(TButOnClickListener);
		weightBut.setOnClickListener(weightButOnClickListener);
		arrowBut.setOnClickListener(ArrowButOnClickListener);

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

	View.OnClickListener EatButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new EventDialog(0);
			newFragment.show(ft, "Eat Dialog");
		}
	};
	View.OnClickListener KButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			//newFragment = new KaDialog();
			newFragment = new EventDialog(1);
			newFragment.show(ft, "Ka Dialog");
		}
	};
	View.OnClickListener PButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			//newFragment = new PeDialog();
			newFragment = new EventDialog(2);
			newFragment.show(ft, "Pee Dialog");
		}
	};
	View.OnClickListener TButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			//newFragment = new TDialog();
			newFragment = new EventDialog(3);
			newFragment.show(ft, "Pee Dialog");
		}
	};
	View.OnClickListener weightButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new EventDialog(4);
			newFragment.show(ft, "Weight Dialog");
		}
	};
	View.OnClickListener ArrowButOnClickListener = new View.OnClickListener() {
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
