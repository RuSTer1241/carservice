package com.car.service.eventcontroller;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.car.service.R;
import com.car.service.ServiceWorksDialog;

import java.io.Serializable;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class CarServiceController extends EventController implements ServiceWorksDialog.OnDialogDataSavedListener,Serializable

{
	private String TAG = getClass().getSimpleName();
	Activity activity;
	DialogFragment newFragment;
	private TextView service_works;
	private EditText sum_price;

	public CarServiceController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout, LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		this.activity=activity;
		title.setText(activity.getResources().getString(R.string.service));
		titleImage.setImageResource(R.drawable.eat_bottle_dialogtitle);
		service_works =(TextView)mainLayout.findViewById(R.id.service_works_but);
		service_works.setOnClickListener(serviceWorksButOnClickListener);
		sum_price=(EditText)commentLayout.findViewById(R.id.price_edit_text);

	}

	View.OnClickListener serviceWorksButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new ServiceWorksDialog();

			Bundle args = new Bundle();
			args.putSerializable("CarServiceController",CarServiceController.this);
			newFragment.setArguments(args);
			newFragment.show(ft, "ServiceComponents Dialog");
		}
	};

	@Override
	public boolean saveEventToDb() {
		/*engine
			.dbWriteRequest(DbEngine.Action.EAT, t_spinner.getSelectedItem().toString(), comment.getText().toString(), new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data) {
					WLog.e("DbEngine", " OnSuccess");
					prefEditor.saveEatQuantity(t_spinner.getSelectedItemPosition());
					prefEditor.saveEatTimer(t_spinner.getSelectedItemPosition());
					long temp = timeParse(t_spinner.getSelectedItem().toString());
					if (temp > 0) {//set new alarm if time period>0
						timerController.setAlarm(temp);
					}
				}

				@Override
				public void onFail(final DbError error) {
					WLog.e("DbEngine", " OnFail");
					Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
				}
			});
*/
		return true;
	}

	@Override
	public void onDataSaved(final String price) {
		sum_price.setText(price);
	}



}
