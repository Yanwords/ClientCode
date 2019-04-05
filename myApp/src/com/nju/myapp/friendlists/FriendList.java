package com.nju.myapp.friendlists;

import java.util.ArrayList;
import java.util.List;

import com.nju.myapp.R;
import com.nju.myapp.contact.removefriend;
import com.nju.myapp.main.MainActivity;
import com.nju.myapp.util.FriendArray;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class FriendList extends Activity {
	private List<Friend> friendList = new ArrayList<Friend>();
	private ListView friendview;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);

		initSpot();

		FriendAdapter adapter = new FriendAdapter(getApplicationContext(), R.layout.friend, friendList);
		friendview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		friendview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Friend friend = friendList.get(position);
				Toast.makeText(getApplicationContext(), "您要查看" + friend.getOrder() + "号车主的全部信息", Toast.LENGTH_SHORT)
						.show();
			}
		});

		adapter.setOnItemSelectListener(new FriendAdapter.onItemSelect() {
			@Override
			public void seleId(int i) {

				int order = i + 1;
				FriendArray.setFriendIndex(i);
				Intent intent = new Intent(FriendList.this, removefriend.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "您正在为" + order + "号车主充值", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initSpot() {
		findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FriendList.this, MainActivity.class));
			}
		});
		friendview = (ListView) findViewById(R.id.friendview);
		ArrayList<Friend> array = FriendArray.getArray();
		if (array != null && array.size() > 0) {
			int nums = array.size();
			for (int i = 0; i < nums; ++i) {
				Friend f = array.get(i);
				friendList.add(f);
			}
		}

//		Spot spot1 = new Spot(1, R.drawable.logo, "胡海", 100);
//		Spot spot2 = new Spot(2, R.drawable.logo, "蓝月", 200);
//		spotList.add(spot1);
//		spotList.add(spot2);
	}
}
