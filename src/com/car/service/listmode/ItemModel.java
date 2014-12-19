package com.car.service.listmode;

import com.car.service.ServiceWorkItemModel;
import com.car.service.database.DbEngine;
import com.car.service.utils.WLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by r.savuschuk on 8/6/14.
 */
public class ItemModel {
	private String data;
	private long dataInt;
	private String time;
	private String comment;
	private String price;
	private DbEngine.Action aType;
	private String odometer;
	private int id;
	private List<ServiceWorkItemModel> itemServicesList = new ArrayList<ServiceWorkItemModel>();

	public DbEngine.Action getActionType() {
		return aType;
	}

	public List<ServiceWorkItemModel> getItemServicesList() {
		return itemServicesList;
	}

	/*
	do not change order
	it will have side effect in previous version user items in DB
	 */
	public void setActionType(final int type) {
		switch (type) {
			case 0:
				this.aType = DbEngine.Action.SERVICE;
				break;
			case 1:
				this.aType = DbEngine.Action.FUEL;
				break;
			case 2:
				this.aType = DbEngine.Action.ADMIN;
				break;
			case 3:
				this.aType = DbEngine.Action.COMMENT;
				break;

			default:
				this.aType = DbEngine.Action.NULL;
				break;
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDataInt() {
		return dataInt;
	}

	public String getData() {
		return data;
	}

	/**
	 * convert long Date to string using format "DD-mm-yyyy HH:mm:ss"
	 */
	public void setData(long dateInMills) {
		dataInt = dateInMills;
		Date temp = new Date(dateInMills);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String formattedTime = df.format(temp);
		WLog.e("TEST", formattedTime);
		this.data = formattedTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public String getPriceAsStr() {
		return String.valueOf(price);
	}

	public String getOdometer() {
		return odometer;
	}

	public void setOdometer(final String odometer) {
		this.odometer = odometer;
	}

	public void setItemServicesList(final String servicesList) {
		WLog.d("setItemServicesList", servicesList);
		if (servicesList != null) {
			try {
				JSONObject obj = new JSONObject(servicesList);
				final JSONArray service_list = obj.getJSONArray("service_list");
				for (int i = 0; i < service_list.length(); ++i) {
					JSONObject itemObj = service_list.getJSONObject(i);
					ServiceWorkItemModel item = new ServiceWorkItemModel(itemObj.getInt("id"), itemObj.getString("name"), itemObj.getDouble("price"));
					itemServicesList.add(item);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
