package com.car.service.database;

import java.io.Serializable;

/**
 * Created by r.savuschuk on 11/11/2014.
 */
public class DbAnswer<T> implements Serializable {
	private static final long serialVersionUID = 1234567890123L;

	private boolean isSuccess;
	private T data;
	private Double allItemsSumPrice;
	private DbError error;

	DbAnswer() {
	}

	DbAnswer(T data) {
		isSuccess = true;
		this.data = data;
	}

	DbAnswer(DbError error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public T getData() {
		return data;
	}

	void setData(T data) {
		this.data = data;
		isSuccess = true;

	}

	public Double getAllItemsSumPrice() {
		return allItemsSumPrice;
	}

	public void setAllItemsSumPrice(final Double allItemsSumPrice) {
		this.allItemsSumPrice = allItemsSumPrice;
	}

	public DbError getError() {
		return error;
	}

	void setError(DbError error) {
		this.error = error;
		isSuccess = false;
	}

}
