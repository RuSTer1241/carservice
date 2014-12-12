package com.car.service.actiondialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.car.service.Event;
import com.car.service.R;
import com.car.service.database.DbEngine;
import com.car.service.model.CarServiceApplication;

import java.util.HashMap;

/**
 * Created by r.savuschuk on 8/27/2014.
 */
public class ActionBaseDialog extends DialogFragment {
	public static int HARDCODED_EVENTS_NUM=5;// how much events hard linked to user buttons

	DbEngine engine;
	LinearLayout q_area;
	LinearLayout c_area;
	RelativeLayout b_area;
	RelativeLayout title_area;
	TextView title;
	ImageView titleImage;
	Context activityContext;
	HashMap<Integer,Event> events;
	String [] eventNames;


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		engine= CarServiceApplication.getDbEngine();
		activityContext=getActivity().getBaseContext();
		events= CarServiceApplication.getEventManager().readEvents();

		eventNames = new String[events.size()-HARDCODED_EVENTS_NUM];
		for (Integer key : events.keySet()) {
			if(key>=HARDCODED_EVENTS_NUM)
				eventNames[key-HARDCODED_EVENTS_NUM]=events.get(key).getName();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.base_dialog, container, false);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		title_area=(RelativeLayout)v.findViewById(R.id.title_area);
		q_area=(LinearLayout)v.findViewById(R.id.quantity_area);
		c_area=(LinearLayout)v.findViewById(R.id.comment_area);
		b_area=(RelativeLayout)v.findViewById(R.id.button_area);

		title=(TextView)v.findViewById(R.id.title);
		titleImage=(ImageView)v.findViewById(R.id.dialog_icon);

		return v;
	}

	public void setTitleImage(int titleImage) {
		this.titleImage.setImageResource(titleImage);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
}
