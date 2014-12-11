package car.simple.service.listmode;

import car.simple.service.database.DbEngine;
import car.simple.service.utils.WLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by r.savuschuk on 8/6/14.
 */
public class ItemModel {
	private String data;
	private long dataInt;
	private String time;
	private String comment;
	private String quantity;
	private DbEngine.Action aType;
	private int id;

	public DbEngine.Action getActionType() {
		return aType;
	}
/*
do not change order
it will have side effect in previous version user items in DB
 */
	public void setActionType(final int type) {
		switch (type) {
			case 0:
					this.aType = DbEngine.Action.EAT;
					break;
			case 1:
					this.aType = DbEngine.Action.KA;
					break;
			case 2:
					this.aType = DbEngine.Action.PE;
					break;
			case 3:
					this.aType = DbEngine.Action.TEMPERATURE;
					break;
			case 4:
					this.aType = DbEngine.Action.WEIGHT;
					break;
			case 5:
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(final String quantity) {
		this.quantity = quantity;
	}
}
