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
import com.nju.myapp.util.HTTPUtil;

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
public class modifypassword extends Activity {
	private static String url = "";
	private final String url_constant = "/user/modifypassword.do?";
	private EditText username = null;
	private EditText password = null;
	private EditText newpassword = null;
	private EditText ackpassword = null;
//   private TextView attempts;
	private TextView USERNAME = null;
	@SuppressLint("NewApi")
	private Button modify;
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
		setContentView(R.layout.modifypassword);

		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
//      attempts = (TextView)findViewById(R.id.textView5);
		USERNAME = (TextView) findViewById(R.id.username);
		newpassword = (EditText) findViewById(R.id.editText3);
		ackpassword = (EditText) findViewById(R.id.editText4);
//      attempts.setText(Integer.toString(counter));
		modify = (Button) findViewById(R.id.button1);
		setListener();
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		modify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String userName = username.getText().toString();
						Log.v("userName = ", userName);
						String passWord = password.getText().toString();
						Log.v("passwd = ", passWord);
						String newPassword = newpassword.getText().toString();
						Log.v("newName = ", newPassword);
						String ackPassword = ackpassword.getText().toString();
						Log.v("ackName = ", ackPassword);
						if (!newPassword.equals(ackPassword)) {
							Toast.makeText(modifypassword.this, "密码不一致", Toast.LENGTH_LONG).show();
						} else {
							url = HTTPUtil.url_constant + url_constant + "username=" + userName + "&password="
									+ passWord + "&newpassword=" + newPassword;
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
								Log.v("json", json);
								Message msg = new Message();
								msg.what = 0x11;
								JSONObject jsonObject = null;
								String result = null;
								try {
									jsonObject = new JSONObject(json);
									result = jsonObject.get("modifypassword").toString();
									if (result == "success") {
										// myUser = jsonObject.get("user").toString();
									}
									Log.v("result", result);
									Log.v("user", jsonObject.toString());
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								Bundle data = new Bundle();
								data.putString("result", json);
								data.putString("password", newPassword);
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
									String result = (String) json.get("modifypassword");
									if ("success".equals(result)) {
//										MainActivity.USERNAME = data.getString("username");
//										USERNAME.setText(MainActivity.USERNAME);
										Toast.makeText(modifypassword.this, "修改成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(modifypassword.this, "修改失败", Toast.LENGTH_LONG).show();
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