package com.car.service.actiondialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.car.service.R;
import com.car.service.eventcontroller.EventController;
import com.car.service.eventcontroller.EventFactory;

/**
 * Base dialog for creation all events dialogs
 * It is contains button area and logic for events selection
 * Created by r.savuschuk on 9/4/2014.
 */
public class EventDialog extends ActionBaseDialog implements View.OnClickListener {

	EventController eventController;
	Button saveButton;
	Button cancelButton;
	EventFactory eventFactory;
	Integer eventKey;

	public EventDialog(final Integer eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		b_area.addView(inflater.inflate(R.layout.button_layout, container, false));
		eventFactory = new EventFactory(this.getActivity(), title_area, q_area, cprice_area, container);

			eventController = eventFactory.createEvent(eventKey);


		saveButton = (Button) v.findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		cancelButton = (Button) v.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(final View v) {
		if (eventController != null) {
			if (!eventController.isScrolling()) {
				switch (v.getId()) {
					case R.id.cancel:
						dismiss();
						break;
					case R.id.save:
						if (eventController.saveEventToDb()) {
							dismiss();
						}
						break;
				}
			}
		}
	}

}