package com.car.service;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by r.savuschuk on 10/28/2014.
 */
	public class SpinnerAdapter extends ArrayAdapter<String>
	{
		private Context context;
		String[] data = null;

		public SpinnerAdapter(Context context, int resource, String[] data)
		{
			super(context, resource, data);
			this.context = context;
			this.data = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
			return super.getView(position, convertView, parent);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent)
		{   // This view starts when we click the spinner.
			View row = convertView;
			if(row == null)
			{

				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.eat_q_spinner_item, parent, false);
			}

			String item = data[position];

			if(item != null)
			{   // Parse the data from each object and set it.

				TextView myCountry = (TextView) row.findViewById(R.id.quantity_string);
				if(myCountry != null)
					myCountry.setText(item);

			}

			return row;
		}
}

