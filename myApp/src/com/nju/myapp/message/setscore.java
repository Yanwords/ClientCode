package com.nju.myapp.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.nju.myapp.R;
import com.nju.myapp.scenicspotlists.ScenicSpot;
import com.nju.myapp.util.HTTPUtil;
import com.nju.myapp.util.ScenicSpotArray;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class setscore extends Activity {
	private static String url = "";
	private final String url_constant = "/scenic/setscore.do?";
	private TextView scenicname = null;
	private TextView scenicinfo = null;
	private EditText scenicscore = null;
	private ScenicSpot ss;
	@SuppressLint("NewApi")
	private Button setscore;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setscore);
		scenicname = (TextView) findViewById(R.id.textView3);
		scenicinfo = (TextView) findViewById(R.id.textView5);
		scenicscore = (EditText) findViewById(R.id.editText1);
		setscore = (Button) findViewById(R.id.button1);
		int index = ScenicSpotArray.getScenicIndex();
		if (index < 0) {
			Toast.makeText(setscore.this, "景点信息有误", Toast.LENGTH_LONG).show();
		} else {
			ArrayList<ScenicSpot> array = ScenicSpotArray.getArray();
			ss = array.get(index);
			scenicname.setText(ss.getSpotname());
			Log.v("scenicName = ", ss.getSpotname());
			scenicinfo.setText(ss.getInformation());
			Log.v("scenicInfo = ", ss.getInformation());
			setListener();
		}

	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		setscore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					@Override
					public void run() {
						String scenicScore = scenicscore.getText().toString();
						Log.v("scenicScore = ", scenicScore);
						url = HTTPUtil.url_constant + url_constant + "scenicname=" + ss.getSpotname() + "&scenicinfo="
								+ ss.getInformation() + "&scenicscore=" + scenicScore;
						Log.d("远程URL", url);
						@SuppressWarnings("resource")
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost request;
						// JSON的解析过程
						try {
//						request = new HttpPost(URLEncoder.encode(url,"UTF-8"));
							request = new HttpPost(url);
							request.addHeader("Accept", "text/json");
							request.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
							Log.d("request", request.toString());
							HttpResponse response = httpclient.execute(request);
							// 获取HttpEntity
							HttpEntity entity = response.getEntity();
							// 获取响应的结果信息
							String json = EntityUtils.toString(entity, "UTF-8");
							Message msg = new Message();
							msg.what = 0x11;

							Bundle data = new Bundle();
							data.putString("result", json);
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
									String result = (String) json.get("setscore");
									if ("success".equals(result)) {
										Toast.makeText(setscore.this, "评分成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(setscore.this, "评分失败", Toast.LENGTH_LONG).show();
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

}
