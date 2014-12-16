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
import com.car.service.PriceController;
import com.car.service.R;
import com.car.service.ServiceWorksDialog;
import com.car.service.alarms.AlarmController;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class CarServiceController extends EventController implements PriceController.OnPriceChangedListener

{
	private String TAG = getClass().getSimpleName();
	Activity activity;
	AlarmController timerController;
	DialogFragment newFragment;
	PriceController priceController;
	private TextView service_works;
	private EditText sum_price;

	private final int TSIZE = 25;

	String[] t_data = new String[TSIZE];


	public CarServiceController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout, LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		this.activity=activity;
		timerController = AlarmController.getInstance(activity);
		timerController.сancelAlarm();//cancel previous alarm
		title.setText(activity.getResources().getString(R.string.service));
		titleImage.setImageResource(R.drawable.eat_bottle_dialogtitle);
		priceController=new PriceController();
		priceController.setListener(this);
		service_works =(TextView)mainLayout.findViewById(R.id.service_works_but);
		service_works.setOnClickListener(serviceWorksButOnClickListener);
		sum_price=(EditText)commentLayout.findViewById(R.id.price_edit_text);
		sum_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(final View view, final boolean b) {
				priceController.setItemsPrice(Double.valueOf(sum_price.getText().toString()));
			}
		});
	}

	View.OnClickListener serviceWorksButOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
			// Create and show the dialog.
			newFragment = new ServiceWorksDialog();
			Bundle args = new Bundle();
			args.putSerializable("PriceController",priceController);
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
	public void onPriceChanged(final String price) {
		sum_price.setText(price);
	}

	public void clear(){
		priceController.setListener(null);
	}

}
