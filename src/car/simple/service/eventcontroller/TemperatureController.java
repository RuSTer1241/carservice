package car.simple.service.eventcontroller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.baby.observer.R;
import car.simple.service.SpinnerAdapter;
import car.simple.service.database.DbEngine;
import car.simple.service.database.DbError;
import car.simple.service.utils.WLog;

/**
 * Created by r.savuschuk on 11/20/2014.
 */
public class TemperatureController extends EventController {
	TextView quantity_label;
	private Spinner temperature_spinner;
	private final int SIZE = 61;
	String[] data = new String[SIZE];

	public TemperatureController(final Activity activity, final RelativeLayout titleLayout, final LinearLayout commentLayout,
		LinearLayout mainLayout) {
		super(activity, titleLayout, commentLayout);
		temperature_spinner = (Spinner) mainLayout.findViewById(R.id.quantity_spinner);
		quantity_label = (TextView) mainLayout.findViewById(R.id.quantity_view);
		quantity_label.setText(R.string.measure);
		spinnerDataInit();
		SpinnerAdapter adapter = new SpinnerAdapter(activity, android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		temperature_spinner.setAdapter(adapter);
		temperature_spinner.setPrompt("Title");
		temperature_spinner.setSelection(prefEditor.getTemperPos());
		temperature_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getActivity().getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		setTitle(activity.getResources().getString(R.string.temperature));
		setTitleImage(R.drawable.termometer_dialogtitle);
	}

	@Override
	public boolean saveEventToDb() {
		engine.dbWriteRequest(DbEngine.Action.TEMPERATURE, temperature_spinner.getSelectedItem().toString(), comment.getText().toString(),
			new DbEngine.Callback<Long>() {
				@Override
				public void onSuccess(final Long data) {
					prefEditor.saveTemperPos(temperature_spinner.getSelectedItemPosition());
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

	private void spinnerDataInit() {

		Float j = 34.0F;
		int i;
		for (i = 0; i < SIZE; i++) {
			data[i] = String.format("%.1f", j);
			j = j + 0.1F;
		}
	}
}
