package com.nju.myapp.util;

import java.util.ArrayList;

import com.nju.myapp.ScenicSpot;

public class ScenicSpotArray {
	static private ArrayList<ScenicSpot> array = null;

	static public ArrayList<ScenicSpot> getArray() {
		if (array == null) {
			array = new ArrayList<ScenicSpot>();
			return array;
		} else {
			return array;
		}
	}
}
