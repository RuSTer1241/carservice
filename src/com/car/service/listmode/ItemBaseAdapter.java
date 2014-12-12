package com.car.service.listmode;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.car.service.R;
import com.car.service.utils.WLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 8/22/2014.
 */
public class ItemBaseAdapter extends BaseAdapter {
	private List<ItemModel> values = new ArrayList<ItemModel>();
	private final Context context;

	public void setValues(final List<ItemModel> values) {
		clear();
		this.values = values;
		notifyDataSetChanged();
	}

	public void addValues(final List<ItemModel> values) {
		this.values.addAll(values);
		notifyDataSetChanged();
	}

	public ItemBaseAdapter(Context context, List<ItemModel> values) {
		this.context = context;
		for (ItemModel value : values) {
			this.values.add(value);
		}
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
		final ItemModel mediaModel = values.get(position);
		if (convertView == null) {
			rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, null);
			holder = new ViewHolder();

			holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
			holder.textViewFistLine = (TextView) rowView.findViewById(R.id.firstLine);
			holder.textViewSecondLine = (TextView) rowView.findViewById(R.id.secondLine);
			holder.textViewSecondLine.setEllipsize(TextUtils.TruncateAt.END);
			rowView.setTag(holder);
		} else {
			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}
		holder.textViewSecondLine.setText("");//clear
		holder.textViewFistLine.setText(mediaModel.getData());
		switch (values.get(position).getActionType()) {
			case EAT:
				holder.imageView.setImageResource(R.drawable.eat_bottle_item);
				if (!mediaModel.getQuantity().isEmpty()) {
					holder.textViewSecondLine.setText(context.getResources().getString(R.string.quantity) + mediaModel.getQuantity());
				}
				break;
			case KA:
				holder.imageView.setImageResource(R.drawable.crap_item);
				break;
			case PE:
				holder.imageView.setImageResource(R.drawable.pee_item);
				break;
			case TEMPERATURE:
				holder.imageView.setImageResource(R.drawable.termometer_item);
				if (!mediaModel.getQuantity().isEmpty()) {
					holder.textViewSecondLine.setText(context.getResources().getString(R.string.temperature) + " " + mediaModel.getQuantity());
				}
				break;
			case WEIGHT:
				holder.imageView.setImageResource(R.drawable.weight_item);
				if (!mediaModel.getQuantity().isEmpty()) {
					holder.textViewSecondLine.setText(context.getResources().getString(R.string.weight) + mediaModel.getQuantity());
				}
				break;
			case COMMENT:
				holder.imageView.setImageResource(R.drawable.comment_item);
				break;
		}

		if (!mediaModel.getComment().isEmpty()) {
			holder.textViewSecondLine.append(" " + mediaModel.getComment());
			WLog.e("COMMENT", mediaModel.getComment());
		}
		return rowView;
	}


	public static class ViewHolder {
		TextView textViewFistLine;
		TextView textViewSecondLine;
		ImageView imageView;
	}

	public void clear() {
		this.values.clear();
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		this.values.remove(position);
		notifyDataSetChanged();
	}
}
