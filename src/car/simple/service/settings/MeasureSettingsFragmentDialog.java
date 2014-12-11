package car.simple.service.settings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.model.PreferenceEditor;

/**
 * Created by r.savuschuk on 9/24/2014.
 */
public class MeasureSettingsFragmentDialog extends DialogFragment {


	Button save;
	Button cancel;
	RadioGroup measure_unit;
	RadioButton metric;
	RadioButton british;
	PreferenceEditor prefEditor;

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefEditor= CarServiceApplication.getPrefEditor();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.measure_settings, container, false);
		getDialog().setTitle(R.string.action_measure_unit);
		metric=(RadioButton)v.findViewById(R.id.metric);
		british=(RadioButton)v.findViewById(R.id.british);
		measure_unit=(RadioGroup)v.findViewById(R.id.measure_unit);
		save=(Button)v.findViewById(R.id.settingdialog_save);
		cancel=(Button)v.findViewById(R.id.settingdialog_cancel);
		if(prefEditor.getMeasureUnit())
			british.toggle();
		else
		    metric.toggle();

		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(final View v) {
              if(measure_unit.getCheckedRadioButtonId()==R.id.metric)
	              prefEditor.saveMeasureUnit(false);
				else
	              prefEditor.saveMeasureUnit(true);
				dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				dismiss();
			}
		});
		return v;
	}


}