package com.nju.myapp.spotlists;

import android.widget.Button;
import android.widget.CheckBox;

public class Spot {
	private int order;
	private int logo;
	private String car_order;
	private String name;
	private int money;
	private CheckBox jud;
	private Button delta;

	public Spot(int order, int logo, String name, int money) {

		this.order = order;
		this.logo = logo;
		this.car_order = "辽A1000" + order;
		this.name = name;
		this.money = money;

	}

	public int getorder() {

		return order;
	}

	public String getcar_order() {

		return car_order;
	}

	public int getlogoID() {

		return logo;
	}

	public String getname() {

		return name;
	}

	public int getmoney() {

		return money;
	}
}
