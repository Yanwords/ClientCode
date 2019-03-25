package com.nju.myapp.news;

import com.nju.myapp.R;
import com.nju.myapp.R.id;
import com.nju.myapp.R.layout;
import com.nju.myapp.spotlists.SpotList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NewsFragment extends Fragment {
	private TextView tv;
	private Button shareExperience;
	private Button scenicEvaluation;
	private Button addFriend;
	private Button spotList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.news_layout, container, false);
		tv = (TextView) newsLayout.findViewById(R.id.news);
		shareExperience = (Button) newsLayout.findViewById(R.id.button7);
		scenicEvaluation = (Button) newsLayout.findViewById(R.id.button8);
		addFriend = (Button) newsLayout.findViewById(R.id.button9);
		spotList = (Button) newsLayout.findViewById(R.id.button10);
		setListener();
		tv.setText("出游协同工具");
		return newsLayout;
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		shareExperience.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), shareexperience.class);
				startActivity(intent);
			}
		});
		scenicEvaluation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), scenicevaluation.class);
				startActivity(intent);
			}
		});
		addFriend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), addfriend.class);
				startActivity(intent);
			}
		});
		spotList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SpotList.class);
				startActivity(intent);
			}
		});
	}
}
