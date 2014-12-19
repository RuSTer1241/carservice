package com.car.service;

/**
 * Created by r.savuschuk on 12/12/2014.
 */
public class ServiceWorkItemModel {
	private String workName;
	private double price;
	private int id;
	private boolean checked=false;

	public ServiceWorkItemModel(final int id, final String workName) {
		this.workName = workName;
		this.id = id;
	}
	public ServiceWorkItemModel(final int id, final String workName,double price) {
		this.workName = workName;
		this.id = id;
		this.price=price;
	}
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(final String workName) {
		this.workName = workName;
	}
	public double getPrice() {
		return price;
	}
	public String getPriceAsStr() {
		return String.valueOf(price);
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}













}

