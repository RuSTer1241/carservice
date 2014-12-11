package car.simple.service;

import car.simple.service.actiondialog.ActionBaseDialog;
import car.simple.service.model.CarServiceApplication;
import car.simple.service.model.PreferenceEditor;
import car.simple.service.utils.WLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class which manage events{eat,weight,temperature etc}
 * Init default order,and reinit order after user request
 * Created by r.savuschuk on 11/19/2014.
 */
public class EventManager {
	private String TAG = getClass().getSimpleName();
	public static int EVENTS_SIZE = 9;
	private PreferenceEditor prefEditor;
	private HashMap<Integer, Event> events;


	public EventManager(long launchNum) {
		prefEditor = CarServiceApplication.getPrefEditor();
		events = new HashMap<Integer, Event>();
		//if (launchNum == 1) {
			writeDefaultEvents();//it is need in app first launch or in app update from prev version.
		//}
		initEventsContainer();
	}

	private void writeDefaultEvents() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("0", "EAT");
			obj.put("1", "CRAP");
			obj.put("2", "PEE");
			obj.put("3", "TEMPERATURE");
			obj.put("4", "WEIGHT");
			obj.put("5", "COMMENT");
			/*obj.put("6", "TOOTH");
			obj.put("7", "GROW");
			obj.put("8", "VACCINATION");*/

			prefEditor.saveEvent(obj.toString(0));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initEventsContainer() {
		prefEditor.getEvents();
		try {
			JSONObject obj = new JSONObject(prefEditor.getEvents());
			for (int i = 0; i < EVENTS_SIZE; i++) {
				events.put(i, new Event(obj.getString(Integer.toString(i)), i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			WLog.e(TAG, "WRONG EVENTS_SIZE");
		}
	}

	public HashMap<Integer, Event> getEvents() {
		return events;
	}

	/**
	 * copy constructor(events copied in constructor)
	 */
	public HashMap<Integer, Event> readEvents() {
		return new HashMap<Integer, Event>(events);
	}
	/**
	 * events copied in new collection)
	 */
	public ArrayList<Event> readAdditionEvents() {
		ArrayList<Event>eventNames = new ArrayList<Event>();
		for (Integer key : events.keySet()) {
			if(key>= ActionBaseDialog.HARDCODED_EVENTS_NUM)
				eventNames.add(events.get(key));
		}
		return eventNames;
	}
}
