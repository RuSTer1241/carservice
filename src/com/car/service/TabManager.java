package com.car.service;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by r.savuschuk on 9/12/2014.
 */
public class TabManager {
	Context context;
	TabHost host;
    int graphUnaccessableTabs[]={1,2,5};

	public TabManager(Context context,TabHost tabs,int tabId) {
		this.host=tabs;
		this.context=context;
		host.setup();
        /*
        do not change tabs order
        it will have side effect in previous version user items in DB*/
		TabHost.TabSpec spec = host.newTabSpec(context.getResources().getString(R.string.eat_short));
		spec.setContent(R.id.tvTab1);
		spec.setIndicator(context.getResources().getString(R.string.eat_short));
		host.addTab(spec);
		host.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.eat_tab_sel);

        spec = host.newTabSpec(context.getResources().getString(R.string.admin));
        spec.setContent(R.id.tvTab2);
        spec.setIndicator(context.getResources().getString(R.string.crap_short));
        host.addTab(spec);
        host.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.crap_tab_sel);


        spec = host.newTabSpec(context.getResources().getString(R.string.fuel));
		spec.setContent(R.id.tvTab3);
		spec.setIndicator(context.getResources().getString(R.string.pees_short));
		host.addTab(spec);
		host.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.pees_tab_sel);



		spec = host.newTabSpec(context.getResources().getString(R.string.temperature));
		spec.setContent(R.id.tvTab4);
		spec.setIndicator(context.getResources().getString(R.string.temperature_short));
		host.addTab(spec);
		host.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.temp_tab_sel);

		spec = host.newTabSpec(context.getResources().getString(R.string.weight));
		spec.setContent(R.id.tvTab5);
		spec.setIndicator(context.getResources().getString(R.string.weight_short));
		host.addTab(spec);
		host.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.weight_tab_sel);

		spec = host.newTabSpec(context.getResources().getString(R.string.comment_));
		spec.setContent(R.id.tvTab6);
		spec.setIndicator(context.getResources().getString(R.string.comment_short));
		host.addTab(spec);
		host.getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.comment_tab_sel);


		for(int i=0;i<host.getTabWidget().getChildCount();i++)
		{
			TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			tv.setTextColor(context.getResources().getColor(R.color.white));
		}
		host.setCurrentTab(tabId);

	}
	public int getCurrentGraphTab(){
		int currentTab=host.getCurrentTab();
		for(int i=0;i<graphUnaccessableTabs.length;i++){
			if(graphUnaccessableTabs[i]==currentTab) {
				currentTab += 1;
				Toast.makeText(context, R.string.items_only, Toast.LENGTH_SHORT).show();
			}
		}
		if(currentTab>=host.getTabWidget().getTabCount())
			currentTab=0;
		host.setCurrentTab(currentTab);
		return currentTab;
	}

	public void deleteUnusableTabs(){
		host.getTabWidget().getChildAt(1).setVisibility(View.GONE);
		host.getTabWidget().getChildAt(2).setVisibility(View.GONE);
		host.getTabWidget().getChildAt(5).setVisibility(View.GONE);
	}
	public void restoreUnusableTabs(){
		host.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
		host.getTabWidget().getChildAt(2).setVisibility(View.VISIBLE);
		host.getTabWidget().getChildAt(5).setVisibility(View.VISIBLE);
	}

}
