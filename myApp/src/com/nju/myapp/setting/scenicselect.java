package com.nju.myapp.setting;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.myapp.R;
import com.nju.myapp.main.MainActivity;
import com.nju.myapp.util.HTTPUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class scenicselect extends Activity {
	private static String url = "";
	private final String url_constant = "/scenic/select.do?";
	private EditText scenicname = null;
	@SuppressLint("NewApi")
	private Button select;
	int counter = 3;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenicselect);
		scenicname = (EditText) findViewById(R.id.editText1);
		select = (Button) findViewById(R.id.button1);
		initView();
		setListener();
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String scenicName = scenicname.getText().toString();
						Log.v("scenicName = ", scenicName);
						url = HTTPUtil.url_constant + url_constant + "scenicname=" + scenicName;
						Log.v("url", url);
						@SuppressWarnings({ "resource" })
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
							data.putString("scenicname", scenicName);
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
//                                 Toast.makeText(MainActivity.this,key,Toast.LENGTH_LONG).show();
								try {
									JSONObject json = new JSONObject(key);
									String result = (String) json.get("select");
									if ("success".equals(result)) {
										Toast.makeText(scenicselect.this, "查询成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(scenicselect.this, "查询失败", Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
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
				startActivity(new Intent(scenicselect.this, MainActivity.class));
			}
		});
	}
}