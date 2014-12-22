package com.car.service.listmode;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.car.service.R;
import com.car.service.eventcontroller.carserviceworks.ItemDetailDialog;
import com.car.service.utils.WLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 8/22/2014.
 */
public class ItemBaseAdapter extends BaseAdapter {
	private String TAG = getClass().getSimpleName();
	//CommonPriceListener priceListener;
	private List<ItemModel> values = new ArrayList<ItemModel>();
	private final Activity activity;
	//Double price=new Double(0);


	/*public void setValues(final List<ItemModel> values) {
		clear();
		this.values = values;
		notifyDataSetChanged();
	}*/

	public void addValues(final List<ItemModel> values) {
		this.values.addAll(values);
		notifyDataSetChanged();
	}

	public ItemBaseAdapter(Activity activity, List<ItemModel> values) {
		this.activity = activity;
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
		WLog.e(TAG, "getView " + mediaModel.getData());

		if (convertView == null) {

			holder = new ViewHolder();
			rowView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.statistic_list_item, null);
			holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
			holder.textViewFistLine = (TextView) rowView.findViewById(R.id.firstLine);
			holder.textViewSecondLine = (TextView) rowView.findViewById(R.id.secondLine);
			holder.textViewSecondLine.setEllipsize(TextUtils.TruncateAt.END);
			holder.detail_but = (ImageView) rowView.findViewById(R.id.item_detail);


			WLog.e(TAG, "initItem ");
			rowView.setTag(holder);
		} else {
			WLog.e(TAG, "reuseItem ");
			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}
		holder.textViewSecondLine.setText("");//clear
		holder.textViewFistLine.setText(mediaModel.getData());
		switch (values.get(position).getActionType()) {
			case SERVICE:
				holder.imageView.setImageResource(R.drawable.eat_bottle_item);
				if (!mediaModel.getPrice().isEmpty()) {
					holder.textViewSecondLine.setText(activity.getResources().getString(R.string.quantity) + mediaModel.getPrice());
				}
				break;
			case FUEL:
				holder.imageView.setImageResource(R.drawable.crap_item);
				break;
			case ADMIN:
				holder.imageView.setImageResource(R.drawable.pee_item);
				break;
			case COMMENT:
				holder.imageView.setImageResource(R.drawable.comment_item);
				break;
		}
		if (mediaModel.getItemServicesList().size() > 0) {
			WLog.e(TAG, "serviceslist.size>0 ");
			holder.detail_but.setVisibility(View.VISIBLE);
			holder.detail_but.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View view) {
					FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
					DialogFragment newFragment = new ItemDetailDialog(activity, mediaModel.getItemServicesList());
					newFragment.show(ft, "ItemDetailDialog");
				}
			});
		} else {
			WLog.e(TAG, "serviceslist.size=0 ");
			holder.detail_but.setVisibility(View.INVISIBLE);
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
		ImageView detail_but;
	}

	public void clear() {
		this.values.clear();
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		this.values.remove(position);
		notifyDataSetChanged();
	}

	/*public void setPriceListener(final CommonPriceListener priceListener) {
		this.priceListener = priceListener;
	}*/


	/*public interface CommonPriceListener {
		public void OnCommonPriceChanged(String price);


	}*/
}
