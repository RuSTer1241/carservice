package com.car.service.eventcontroller;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.car.service.R;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.model.CarServiceApplication;
import com.car.service.model.PreferenceEditor;
import com.car.service.utils.WLog;
import com.car.service.widget.wheel.WheelView;
import com.car.service.widget.wheel.adapter.ArrayWheelAdapter;
import com.car.service.widget.wheel.listener.OnWheelChangedListener;
import com.car.service.widget.wheel.listener.OnWheelScrollListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by r.savuschuk on 11/17/2014.
 */
public class WeightController extends EventController {


	private final String kg_unit_tx[] = new String[] {"  kg  "};
	private final String g_unit_tx[] = new String[] {"  g  "};
	private String rezultValue_kg = "";
	private String rezultValue_g = "";
	private boolean unit = true;//british by default
	private String kg_values[];// = new String[15];
	private String g_values[];// = new String[SIZE];
	private final int SIZE = 20;
	PreferenceEditor prefEditor;
	TextView quantity_label;

	public WeightController(Activity activity, RelativeLayout titleLayout, LinearLayout commentLayout, LinearLayout wheelLayout) {
		super(activity, titleLayout, commentLayout);
		prefEditor = CarServiceApplication.getPrefEditor();
		unit = prefEditor.getMeasureUnit();

		if (unit) {
			kg_unit_tx[0] = "  lb  ";
			g_unit_tx[0] = "  oz  ";
		} else {
			kg_unit_tx[0] = "  kg  ";
			g_unit_tx[0] = "  g  ";
		}

		valuesInit();
		initWheels(wheelLayout);
		title.setText(activity.getResources().getString(R.string.child_weight));
		titleImage.setImageResource(R.drawable.weight_dialogtitle);
		quantity_label = (TextView) wheelLayout.findViewById(R.id.quantity_view);
		quantity_label.setText(R.string.measure);
	}

	void initWheels(View v) {
		initValueWheel(v, R.id.wheel_kg_value, kg_values);
		updateValues(initTextWheel(v, R.id.wheel_kg_text), kg_unit_tx, 0);
		initValueWheel(v, R.id.wheel_g_value, g_values);
		updateValues(initTextWheel(v, R.id.wheel_g_text), g_unit_tx, 0);
	}

	/**
	 * Updates  wheels
	 */
	private void updateValues(WheelView v, String values[], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(activity, values);
		adapter.setTextSize(18);
		v.setViewAdapter(adapter);
		v.setCurrentItem(index);
		switch (v.getId()) {
			case R.id.wheel_kg_value:
				//if (index != 0) {
				if (unit) {
					rezultValue_kg = values[index] + "lb";
				} else {
					rezultValue_kg = values[index] + "kg";
				}
				/*} else {
					rezultValue_kg = "0";
				}*/
				break;
			case R.id.wheel_g_value:
				//if (index != 0) {
				if (unit) {
					rezultValue_g = values[index] + "oz";
				} else {
					rezultValue_g = values[index] + "g";
				}
				/*} else {
					rezultValue_g = "0";
				}*/
				break;
		}
	}

	/**
	 * Init value  wheels
	 */
	private void initValueWheel(View v, int id, final String[] values) {
		WheelView wheel = (WheelView) v.findViewById(id);
		wheel.setVisibleItems(5);
		wheel.setCurrentItem(1);
		wheel.setCyclic(true);

		updateValues(wheel, values, 1);
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!isScrolling()) {
					updateValues(wheel, values, newValue);
				}
			}
		});
		wheel.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				setScrolling(true);
			}

			public void onScrollingFinished(WheelView wheel) {
				setScrolling(false);
				updateValues(wheel, values, wheel.getCurrentItem());
			}
		});
	}

	/**
	 * Init text wheels
	 */
	private WheelView initTextWheel(View v, int id) {
		WheelView wheel_kg_v = (WheelView) v.findViewById(id);
		wheel_kg_v.setVisibleItems(5);
		wheel_kg_v.setCurrentItem(0);
		return wheel_kg_v;
	}

	/**
	 * Init wheels values arrays
	 */
	private void valuesInit() {
		int firstWheelSize = 0;
		int thirdWheelSize = 0;
		int mult = 0;
		if (unit) {
			firstWheelSize = 30;//for british
			thirdWheelSize = 16;//1 lb=16 oz
			mult = 1;
		} else {
			firstWheelSize = 15;//for metric
			thirdWheelSize = SIZE;
			mult = 50;
		}
		kg_values = new String[firstWheelSize];
		g_values = new String[thirdWheelSize];
		for (int i = 0; i < firstWheelSize; i++) {
			kg_values[i] = "  " + i + "  ";
		}
		for (int i = 0; i < thirdWheelSize; i++) {
			g_values[i] = "  " + i * mult + "  ";
		}
	}

	private String getWeightSum() {
		return rezultValue_kg.concat(rezultValue_g);
	}

	@Override
	public boolean saveEventToDb() {
		boolean ret = false;
		final String sum = getWeightSum();
		String re1 = "(\\d+)";    // Integer Number 1
		String re2 = ".*?";    // Non-greedy match on filler
		String re3 = "(\\d+)";    // Integer Number 2

		Pattern p = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(sum);
		if (m.find()) {

			if (m.group(1).equals("0") && m.group(2).equals("0")) {
				Toast.makeText(activity, activity.getResources().getString(R.string.set_weight_msg), Toast.LENGTH_SHORT).show();
				ret = false;
			} else {
				engine.dbWriteRequest(DbEngine.Action.WEIGHT, sum, " " + comment.getText().toString(), new DbEngine.Callback<Long>() {
					@Override
					public void onSuccess(final Long data) {
						WLog.e("DbEngine", " OnSuccess");
					}

					@Override
					public void onFail(final DbError error) {
						WLog.e("DbEngine", " OnFail");
						Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
					}
				});
				ret = true;
			}
		}
		return ret;
	}
}
