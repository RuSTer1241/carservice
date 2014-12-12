package com.car.service.actiondialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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
	private Spinner event_spinner;
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
		eventFactory = new EventFactory(this.getActivity(), title_area, q_area, c_area, container);

	//	if (eventKey < HARDCODED_EVENTS_NUM) {
			eventController = eventFactory.createEvent(eventKey);
	//	} else {
			/*q_area.addView(inflater.inflate(R.layout.event_selector, container, false));
			event_spinner = (Spinner) v.findViewById(R.id.event_spinner);
			SpinnerAdapter e_adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, eventNames);
			e_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			event_spinner.setAdapter(e_adapter);
			event_spinner.setPrompt("Title");

			event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					Toast.makeText(getActivity().getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
					if(position>0)
						eventController = eventFactory.createEvent(position+HARDCODED_EVENTS_NUM);

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			setTitle(getActivity().getResources().getString(R.string.event));
			setTitleImage(R.drawable.event_dialogtitle);*/

	//	}

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