package car.simple.service;

import car.simple.service.database.DbEngine;

/**
 * Class include Event parameter
 * Created by r.savuschuk on 11/19/2014.
 */
public class Event {


	private String name;
	 private int number;
	 private DbEngine.Action action;
	 private String title;

	public Event(final String name,final int num) {
		this.name = name;
		this.number=num;
	}

	public String getName() {
		return name;
	}
}
