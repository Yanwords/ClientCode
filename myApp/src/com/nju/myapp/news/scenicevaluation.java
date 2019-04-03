package com.nju.myapp.news;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class scenicevaluation extends Activity {
	private static String url = "";
	private final String url_constant = "/scenic/insertEvaluation.do?";

	@SuppressLint("NewApi")
	private EditText scenicName = null;
	private EditText evaluationContent = null;
	private Button evaluation;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenicevaluation);
		scenicName = (EditText) findViewById(R.id.editText1);
		evaluationContent = (EditText) findViewById(R.id.editText2);
		evaluation = (Button) findViewById(R.id.button1);
		setListener();
	}

	/**
	 * 设置事件的监听器的方法
	 */
	private void setListener() {
		evaluation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String scenicname = scenicName.getText().toString();
						String evalInfo = evaluationContent.getText().toString();
						Log.v("evaluationInfo = ", evalInfo);
						try {
							evalInfo = URLEncoder.encode(evalInfo, "utf-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String nowDate = format.format(date);
						// url= url_constant+"scenicname="+scenicName;
						url = HTTPUtil.url_constant + url_constant + "evalinfo=" + evalInfo + "&evaldate=" + nowDate
								+ "&username=" + MainActivity.USERNAME + "&scenicname=" + scenicname;
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
							data.putString("evalinfo", evalInfo);
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
									String result = (String) json.get("evaluation");

									if ("success".equals(result)) {
										// Log.v()
										Toast.makeText(scenicevaluation.this, "景点评价成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(scenicevaluation.this, "景点评价失败", Toast.LENGTH_LONG).show();
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

/**
 * 获取Struts2 Http 登录的请求信息
 * 
 * @param userName
 * @param password
 */
/*
 * public void loginRemoteService(String userName,String password){ String
 * result=null; try { //创建一个HttpClient对象 HttpClient httpclient = new
 * DefaultHttpClient(); //远程登录URL //下面这句是原有的
 * //processURL=processURL+"userName="+userName+"&password="+password; url=
 * url_constant+"username="+userName+"&password="+password; Log.d("远程URL", url);
 * //创建HttpGet对象 HttpGet request=new HttpGet(url);
 * //请求信息类型MIME每种响应类型的输出（普通文本、html 和 XML，json）。允许的响应类型应当匹配资源类中生成的 MIME 类型
 * //资源类生成的 MIME 类型应当匹配一种可接受的 MIME 类型。如果生成的 MIME 类型和可接受的 MIME 类型不 匹配，那么将 //生成
 * com.sun.jersey.api.client.UniformInterfaceException。例如，将可接受的 MIME 类型设置为
 * text/xml，而将 //生成的 MIME 类型设置为 application/xml。将生成 UniformInterfaceException。
 * request.addHeader("Accept","text/json"); Log.d("request",
 * request.toString()); //获取响应的结果 HttpResponse response
 * =httpclient.execute(request); //获取HttpEntity HttpEntity
 * entity=response.getEntity(); //获取响应的结果信息 String json
 * =EntityUtils.toString(entity,"UTF-8"); String myUser = ""; //JSON的解析过程
 * if(json!=null){
 * 
 * } if(result==null){ json="登录失败请重新登录"; }
 * 
 * //创建提示框提醒是否登录成功 AlertDialog.Builder builder=new Builder(scenicdisplay.this);
 * builder.setTitle("提示") .setMessage(myUser) .setPositiveButton("确定", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * dialog.dismiss(); } }).create().show(); } catch (ClientProtocolException e) {
 * // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException
 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
 */