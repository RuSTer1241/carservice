package com.car.service;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.car.service.graphmode.GraphFragment;
import com.car.service.listmode.ListItemFragment;
import com.car.service.model.CarServiceApplication;
import com.car.service.utils.WLog;
import com.car.service.widget.searchslider.SearchSlider;
import com.google.android.gms.analytics.HitBuilders;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by r.savuschuk on 8/4/14.
 */
public class Statistic extends OptionMenuActivity {

	TextView fromDate;
	TextView toDate;
	//Long toDateInt;
	//Long fromDateInt;
	ImageView fromDateBut;
	ImageView toDateBut;
	LinearLayout fromDateLayout;
	LinearLayout toDateLayout;
	SearchController searchController;
	DialogFragment calendarFragmentDialog;
	Handler dialogHandler;
	ListItemFragment listItemFrag;
	GraphFragment grafFrag;
	FragmentTransaction fTrans;
	TabHost tabs;
	TabManager tabManager;
	private SearchSlider searchView;
	ActionBarController actionBarController;

	private final long ONEWEEKINMILLS = 604800000L;
	private final long ONEDAYINMILLS = 86399999L;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic);
		mActionBar.setCustomView(actionBarCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		prefEditor = CarServiceApplication.getPrefEditor();
		searchView=(SearchSlider)findViewById(R.id.slider);
        searchController=new SearchController(this,searchView,prefEditor.getLaunch());
		listItemFrag = new ListItemFragment();
		grafFrag = new GraphFragment();
		actionBarController=new ActionBarController(this,actionBarCustomView);
		actionBarController.initActionViewStatistic(listItemFrag,grafFrag);

		fTrans = getFragmentManager().beginTransaction();
		if (prefEditor.getCurFragment()) {
			fTrans.add(android.R.id.tabcontent, listItemFrag);
		} else {
			fTrans.add(android.R.id.tabcontent, grafFrag);
		}
		fTrans.commit();
		initViews();
		tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabManager = new TabManager(this,tabs, prefEditor.loadTabId());
		tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				prefEditor.saveCurTab(tabs.getCurrentTab());
				if (prefEditor.getCurFragment()) {
					listItemFrag.setContent(tabs.getCurrentTab());
				} else {
					grafFrag.replaceContent(getCurrentGraphTab());
				}
			}
		});

		fromDateLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				showFragmentDialog(fromDate.getId());
			}
		});
		toDateLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				showFragmentDialog(toDate.getId());
			}
		});
		dialogHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
					case 10:
						fromDate.setText("");
						fromDate.append(msg.getData().getString("currentDate").toString());
						calendarFragmentDialog.dismiss();
						CarServiceApplication.setFromTime(msg.getData().getLong("currentDateInt"));
						break;

					case 20:
						toDate.setText("");
						toDate.append(msg.getData().getString("currentDate").toString());
						CarServiceApplication.setToTime(msg.getData().getLong("currentDateInt") + ONEDAYINMILLS);
						toDate.invalidate();
						calendarFragmentDialog.dismiss();
						break;
				}
				if (prefEditor.getCurFragment()) {
					listItemFrag.setContent(tabs.getCurrentTab());
				} else {
					grafFrag.replaceContent(getCurrentGraphTab());
				}
			}
		};
	}

	@Override
	protected void onResume() {
		super.onResume();
		t.send(new HitBuilders.EventBuilder()
			.setCategory("open activity")
			.setAction(this.getClass().getSimpleName())
			.setValue(1)
			.build());
		WLog.d("TEST", "Statistic onResume");
	}

	private void initViews() {
		fromDate = (TextView) findViewById(R.id.from_date_view);
		fromDateBut = (ImageView) findViewById(R.id.from_date_but);
		fromDateLayout = (LinearLayout)findViewById(R.id.from_date_layout);

		toDateBut = (ImageView) findViewById(R.id.to_date_but);
		toDate = (TextView) findViewById(R.id.to_date_view);
		toDateLayout = (LinearLayout)findViewById(R.id.to_date_layout);
		long toDateInt = Calendar.getInstance().getTimeInMillis();
		long fromDateInt = toDateInt - ONEWEEKINMILLS;
		CarServiceApplication.setToTime(toDateInt);
		CarServiceApplication.setFromTime(fromDateInt);
		fromDate.setText(getCurrentDate(CarServiceApplication.getFromTime()));
		toDate.setText(getCurrentDate(CarServiceApplication.getToTime()));
	}

	private String getCurrentDate(long time) {
		SimpleDateFormat df = new SimpleDateFormat(" dd-MM-yyyy");
		String formattedDate = df.format(time);
		return formattedDate;
	}

	void showFragmentDialog(int id) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// Create and show the dialog.
		calendarFragmentDialog = new DateDialogFragment(dialogHandler, id);
		calendarFragmentDialog.show(ft, "dialog");
	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		/*menu.getItem(1).setVisible(false);//share button
		menu.getItem(2).setVisible(false);//chart/line  button
		if (menu.getItem(0).getItemId() == R.id.action_chart) {
			WLog.d("TEST", "Statistic onCreateOptionsMenu");
			if (prefEditor.getCurFragment()) {
				menu.getItem(0).setIcon(R.drawable.chart_line);
			} else {
				menu.getItem(0).setIcon(R.drawable.list_num);
			}
		}*/
		return super.onPrepareOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		/*fTrans = getFragmentManager().beginTransaction();
		switch (item.getItemId()) {
			case R.id.action_chart:
				if (prefEditor.getCurFragment()) {
					item.setIcon(R.drawable.list_num);
					item.setTitle("Chart");
					fTrans.replace(android.R.id.tabcontent, grafFrag);
					prefEditor.saveCurFragment(false);
				} else {
					item.setIcon(R.drawable.chart_line);
					item.setTitle("itemlist");
					fTrans.replace(android.R.id.tabcontent, listItemFrag);
					prefEditor.saveCurFragment(true);
				}


				break;

			default:
				break;
		}
		fTrans.commit();*/
		return super.onOptionsItemSelected(item);
	}


	public int getCurrentGraphTab() {
		return tabManager.getCurrentGraphTab();
	}

	public void setGraphMode() {
		tabManager.deleteUnusableTabs();
	}

	public void setItemsMode() {
		tabManager.restoreUnusableTabs();
	}
}
