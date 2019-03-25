package com.nju.myapp.util;

import java.util.ArrayList;

import com.nju.myapp.scenicspotlists.ScenicSpot;

public class ScenicSpotArray {
	static private ArrayList<ScenicSpot> array = null;
	static private int scenicIndex = -1;

	static public ArrayList<ScenicSpot> getArray() {
		if (array == null) {
			array = new ArrayList<ScenicSpot>();
			return array;
		} else {
			return array;
		}
	}

	static public void setScenicIndex(int index) {
		scenicIndex = index;
	}

	static public int getScenicIndex() {
		return scenicIndex;
	}
}
