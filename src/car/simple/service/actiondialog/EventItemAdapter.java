package car.simple.service.actiondialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import car.simple.service.Event;
import com.baby.observer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 11/21/2014.
 */
public class EventItemAdapter extends BaseAdapter {
	private List<Event> values;// = new ArrayList<Event>();
	private final Context context;

	public void setValues(final List<Event> values) {
		clear();
		this.values = values;
		notifyDataSetChanged();
	}

	public void addValues(final List<Event> values) {
		this.values.addAll(values);
		notifyDataSetChanged();
	}

	public EventItemAdapter(Context context,ArrayList<Event> values) {
		this.context = context;
		this.values=values;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(final int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final View rowView;
		final ViewHolder holder;
		final Event event = values.get(position);
		if (convertView == null) {
			rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.event_item, null);
			holder = new ViewHolder();

			holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
			holder.eventName = (TextView) rowView.findViewById(R.id.eventline);
			rowView.setTag(holder);
		} else {
			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}
		holder.eventName.setText(event.getName());
		switch (ActionBaseDialog.HARDCODED_EVENTS_NUM+position) {
			case 0:
				holder.imageView.setImageResource(R.drawable.eat_bottle_item);
				break;
			case 1:
				holder.imageView.setImageResource(R.drawable.crap_item);
				break;
			case 2:
				holder.imageView.setImageResource(R.drawable.pee_item);
				break;
			case 3:
				holder.imageView.setImageResource(R.drawable.termometer_item);
				break;
			case 4:
				holder.imageView.setImageResource(R.drawable.weight_item);
				break;
			case 5:
				holder.imageView.setImageResource(R.drawable.weight_item);
				break;
			case 6:
				holder.imageView.setImageResource(R.drawable.weight_item);
				break;
			case 7:
				holder.imageView.setImageResource(R.drawable.weight_item);
				break;
			case 8:
				holder.imageView.setImageResource(R.drawable.weight_item);
				break;
		}


		return rowView;
	}

	public static class ViewHolder {
		TextView eventName;
		ImageView imageView;
	}

	public void clear() {
		this.values.clear();
		notifyDataSetChanged();
	}


}
