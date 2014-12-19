package com.car.service;

import android.app.Activity;
import android.widget.TextView;
import com.car.service.utils.ConfigInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r.savuschuk on 12/15/2014.
 */
public class ServiceItemsController implements ServiceWorksAdapter.PriceChangeListener {
	private String TAG = getClass().getSimpleName();
	private TextView sum_price;
    //private PriceController priceController;
	private String [] itemNames;
	private List<ServiceWorkItemModel> values = new ArrayList<ServiceWorkItemModel>();
	private ServiceWorksAdapter worksAdapter;
	private Activity context;
	private double price;

	public ServiceItemsController(Activity context, TextView sum_price) {
		this.context=context;
		this.sum_price=sum_price;
		itemNames= ConfigInfo.service_works;
		for (int i=0;i<itemNames.length;i++) {
			this.values.add(new ServiceWorkItemModel(i,itemNames[i]));
		}
		worksAdapter=new ServiceWorksAdapter(context,values);
		worksAdapter.setPriceListener(this);
	}

	public ServiceWorksAdapter getWorksAdapter() {
		return worksAdapter;
	}

	@Override
	public void priceUpdate() {
		price=0;
		for(ServiceWorkItemModel item: values){
			if(item.isChecked()){
				price+=item.getPrice();
			}
		}
		sum_price.setText(String.valueOf(price));
	}

	public void clear(){
		worksAdapter.setPriceListener(null);
	}





}
