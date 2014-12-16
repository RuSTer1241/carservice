package com.car.service;

import java.io.Serializable;

/**
 * Created by r.savuschuk on 12/15/2014.
 */
public class PriceController  implements Serializable {
	private double price;
	private OnPriceChangedListener listener;
	public void setListener(final OnPriceChangedListener listener) {
		this.listener = listener;
	}

	public double getPrice() {
		return price;
	}

	public String getTextPrice() {
		return String.valueOf(price);
	}

	public void addToItemPrice(final double price) {
		this.price += price;
		listener.onPriceChanged(this. getTextPrice());
	}

	public void setItemsPrice(final double price) {
		this.price = price;
		listener.onPriceChanged(this. getTextPrice());
	}

public interface OnPriceChangedListener {
	void onPriceChanged(String price);
}


}
