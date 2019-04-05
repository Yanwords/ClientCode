package com.nju.myapp.message;

import com.nju.myapp.R;

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
public class MessageFragment extends Fragment {
	private TextView tv;
	private Button scenicInsert;
	private Button scenicDisplay;
	private Button travelRecord;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.message_layout, container, false);
		tv = (TextView) messageLayout.findViewById(R.id.message);
		tv.setText("出游协同工具");
		scenicInsert = (Button) messageLayout.findViewById(R.id.button4);
		scenicDisplay = (Button) messageLayout.findViewById(R.id.button5);
		travelRecord = (Button) messageLayout.findViewById(R.id.button6);
		setListener();
		return messageLayout;
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		scenicInsert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), scenicinsert.class);
				startActivity(intent);
			}
		});
		scenicDisplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), scenicdisplay.class);
				startActivity(intent);
			}
		});
		travelRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), travelrecord.class);
				startActivity(intent);
			}
		});
	}

}
