package car.simple.service.graphmode;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import car.simple.service.BaseFragment;
import car.simple.service.model.CarServiceApplication;
import com.baby.observer.R;
import car.simple.service.database.DbEngine;
import car.simple.service.database.DbError;
import car.simple.service.listmode.ItemModel;
import car.simple.service.utils.WLog;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.List;

/**
 * Created by r.savuschuk on 9/12/2014.
 * https://code.google.com/p/achartengine/
 */
public class GraphFragment extends BaseFragment implements DbEngine.Callback<List<ItemModel>>
{
	GraphDataProcessor graphDataProcessor;
	DbEngine engine;
	GraphView graphView;
	ProgressDialog progress;


		@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		engine= CarServiceApplication.getDbEngine();
		graphDataProcessor =new GraphDataProcessor(engine);
		progress= new ProgressDialog(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		graphView = new LineGraphView(this.getActivity(), "Title");
		graphView.setHorizontalLabels(new String[] {activity.getResources().getString(R.string.today),
													activity.getResources().getString(R.string.yesterday),
													activity.getResources().getString(R.string.early)});
        graphView.getGraphViewStyle().setNumVerticalLabels(8);
		graphView.getGraphViewStyle().setGridColor(activity.getResources().getColor(R.color.listfragment_background));
		graphView.getGraphViewStyle().setHorizontalLabelsColor(activity.getResources().getColor(R.color.listfragment_background));
		graphView.getGraphViewStyle().setVerticalLabelsColor(activity.getResources().getColor(R.color.listfragment_background));
		graphView.setVisibility(View.GONE);

		activity.setGraphMode();
		replaceContent(activity.getCurrentGraphTab());
		return graphView;
	}

	@Override
	public void onSuccess(final List<ItemModel> data) {
		WLog.e("DbEngine", " OnSuccess");
		graphView.removeAllSeries();
		graphView.setVisibility(View.VISIBLE);
		try {//data.get(0) can be zero if db empty
			switch (data.get(0).getActionType()) {
				case EAT:
					graphView.setTitle(activity.getResources().getString(R.string.eat_short));
					addSerie(activity.getResources().getColor(R.color.eat_color), graphDataProcessor.initEatData(data));
					break;

				case TEMPERATURE:
					graphView.setTitle(activity.getResources().getString(R.string.temperature));
					addSerie(activity.getResources().getColor(R.color.temperature_color), graphDataProcessor.initTemperatureData(data));
					break;

				case WEIGHT:
					String unit_txt = "";
					if (activity.prefEditor.getMeasureUnit()) unit_txt = " (lb)";
					else unit_txt = " (kg)";
					graphView.setTitle(activity.getResources().getString(R.string.weight) + unit_txt);
					addSerie(activity.getResources().getColor(R.color.weight_color), graphDataProcessor.initWeightData(data));
					break;
			}
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			graphView.setVisibility(View.GONE);
			Toast.makeText(CarServiceApplication.getAppContext(), R.string.empty_category, Toast.LENGTH_SHORT).show();
		}
		progress.dismiss();
	}

	@Override
	public void onFail(final DbError error) {
		WLog.e("DbEngine", " OnFail");
		Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
		progress.dismiss();
	}

	public void  replaceContent(int type) {
		engine.dbReadRequest(DbEngine.Mode.GRAPH,type,0L,this);
        openProgress();
	}

    private void addSerie(int color,GraphView.GraphViewData[] array){
	    if (array.length > 1)
		    graphView.addSeries(new GraphViewSeries("", new GraphViewSeries.GraphViewSeriesStyle(color, 3),array));

	    else {
             graphView.setVisibility(View.GONE);
		     Toast.makeText(CarServiceApplication.getAppContext(), activity.getResources().getString(R.string.sel_other_interval), Toast.LENGTH_SHORT)
			    .show();
	    }

	}
	private void openProgress(){
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.show();
		progress.setContentView(R.layout.progress);
	}


}

