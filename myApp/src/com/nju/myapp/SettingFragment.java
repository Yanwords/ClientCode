package com.nju.myapp;

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
public class SettingFragment extends Fragment {
	private TextView tv;
	private Button login;
	private Button register;
	private Button scenicSelect;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.setting_layout, container, false);
        tv=(TextView) settingLayout.findViewById(R.id.setting);
        tv.setText("哈哈哈哈哈哈");
        login = (Button)settingLayout.findViewById(R.id.button1);
//      welcome = (TextView)findViewById(R.id.textView1);
        register = (Button)settingLayout.findViewById(R.id.button2);
        scenicSelect = (Button)settingLayout.findViewById(R.id.button3);
        setListener();
        return settingLayout;
     }
    
    private void setListener() {
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         	  Intent intent = new Intent(getActivity(), login.class);
         	  startActivity(intent);
           }
       });
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         	  Intent intent = new Intent(getActivity(), register.class);
         	  startActivity(intent);
           }
       });
       scenicSelect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         	  Intent intent = new Intent(getActivity(), scenicselect.class);
         	  startActivity(intent);
           }
       });
    }
    
    
}
