package com.car.service;

import android.content.Context;
import android.widget.ImageView;
import com.car.service.widget.searchslider.SearchSlider;

/**
 * Created by r.savuschuk on 10/3/2014.
 */
public class SearchController {
	Context context;
	SearchSlider view;
	ImageView slide_image;


	public SearchController(final Context context,final SearchSlider v,long firstLaunch) {
		this.context=context.getApplicationContext();
		view=v;
		v.setOnDrawerOpenListener(new SearchSlider.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				slide_image=(ImageView) v.findViewById(R.id.slide_search_image);
				slide_image.setImageDrawable(context.getResources().getDrawable(R.drawable.slidearrow_up));
			}
		});

		v.setOnDrawerCloseListener(new SearchSlider.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				slide_image=(ImageView) v.findViewById(R.id.slide_search_image);
				slide_image.setImageDrawable(context.getResources().getDrawable(R.drawable.slidearrow_down));
			}
		});
		if(firstLaunch==1)//appfirst launch
		v.open();
	}
}
