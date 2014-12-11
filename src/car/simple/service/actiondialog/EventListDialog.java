package car.simple.service.actiondialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import car.simple.service.Const;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.database.DbEngine;
import car.simple.service.listmode.ListModeController;

/**
 * Created by r.savuschuk on 11/21/2014.
 */
public class EventListDialog extends DialogFragment {
	TextView title;
	DbEngine engine = CarServiceApplication.getDbEngine();
	ListModeController controller;
	private ListView listview;
	EventItemAdapter adapter;

	public EventListDialog(ListModeController controller) {
		this.controller =controller;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new EventItemAdapter(getActivity().getBaseContext(), CarServiceApplication.getEventManager().readAdditionEvents());//need to init itemdatalist

	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v= inflater.inflate(R.layout.event_list_dialog, null);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		title=(TextView)v.findViewById(R.id.event_list_title);
		this.title.setText("Please, select event");
		listview = (ListView)v.findViewById(R.id.eventnames_list);
		initListView();

		return v;
	}

	private void  initListView(){
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				engine.dbReadRequest(DbEngine.Mode.LIST, position+ Const.HARDCODED_EVENTS_NUM,
					CarServiceApplication.getToTime(), controller);
				dismiss();

			}
		});


	}
}
