package com.nju.myapp.contact;

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
public class ContactsFragment extends Fragment {
	private TextView tv;
	private Button display;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.contacts_layout, container, false);
		tv = (TextView) contactsLayout.findViewById(R.id.contacts);
		tv.setText("出游协同工具");
		display = (Button) contactsLayout.findViewById(R.id.button);
		setListener();
		return contactsLayout;
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		display.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), frienddisplay.class);
				startActivity(intent);
			}
		});
	}
}
