package com.nju.myapp;

import android.widget.Button;
import android.widget.CheckBox;

public class ScenicSpot {
	private int order;
	private int logo;
	private String spotname;
	private float score;
	private String information;
	private CheckBox jud;
	private Button delta;

	public ScenicSpot(int order, int logo, String name, float score, String info) {

		this.order = order;
		this.logo = logo;
		this.spotname = name;
		this.score = score;
		this.information = info;

	}

	public String getSpotname() {
		return spotname;
	}

	public void setSpotname(String spotname) {
		this.spotname = spotname;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public int getorder() {

		return order;
	}

	public int getlogoID() {

		return logo;
	}

}
