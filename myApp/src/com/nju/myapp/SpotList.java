package com.nju.myapp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class SpotList extends Activity {
	private List<Spot> spotList = new ArrayList<Spot>();
	private ListView spotview;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spotlist);

		initSpot();

		SpotAdapter adapter = new SpotAdapter(getApplicationContext(), R.layout.spot, spotList);
		spotview.setAdapter(adapter);

		spotview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Spot spot = spotList.get(position);
				Toast.makeText(getApplicationContext(), "您要查看" + spot.getorder() + "号车主的全部信息", Toast.LENGTH_SHORT)
						.show();
			}
		});

		adapter.setOnItemSelectListener(new SpotAdapter.onItemSelect() {
			@Override
			public void seleId(int i) {

				int order = i + 1;
				Toast.makeText(getApplicationContext(), "您正在为" + order + "号车主充值", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initSpot() {

		spotview = (ListView) findViewById(R.id.spotview);
		Spot spot1 = new Spot(1, R.drawable.logo, "胡海", 100);
		Spot spot2 = new Spot(2, R.drawable.logo, "蓝月", 200);
		spotList.add(spot1);
		spotList.add(spot2);
	}
}
