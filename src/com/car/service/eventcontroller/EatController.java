package com.car.service.eventcontroller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.car.service.R;
import com.car.service.SpinnerAdapter;
import com.car.service.alarms.AlarmController;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.utils.WLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class EatController extends EventController {
	private String TAG = getClass().getSimpleName();
	AlarmController timerController;
	private Spinner q_spinner;
	private Spinner t_spinner;
	private final int QSIZE = 29;
	private final int TSIZE = 25;
	String[] q_data = new String[QSIZE];
	String[] t_data = new String[TSIZE];

	public EatController(Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout, LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		timerController = AlarmController.getInstance(activity);
		timerController.сancelAlarm();//cancel previous alarm

		/*setTitle(activity.getResources().getString(R.string.child_eat));
		setTitleImage(R.drawable.eat_bottle_dialogtitle);*/
		title.setText(activity.getResources().getString(R.string.child_eat));
		titleImage.setImageResource(R.drawable.eat_bottle_dialogtitle);
		q_spinner = (Spinner) mainLayout.findViewById(R.id.quantity_spinner);
		quantitySpinnerDataInit();
		SpinnerAdapter q_adapter = new SpinnerAdapter(activity, android.R.layout.simple_spinner_item, q_data);
		q_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		q_spinner.setAdapter(q_adapter);
		q_spinner.setPrompt("Title");
		q_spinner.setSelection(prefEditor.getEatQuantity());
		q_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getActivity().getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		t_spinner = (Spinner) mainLayout.findViewById(R.id.timer_spinner);
		timerSpinnerDataInit();
		SpinnerAdapter t_adapter = new SpinnerAdapter(activity, android.R.layout.simple_spinner_item, t_data);
		t_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		t_spinner.setAdapter(t_adapter);
		t_spinner.setPrompt("Title");
		t_spinner.setSelection(prefEditor.getTimerQuantity());
		t_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getActivity().getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public boolean saveEventToDb() {
		engine
			.dbWriteRequest(DbEngine.Action.EAT, q_spinner.getSelectedItem().toString(), comment.getText().toString(), new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data) {
					WLog.e("DbEngine", " OnSuccess");
					prefEditor.saveEatQuantity(q_spinner.getSelectedItemPosition());
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

		return true;
	}

	private void quantitySpinnerDataInit() {
		Integer j = 30;
		q_data[0] = "0 ml";
		q_data[1] = "5 ml";
		q_data[2] = "10 ml";
		q_data[3] = "15 ml";
		q_data[4] = "20 ml";
		int i;
		for (i = 5; i < QSIZE; i++) {
			q_data[i] = String.format("%d ml", j);
			j = j + 10;
		}
	}

	private void timerSpinnerDataInit() {
		Integer j = 15;
		t_data[0] = activity.getResources().getString(R.string.notneed);// "not need";
		int i;
		for (i = 1; i < TSIZE; i++) {
			if (j < 60) {
				t_data[i] = String.format("%d min", j);
			}
			if (j >= 60) {
				if (j % 60 == 0) {
					t_data[i] = String.format("%d hour", j / 60);
				} else {
					t_data[i] = String.format("%d hour %d min", j / 60, j % 60);
				}
			}

			j = j + 15;
		}
	}

	/**
	 * return time in mills , which leave before eat timer start
	 */
	private long timeParse(String time_str) {
		Pattern p;
		long time = 0;
		long out_v = 0;

		String re1 = "(\\d+)";    // Integer Number 1
		String re2 = ".*?";    // Non-greedy match on filler
		String re3 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 1
		String re4 = ".*?";    // Non-greedy match on filler
		String re5 = "(\\d+)";    // Integer Number 2
		String re6 = ".*?";    // Non-greedy match on filler
		String re7 = "((?:[a-z][a-z0-9_]*))";    // Variable Name 2

		if (time_str.contains("hour") && time_str.contains("min")) {
			p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		} else {
			p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		}
		Matcher m = p.matcher(time_str);
		if (m.find()) {
			if (m.group(2).equals("min")) {
				time = (Integer.valueOf(m.group(1)) * 60);
			}
			if (m.group(2).equals("hour")) {
				time = (Integer.valueOf(m.group(1)) * 3600);
				if (m.groupCount() > 2) {
					time += (Integer.valueOf(m.group(3))) * 60;
				}
			}
			WLog.d(TAG, "time= " + time);
			out_v =/* engine.getDate() +*/ time * 1000;//time in mills
		} else {
			out_v = 0;
		}
		//return 20000;// for tests only
		return out_v;
	}
}
