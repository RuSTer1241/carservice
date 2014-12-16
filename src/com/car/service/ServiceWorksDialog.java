package com.car.service;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.car.service.database.DbEngine;
import com.car.service.eventcontroller.CarServiceController;
import com.car.service.model.CarServiceApplication;

/**
 * Created by r.savuschuk on 12/12/2014.
 */
public class ServiceWorksDialog extends DialogFragment implements View.OnClickListener{

	DbEngine engine;
	Context activityContext;
	private TextView sum_price;
	private ListView listview;
	Button saveButton;
	Button cancelButton;
	ServiceItemsController worksItemController;
	private CarServiceController carServiceController;


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		engine= CarServiceApplication.getDbEngine();
		activityContext=getActivity().getBaseContext();
		try {
			carServiceController = (CarServiceController)getArguments().getSerializable("CarServiceController");
		}
		catch (final ClassCastException e) {
			throw new ClassCastException(this.toString() + " must transfer CarServiceController object");
		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.service_components_dialog, container, false);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		saveButton = (Button) v.findViewById(R.id.serv_save);
		saveButton.setOnClickListener(this);
		cancelButton = (Button) v.findViewById(R.id.serv_cancel);
		cancelButton.setOnClickListener(this);
		sum_price=(TextView)v.findViewById(R.id.serv_summary_price);
		worksItemController=new ServiceItemsController(this.getActivity(),sum_price);
		listview = (ListView)v.findViewById(R.id.service_component_list);
		listview.setAdapter(worksItemController.getWorksAdapter());
		return v;
	}

	@Override
	public void onClick(final View v) {


		switch (v.getId()) {
			case R.id.serv_cancel:
				dismiss();
				break;
			case R.id.serv_save:
				carServiceController.onDataSaved(sum_price.getText().toString());
				dismiss();
				break;

		}
		worksItemController.clear();
	}

	public interface OnDialogDataSavedListener {
		void onDataSaved(String price);
	}




}

