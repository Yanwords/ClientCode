package com.nju.myapp.util;

import java.util.ArrayList;

import com.nju.myapp.friendlists.Friend;

public class FriendArray {
	static private ArrayList<Friend> array = null;
	static private int friendIndex = -1;
	static private String myName = "";

	static public ArrayList<Friend> getArray() {
		if (array == null) {
			array = new ArrayList<Friend>();
			return array;
		} else {
			return array;
		}
	}

	public static int getFriendIndex() {
		return friendIndex;
	}

	public static void setFriendIndex(int friendIndex) {
		FriendArray.friendIndex = friendIndex;
	}

	public static String getMyName() {
		return myName;
	}

	public static void setMyName(String myName) {
		FriendArray.myName = myName;
	}

}
