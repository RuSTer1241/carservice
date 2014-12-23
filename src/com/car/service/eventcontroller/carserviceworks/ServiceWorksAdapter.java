package com.car.service.eventcontroller.carserviceworks;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.car.service.R;
import com.car.service.utils.WLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 12/12/2014.
 */
public class ServiceWorksAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
	private String TAG = getClass().getSimpleName();
	private List<ServiceWorkItemModel> dataList;

	private Activity context;
	private PriceChangeListener priceListener;

	public ServiceWorksAdapter(Activity context,ArrayList<ServiceWorkItemModel> dataList) {
		this.context = context;
		this.dataList=dataList;
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
			WLog.d(TAG, "create row " +position);
			rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.service_work_item, null);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);
			holder.textViewFirstLine = (TextView) rowView.findViewById(R.id.workname);
			holder.workPrice = (EditText) rowView.findViewById(R.id.service_work_price);
			holder.textViewFirstLine.setEllipsize(TextUtils.TruncateAt.END);
			rowView.setTag(holder);
		} else {
			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}

		if(!mediaModel.getPriceAsStr().equals("0.0"))
		    holder.workPrice.setText(mediaModel.getPriceAsStr());
		else
			holder.workPrice.setText("");

		holder.textViewFirstLine.setText(mediaModel.getWorkName());
		holder.checkBox.setTag(position);
		holder.checkBox.setChecked(dataList.get(position).isChecked());
		holder.checkBox.setOnCheckedChangeListener(this);
		holder.workPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(final View view, final boolean b) {
				WLog.d(TAG, "onFocusChange " +b);
				if(holder.workPrice.getText().toString().equals(""))
					mediaModel.setPrice(0);
				else
					mediaModel.setPrice(Double.valueOf(holder.workPrice.getText().toString()));
				priceListener.priceUpdate();
			}
		});
		return rowView;
	}

	public static class ViewHolder {
		TextView textViewFirstLine;
		EditText workPrice;
		CheckBox checkBox;
	}
	public List<ServiceWorkItemModel> getCheckedItems(){
		List<ServiceWorkItemModel> checkedDataList=new ArrayList<ServiceWorkItemModel>();
		for(ServiceWorkItemModel item:dataList){
			if(item.isChecked())
				checkedDataList.add(item);
		}
      return checkedDataList;	}

	public void setPriceListener(final PriceChangeListener priceListener) {
			this.priceListener = priceListener;
		}
		public interface PriceChangeListener{
			public void priceUpdate();
		}

		@Override
		public void onCheckedChanged(final CompoundButton butView, final boolean b) {
			Integer position=(Integer)butView.getTag();
		if(b){
			dataList.get(position).setChecked(true);
			WLog.d(TAG, "CheckBoxChecked " +position);
		}else{
			dataList.get(position).setChecked(false);
			WLog.d(TAG, "CheckBox UnChecked " +position);
		}
		priceListener.priceUpdate();

	}


}

