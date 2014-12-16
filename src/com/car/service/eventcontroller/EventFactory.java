package com.car.service.eventcontroller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.car.service.R;

/**
 * Created by r.savuschuk on 11/18/2014.
 * factory which create event Dialogs
 */
public class EventFactory {
	Activity activity;
	RelativeLayout title_area;
	LinearLayout main_area;
	LinearLayout comment_area;
	ViewGroup container;
	LayoutInflater li;


	public EventFactory(Activity context, RelativeLayout title_area, LinearLayout main_area, LinearLayout comment_area, ViewGroup container) {
		this.activity = context;
		this.title_area = title_area;
		this.main_area = main_area;
		this.comment_area = comment_area;
		this.container = container;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * This method creates event dialog based on eventKey information
	 * @param eventKey Key, which identify dialog type
	 * @return
	 */
	public EventController createEvent(int eventKey) {
		EventController eventController = null;
		switch (eventKey) {
			case 0://eat
				main_area.removeAllViewsInLayout();
				main_area.addView(li.inflate(R.layout.eat_quantity_spinner, container, false));
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new CarServiceController(activity, title_area, comment_area, main_area);
				break;
			case 1://crap
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new CrapController(activity, title_area, comment_area);
				break;

			case 2://pee
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new PeeController(activity, title_area, comment_area);
				break;
			case 3://temperature
				main_area.removeAllViewsInLayout();
				main_area.addView(li.inflate(R.layout.quantity_spinner_layout, container, false));
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new TemperatureController(activity, title_area, comment_area, main_area);
				break;

			case 4://weight
				main_area.removeAllViewsInLayout();
				main_area.addView(li.inflate(R.layout.quantity_wheel_layout, container, false));
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new WeightController(activity, title_area, comment_area, main_area);
				break;
			case 5:
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new CommentController(activity, title_area, comment_area);
				break;

			default://temporary decision
				main_area.removeAllViewsInLayout();
				main_area.addView(li.inflate(R.layout.quantity_wheel_layout, container, false));
				comment_area.addView(li.inflate(R.layout.comment_price_layout, container, false));
				eventController = new WeightController(activity, title_area, comment_area, main_area);
				break;
		}

		return eventController;
	}
}
