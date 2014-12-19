package com.car.service;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import java.util.List;

/**
 * Created by r.savuschuk on 12/12/2014.
 */
public class ItemDetailDialog extends DialogFragment {


	Context activityContext;
	List<ServiceWorkItemModel> list;
	private ListView listview;

	public ItemDetailDialog(final Context activityContext, final List<ServiceWorkItemModel> list) {
		this.activityContext = activityContext;
		this.list = list;
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.item_detail_dialog, container, false);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		listview = (ListView) v.findViewById(R.id.item_works_list);
		listview.setAdapter(new ItemDetailsAdapter(getActivity(), list));
		return v;
	}
}


