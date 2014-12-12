package com.car.service.listmode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.car.service.R;
import com.car.service.BaseFragment;
import com.car.service.database.DbEngine;
import com.car.service.model.CarServiceApplication;

/**
 * Created by r.savuschuk on 9/12/2014.
 */
public class ListItemFragment extends BaseFragment implements	ListScrollListener.ListFinish
{
	private String TAG = getClass().getSimpleName();
	private ListModeController controller;
	private ListView listview;
	private SwipeDismissListViewTouchListener touchListener;
	private ListScrollListener scrollListener;



	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity.setItemsMode();
		controller=new ListModeController(this.getActivity());
		scrollListener=new ListScrollListener();
		scrollListener.addListFinishListener(this);
		setContent(activity.prefEditor.loadTabId());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.list_item_fragment, null);
		listview = (ListView)v.findViewById(R.id.listview);
		initListView();
		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		scrollListener.removeListFinishListener(this);
	}



	@Override
	public void listFinished(long dataInt) {
		    if(!controller.isListEmpty())
		     changeContent(activity.prefEditor.loadTabId(),dataInt);
	}

	private void  initListView(){
		listview.setAdapter(controller.getAdapter());
		listview.setOnScrollListener(scrollListener);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				final ItemModel item = (ItemModel) parent.getItemAtPosition(position);
			}
		});
		touchListener = new SwipeDismissListViewTouchListener(listview,controller);
		listview.setOnTouchListener(touchListener);

	}
	/**
	 * if user change tab --- load new type data items
	 * if user scroll down ---- load new items by parts (30 items for one time)
	 */
	 public void changeContent(int type,long startTime){
		/* if(type==ActionBaseDialog.HARDCODED_EVENTS_NUM){
			 FragmentTransaction ft = getFragmentManager().beginTransaction();
			 DialogFragment newFragment = new EventListDialog(controller);
			 newFragment.show(ft, "Event Dialog");
		 }else {*/
			 CarServiceApplication.getDbEngine().dbReadRequest(DbEngine.Mode.LIST, type, startTime, controller);
		 //}
	 }

	public void setContent(int type){
		controller.getAdapter().clear();
		changeContent(type, CarServiceApplication.getToTime());
	}



}
