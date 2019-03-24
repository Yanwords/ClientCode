package com.nju.myapp;

import java.util.ArrayList;
import java.util.List;

import com.nju.myapp.util.ScenicSpotArray;

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
public class ScenicSpotList extends Activity {
	private List<ScenicSpot> spotList = new ArrayList<ScenicSpot>();
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
		setContentView(R.layout.scenicspotlist);

		initSpot();

		ScenicSpotAdapter adapter = new ScenicSpotAdapter(getApplicationContext(), R.layout.scenicspot, spotList);
		spotview.setAdapter(adapter);

		spotview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ScenicSpot scenicspot = spotList.get(position);
				Toast.makeText(getApplicationContext(), "您要查看" + scenicspot.getorder() + "号车主的全部信息", Toast.LENGTH_SHORT)
						.show();
			}
		});

		adapter.setOnItemSelectListener(new ScenicSpotAdapter.onItemSelect() {
			@Override
			public void seleId(int i) {

				int order = i + 1;
				Toast.makeText(getApplicationContext(), "您正在为" + order + "号车主充值", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initSpot() {

		spotview = (ListView) findViewById(R.id.spotview);
		ArrayList<ScenicSpot> array = ScenicSpotArray.getArray();
		if (array != null && array.size() > 0) {
			int nums = array.size();
			for (int i = 0; i < nums; ++i) {
				ScenicSpot ss = array.get(i);
				spotList.add(ss);
			}
		}

//		Spot spot1 = new Spot(1, R.drawable.logo, "胡海", 100);
//		Spot spot2 = new Spot(2, R.drawable.logo, "蓝月", 200);
//		spotList.add(spot1);
//		spotList.add(spot2);
	}
}
