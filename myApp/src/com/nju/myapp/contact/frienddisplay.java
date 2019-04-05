package com.nju.myapp.contact;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.myapp.R;
import com.nju.myapp.friendlists.Friend;
import com.nju.myapp.friendlists.FriendList;
import com.nju.myapp.main.MainActivity;
import com.nju.myapp.util.FriendArray;
import com.nju.myapp.util.HTTPUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
//public class scenicdisplay extends Activity {
public class frienddisplay extends AppCompatActivity {
	private static String url = "";
	private final String url_constant = "/user/friend.do?";
//	private EditText myname = null;
	@SuppressLint("NewApi")
	private Button display;
//	private TableLayout table;
	int column = 4;
	/* add bottom */
//	private RadioGroup mTabRadioGroup;
//	private SparseArray<BlankFragment> mFragmentSparseArray;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frienddisplay);
//		myname = (EditText) findViewById(R.id.editText1);
		display = (Button) findViewById(R.id.button1);
//		table = (TableLayout) findViewById(R.id.table);

		initView();
		setListener();
		// 第一次启动时选中第0个tab
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		display.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
//						String name = myname.getText().toString();
						String name = MainActivity.USERNAME;
						if ("".equals(name)) {
							name = "";
						}
						Log.v("myname", name);
//						url = HTTPUtil.url_constant + url_constant + "name=" + MainActivity.USERNAME;
						url = HTTPUtil.url_constant + url_constant + "name=" + name;
						Log.v("url", url);
						@SuppressWarnings("resource")
						HttpClient httpclient = new DefaultHttpClient();
						HttpGet request = new HttpGet(url);
						request.addHeader("Accept", "text/json");
						Log.d("request", request.toString());
						// 获取响应的结果
						HttpResponse response;
						try {
							response = httpclient.execute(request);
							// 获取HttpEntity
							HttpEntity entity = response.getEntity();
							// 获取响应的结果信息
							String json = EntityUtils.toString(entity, "UTF-8");
//	           	       	  String myUser = "";
							Message msg = new Message();
							msg.what = 0x11;

							Bundle data = new Bundle();
							data.putString("result", json);
							data.putString("myname", name);
							msg.setData(data);
							hander.sendMessage(msg);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@SuppressLint("HandlerLeak")
					Handler hander = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == 0x11) {
								Bundle data = msg.getData();

								String key = data.getString("result");// 得到json返回的json数据
								String name = data.getString("myname");
//                                 Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
								try {
									JSONObject json = new JSONObject(key);
									String result = (String) json.get("friend");

									if ("success".equals(result)) {
										// Log.v()
										JSONArray array = (JSONArray) json.get("data");
										Log.v("length", Integer.toString(array.length()));
										if (array.length() > 0) {
//											displayTable(array);
											setArray(array);
											FriendArray.setMyName(name);
											Log.v("json:", array.toString());
										}
										Toast.makeText(frienddisplay.this, "查询信息成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(frienddisplay.this, "查询信息失败", Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}

						private void setArray(JSONArray array) throws JSONException {
							String[] columnName = { "friendId", "friendName", "friendGender", "friendAge" };
							ArrayList<Friend> a = FriendArray.getArray();
							a.clear();
							for (int row = 0; row < array.length(); ++row) {
								JSONObject json = array.getJSONObject(row);
//								int id = json.getInt(columnName[0]);
								String name = json.getString(columnName[1]);
								String gender = json.getString(columnName[2]);
								String temp = json.getString(columnName[3]);
								int age = Integer.parseInt(temp);
								Friend f = new Friend(row + 1, R.drawable.friend, name, gender, age);
								a.add(f);
//							Spot spot1 = new Spot(1, R.drawable.logo, "胡海", 100);
							}
							Intent intent = new Intent(frienddisplay.this, FriendList.class);
							startActivity(intent);
						}

					};

				}).start();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	private void initView() {
		findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(frienddisplay.this, MainActivity.class));
			}
		});
	}

}
