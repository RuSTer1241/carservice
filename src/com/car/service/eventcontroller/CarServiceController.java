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
import android.widget.Toast;
import com.car.service.R;
import com.car.service.eventcontroller.carserviceworks.ServiceWorkItemModel;
import com.car.service.eventcontroller.carserviceworks.ServiceWorksDialog;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.utils.WLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.Serializable;
import java.util.List;

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
	private EditText odometer;
	String checkedItemsStr;

	public CarServiceController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout, LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		this.activity=activity;
		title.setText(activity.getResources().getString(R.string.service));
		titleImage.setImageResource(R.drawable.service_dialogtitle);
		service_works =(TextView)mainLayout.findViewById(R.id.service_works_but);
		service_works.setOnClickListener(serviceWorksButOnClickListener);
		sum_price=(EditText)commentLayout.findViewById(R.id.price_edit_text);
		odometer=(EditText)commentLayout.findViewById(R.id.odometr_edit_text);

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
		engine.dbWriteRequest(DbEngine.Action.SERVICE, sum_price.getText().toString(), comment.getText().toString(), odometer.getText().toString(),
			checkedItemsStr,null, new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data,Double sumPrice) {
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

	@Override
	public void onDataSaved(final String price, final List<ServiceWorkItemModel> checkedList) {
		sum_price.setText(price);
		JSONObject mainobj = new JSONObject();
		JSONArray array= new JSONArray();
		try {
		for (ServiceWorkItemModel item : checkedList) {
			    JSONObject obj = new JSONObject();
			    obj.put("id",item.getId());
				obj.put("name",item.getWorkName());
				obj.put("price",item.getPrice());
			    array.put(obj);
		}
			mainobj.put("service_list", array);
	} catch (JSONException e) {
		e.printStackTrace();
	}
		checkedItemsStr = mainobj.toString();

	}



}
