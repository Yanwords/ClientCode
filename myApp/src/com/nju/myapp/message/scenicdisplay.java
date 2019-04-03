package com.nju.myapp.message;

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
import com.nju.myapp.bottom.BlankFragment;
import com.nju.myapp.main.MainActivity;
import com.nju.myapp.scenicspotlists.ScenicSpot;
import com.nju.myapp.scenicspotlists.ScenicSpotList;
import com.nju.myapp.util.HTTPUtil;
import com.nju.myapp.util.ScenicSpotArray;

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
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
//public class scenicdisplay extends Activity {
public class scenicdisplay extends AppCompatActivity {
	private static String url = "";
	private final String url_constant = "/scenic/display.do?";
	private EditText scenicscore = null;
	@SuppressLint("NewApi")
	private Button display;
//	private TableLayout table;
	int column = 4;
	/* add bottom */
	private RadioGroup mTabRadioGroup;
	private SparseArray<BlankFragment> mFragmentSparseArray;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置线程的策略
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scenicdisplay);
		scenicscore = (EditText) findViewById(R.id.editText1);
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
						String scenicScore = scenicscore.getText().toString();
						if ("".equals(scenicScore)) {
							scenicScore = "0";
						}
//            		  float score = Float.parseFloat(scenicScore);
						Log.v("scenicName", scenicScore);
						// url= url_constant+"scenicname="+scenicName;
						url = HTTPUtil.url_constant + url_constant + "scenicscore=" + scenicScore;
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
							data.putString("scenicscore", scenicScore);
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
									String result = (String) json.get("display");

									if ("success".equals(result)) {
										// Log.v()
										JSONArray array = (JSONArray) json.get("data");
										Log.v("length", Integer.toString(array.length()));
										if (array.length() > 0) {
//											displayTable(array);
											setArray(array);
											Log.v("json:", array.toString());
										}
										Toast.makeText(scenicdisplay.this, "查询信息成功", Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(scenicdisplay.this, "查询信息失败", Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}

						private void setArray(JSONArray array) throws JSONException {
							String[] columnName = { "scenicId", "scenicName", "scenicInfo", "scenicScore" };
							ArrayList<ScenicSpot> a = ScenicSpotArray.getArray();
							a.clear();
							for (int row = 0; row < array.length(); ++row) {
								JSONObject json = array.getJSONObject(row);
//								int id = json.getInt(columnName[0]);
								String name = json.getString(columnName[1]);
								String info = json.getString(columnName[2]);
								String temp = json.getString(columnName[3]);
								float score = Float.parseFloat(temp);
								ScenicSpot ss = new ScenicSpot(row + 1, R.drawable.logo, name, score, info);
								a.add(ss);
//							Spot spot1 = new Spot(1, R.drawable.logo, "胡海", 100);
							}
							Intent intent = new Intent(scenicdisplay.this, ScenicSpotList.class);
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
		mTabRadioGroup = (RadioGroup) findViewById(R.id.tabs_rg);
		mFragmentSparseArray = new SparseArray<BlankFragment>();
		mFragmentSparseArray.append(R.id.today_tab, BlankFragment.newInstance("今日"));
		mFragmentSparseArray.append(R.id.record_tab, BlankFragment.newInstance("记录"));
		mFragmentSparseArray.append(R.id.contact_tab, BlankFragment.newInstance("通讯录"));
		mFragmentSparseArray.append(R.id.settings_tab, BlankFragment.newInstance("设置"));
		mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_container, mFragmentSparseArray.get(checkedId)).commit();
			}
		});
		// 默认显示第一个
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, mFragmentSparseArray.get(R.id.today_tab)).commit();
		findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(scenicdisplay.this, MainActivity.class));
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

/*
 * private void displayTable(JSONArray array) throws JSONException { // TODO
 * Auto-generated method stub String[] columnName = { "scenicId", "scenicName",
 * "scenicInfo", "scenicScore" }; table.removeAllViews(); for (int row = 0; row
 * < array.length(); ++row) { TableRow tableRow = new
 * TableRow(scenicdisplay.this); // tableRow.setBackgroundColor(Color.BLUE);
 * LayoutParams layoutParam = new LayoutParams(); if (layoutParam != null) {
 * layoutParam.setMargins(2, 2, 2, 2); tableRow.setLayoutParams(layoutParam); }
 * //tableRow JSONObject json = array.getJSONObject(row); for (int col = 1; col
 * < column; ++col) { TextView textView = new TextView(scenicdisplay.this);
 * textView.setBackgroundColor(Color.GREEN);
 * textView.setText(json.getString(columnName[col]));
 * textView.setGravity(Gravity.CENTER);
 * textView.setBackgroundResource(R.drawable.abc_tab_indicator_material);
 * tableRow.addView(textView);
 * 
 * LayoutParams lp = new LayoutParams(); if (lp != null) { lp.setMargins(2, 2,
 * 2, 2); textView.setLayoutParams(lp); }
 * 
 * }
 * 
 * table.addView(tableRow, new TableLayout.LayoutParams(100, 150)); }
 * //table.setScrol }
 */