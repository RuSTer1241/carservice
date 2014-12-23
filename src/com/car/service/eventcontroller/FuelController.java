package com.car.service.eventcontroller;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.car.service.R;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.utils.WLog;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class FuelController extends EventController {
	private EditText quantity;
	private EditText sum_price;
	private EditText odometer;
	public FuelController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout,final LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		setTitle(activity.getResources().getString(R.string.fuel));
		setTitleImage(R.drawable.fuel_dialogtitle);
		quantity=(EditText)mainLayout.findViewById(R.id.quantity_edit_text);
		sum_price=(EditText)commentLayout.findViewById(R.id.price_edit_text);
		odometer=(EditText)commentLayout.findViewById(R.id.odometr_edit_text);
	}

	@Override
	public boolean saveEventToDb() {
				engine.dbWriteRequest(DbEngine.Action.FUEL, sum_price.getText().toString(), comment.getText().toString(),odometer.getText().toString(),
				null,quantity.getText().toString(),
				new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data,Double sumPrice ) {
					WLog.e("DbEngine", " OnSuccess");
				}

				@Override
				public void onFail(final DbError error) {
					WLog.e("DbEngine", " OnFail");
					Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
				}
			});
		return true;
	}
}
