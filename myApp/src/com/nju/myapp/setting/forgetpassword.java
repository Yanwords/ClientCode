package com.nju.myapp.setting;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class forgetpassword extends Activity {
	private static String url = "";
	private final String url_constant = "/user/reset.do?";
	private EditText username = null;
	private EditText gender = null;
	private EditText age = null;
	private TextView USERNAME = null;
	private EditText newpassword = null;
	private EditText ackpassword = null;

	@SuppressLint("NewApi")
	private Button reset;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgetpassword);
		username = (EditText) findViewById(R.id.editText1);
		gender = (EditText) findViewById(R.id.editText2);
		age = (EditText) findViewById(R.id.editText3);
		USERNAME = (TextView) findViewById(R.id.username);
		newpassword = (EditText) findViewById(R.id.editText4);
		ackpassword = (EditText) findViewById(R.id.editText5);
		reset = (Button) findViewById(R.id.button1);
		initView();
		setListener();
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					@Override
					public void run() {
						String userName = username.getText().toString();
						Log.v("userName = ", userName);
						String userGender = gender.getText().toString();
						Log.v("gender = ", userGender);
						String userAge = age.getText().toString();
						Log.v("age = ", userAge);
						String newPassword = newpassword.getText().toString();
						Log.v("newpass=", newPassword);
						String ackPassword = ackpassword.getText().toString();
						Log.v("ackpass=", ackPassword);
						if (!newPassword.equals(ackPassword)) {
							Toast.makeText(forgetpassword.this, "密码不一致", Toast.LENGTH_LONG).show();
						} else {
							try {
								userGender = URLEncoder.encode(userGender, "utf-8");
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							url = HTTPUtil.url_constant + url_constant + "username=" + userName + "&newpassword="
									+ newPassword + "&gender=" + userGender + "&age=" + userAge;
							Log.d("远程URL", url);
							@SuppressWarnings("resource")
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost request = new HttpPost(url);
							request.addHeader("Accept", "text/json");
							request.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
							Log.d("request", request.toString());

							// JSON的解析过程

							try {
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
									String result = (String) json.get("reset");
									if ("success".equals(result)) {
										Toast.makeText(forgetpassword.this, "重置成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(forgetpassword.this, "重置失败", Toast.LENGTH_LONG).show();
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
				startActivity(new Intent(forgetpassword.this, MainActivity.class));
			}
		});
	}
}
/**
 * 获取Struts2 Http 登录的请求信息
 * 
 * @param userName
 * @param password
 */
/*
 * public void registerRemoteService(String userName,String password, String
 * gender, String age){ String result=null; try { //创建一个HttpClient对象
 * 
 * @SuppressWarnings("resource") HttpClient httpclient = new
 * DefaultHttpClient(); //远程登录URL //下面这句是原有的
 * //processURL=processURL+"userName="+userName+"&password="+password; gender =
 * URLEncoder.encode(gender, "utf-8"); url=
 * url_constant+"username="+userName+"&password="+password+"&gender="+gender+
 * "&age="+age; Log.d("远程URL", url); //创建HttpGet对象 // HttpGet request=new
 * HttpGet(url); HttpPost request = new HttpPost(url);
 * //请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的 MIME 类型
 * //资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不 匹配，那么将 //生成
 * com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的 MIME 类型设置为
 * text/xml，而将 //生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
 * request.addHeader("Accept","text/json"); request.addHeader(
 * "Content-type","application/x-www-form-urlencoded; charset=utf-8");
 * Log.d("request", request.toString()); //获取响应的结果 HttpResponse response
 * =httpclient.execute(request); //获取HttpEntity HttpEntity
 * entity=response.getEntity(); //获取响应的结果信息 String json
 * =EntityUtils.toString(entity,"UTF-8"); //JSON的解析过程 if(json!=null){ JSONObject
 * jsonObject=new JSONObject(json);
 * result=jsonObject.get("register").toString(); } if(result==null){
 * json="登录失败请重新登录"; } //创建提示框提醒是否登录成功 AlertDialog.Builder builder=new
 * Builder(register.this); builder.setTitle("提示") .setMessage(result)
 * .setPositiveButton("确定", new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * dialog.dismiss(); } }).create().show(); } catch (ClientProtocolException e) {
 * // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException
 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
 * (JSONException e) { // TODO Auto-generated catch block e.printStackTrace(); }
 * }
 */