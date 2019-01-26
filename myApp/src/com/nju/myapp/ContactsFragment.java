package com.nju.myapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ContactsFragment extends Fragment {
	private TextView tv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.contacts_layout, container, false);
        tv=(TextView) contactsLayout.findViewById(R.id.contacts);
        tv.setText("出游协同工具");
        return contactsLayout;
     }
}
