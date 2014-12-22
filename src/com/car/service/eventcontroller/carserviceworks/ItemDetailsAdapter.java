package com.car.service.eventcontroller.carserviceworks;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.car.service.R;
import com.car.service.eventcontroller.carserviceworks.ServiceWorkItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 12/12/2014.
 */
public class ItemDetailsAdapter extends BaseAdapter {
	private String TAG = getClass().getSimpleName();
	private List<ServiceWorkItemModel> dataList = new ArrayList<ServiceWorkItemModel>();

	private Activity activity;

	public ItemDetailsAdapter(Activity activity, List<ServiceWorkItemModel> dataList) {
		this.activity = activity;
		this.dataList.addAll(dataList);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(final int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final View rowView;
		final ViewHolder holder;
		final ServiceWorkItemModel mediaModel = dataList.get(position);
		if (convertView == null) {
			rowView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.details_list_item, null);
			holder = new ViewHolder();
			holder.textViewFirstLine = (TextView) rowView.findViewById(R.id.detail_item_name);
			holder.workPrice = (TextView) rowView.findViewById(R.id.detail_item_price);
			holder.textViewFirstLine.setEllipsize(TextUtils.TruncateAt.END);
			rowView.setTag(holder);
		} else {
			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}
		holder.textViewFirstLine.setText(mediaModel.getWorkName());
		holder.workPrice.setText("Price: " + mediaModel.getPriceAsStr());
		return rowView;
	}


	public static class ViewHolder {
		TextView textViewFirstLine;
		TextView workPrice;
	}
}


