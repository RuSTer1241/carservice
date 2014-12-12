package com.car.service.listmode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Toast;
import com.car.service.R;
import com.car.service.database.DbEngine;
import com.car.service.database.DbError;
import com.car.service.model.CarServiceApplication;
import com.car.service.utils.WLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 11/21/2014.
 */
public class ListModeController implements DbEngine.Callback<List<ItemModel>>, SwipeDismissListViewTouchListener.DismissCallbacks{
	DbEngine engine;

	private ItemBaseAdapter adapter;
	private List<ItemModel> itemsDataList=new ArrayList<ItemModel>();
	Activity activity;
	public ListModeController(Activity activity) {
		this.activity=activity;
		engine = CarServiceApplication.getDbEngine();
		adapter = new ItemBaseAdapter(activity, itemsDataList);//need to init itemdatalist

	}
	@Override
	public void onSuccess(final List<ItemModel> data) {
		WLog.e("DbEngine", " OnSuccess");
		addRows(data);
		adapter.addValues(itemsDataList);

	}

	@Override
	public void onFail(final DbError error) {
		WLog.e("DbEngine", " OnFail");
		Toast.makeText(activity, "Database error", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean canDismiss(final int position) {
		return true;
	}

	@Override
	public void onDismiss(final ListView listView, final int[] reverseSortedPositions) {
		new AlertDialog.Builder(activity)
			.setTitle(activity.getResources().getString(R.string.delete_item))
			.setMessage(activity.getResources().getString(R.string.delete_item_msg))
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					WLog.e("TEST", "delete ListView item");
					for (int position : reverseSortedPositions) {
						ItemModel item= (ItemModel)adapter.getItem(position);
						engine.dbDeleteRequest(item.getId());
						adapter.removeItem(position);
					}
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// do nothing
				}
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();


	}

	public ItemBaseAdapter getAdapter() {
		return adapter;
	}

	private void addRows(List<ItemModel> list) {
		itemsDataList.clear();
		if(list.size()>0) {
			itemsDataList.addAll(list);
		}
	}

	 boolean isListEmpty(){
		boolean b=false;
		if(itemsDataList.size()==0)
			b=true;
		return b;
	}


}
