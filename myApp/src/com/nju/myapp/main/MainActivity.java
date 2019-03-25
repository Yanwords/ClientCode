package com.nju.myapp.main;

import com.nju.myapp.R;
import com.nju.myapp.R.id;
import com.nju.myapp.R.layout;
import com.nju.myapp.R.menu;
import com.nju.myapp.contact.ContactsFragment;
import com.nju.myapp.message.MessageFragment;
import com.nju.myapp.news.NewsFragment;
import com.nju.myapp.setting.SettingFragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
//import android.support.v7.appcompat.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {
   //private static String url = "http://114.212.87.161:8080/myServer/user/login.do?";
   //private final String url_constant = "http://114.212.87.161:8080/myServer/user/login.do?";
   //private EditText  username=null;
   //private EditText  password=null;
 
	public static String USERNAME = "";
   
//	@SuppressLint("NewApi")
//   private TextView welcome;
   /*
   private Button login;
   private Button register;
   private Button scenicSelect;
   private Button scenicInsert;
   private Button scenicDisplay;
   private Button travelRecord;
   private Button shareExperience;
   private Button scenicEvaluation;
   private Button addFriend;
   */
	/**
	* 用于展示消息的Fragment
	*/
	private MessageFragment messageFragment;
	 
	/**
	* 用于展示联系人的Fragment
	*/
	private ContactsFragment contactsFragment;
	 
	/**
	* 用于展示动态的Fragment
	*/
	private NewsFragment newsFragment;
	 
	/**
	* 用于展示设置的Fragment
	*/
	private SettingFragment settingFragment;
	 
	/**
	* 消息界面布局
	*/
	private View messageLayout;
	 
	/**
	* 联系人界面布局
	*/
	private View contactsLayout;
	 
	/**
	* 动态界面布局
	*/
	private View newsLayout;
	 
	/**
	* 设置界面布局
	*/
	private View settingLayout;
	/**
	* 用于对Fragment进行管理
	*/
	private FragmentManager fragmentManager;
//   @SuppressLint("NewApi")
@Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      /*
      setContentView(R.layout.activity_main);
      
      login = (Button)findViewById(R.id.button1);
//      welcome = (TextView)findViewById(R.id.textView1);
      register = (Button)findViewById(R.id.button2);
      scenicSelect = (Button)findViewById(R.id.button3);
      scenicInsert = (Button)findViewById(R.id.button4);
      scenicDisplay = (Button)findViewById(R.id.button5);
      travelRecord = (Button)findViewById(R.id.button6);
      shareExperience = (Button)findViewById(R.id.button7);
      scenicEvaluation = (Button)findViewById(R.id.button8);
      addFriend = (Button)findViewById(R.id.button9);
      setListener();
      */
      setContentView(R.layout.main_layout);
      initViews();
      fragmentManager = getFragmentManager();
      // 第一次启动时选中第0个tab
      setTabSelection(0);
   }
   /**
   * 设置事件的监听器的方法
   */
   /*
   private void setListener() {
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, login.class);
        	  startActivity(intent);
          }
      });
      register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, register.class);
        	  startActivity(intent);
          }
      });
      scenicSelect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, scenicselect.class);
        	  startActivity(intent);
          }
      });
      scenicInsert.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, scenicinsert.class);
        	  startActivity(intent);
          }
      });
      scenicDisplay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, scenicdisplay.class);
        	  startActivity(intent);
          }
      });
      travelRecord.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, travelrecord.class);
        	  startActivity(intent);
          }
      });
      shareExperience.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, shareexperience.class);
        	  startActivity(intent);
          }
      });
      scenicEvaluation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, scenicevaluation.class);
        	  startActivity(intent);
          }
      });
      addFriend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        	  Intent intent = new Intent(MainActivity.this, addfriend.class);
        	  startActivity(intent);
          }
      });
   }
   */


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu, menu);
      return true;
   }
   
   /**
   * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
   */
    
   private void initViews() {
         messageLayout = findViewById(R.id.message_layout);
         contactsLayout = findViewById(R.id.contacts_layout);
         newsLayout = findViewById(R.id.news_layout);
         settingLayout = findViewById(R.id.setting_layout);
         messageLayout.setOnClickListener(this);
         contactsLayout.setOnClickListener(this);
         newsLayout.setOnClickListener(this);
         settingLayout.setOnClickListener(this);
   }
    
   @Override
   public void onClick(View v) {
        switch (v.getId()) {
        case R.id.message_layout:
            // 当点击了消息tab时，选中第1个tab
            setTabSelection(0);
            break;
         case R.id.contacts_layout:
             // 当点击了联系人tab时，选中第2个tab
             setTabSelection(1);
             break;
         case R.id.news_layout:
               // 当点击了动态tab时，选中第3个tab
               setTabSelection(2);
               break;
         case R.id.setting_layout:
                // 当点击了设置tab时，选中第4个tab
               setTabSelection(3);
                break;
         default:
           break;
   }
    
   }
    
   /**
   * 根据传入的index参数来设置选中的tab页。
   *
   * @param index
   * 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
   */
   private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
        case 0:
             messageLayout.setBackgroundColor(0xff0000ff);
    
             if (messageFragment == null) {
                 // 如果MessageFragment为空，则创建一个并添加到界面上
                  messageFragment = new MessageFragment();
                 transaction.add(R.id.content, messageFragment);
             } else {
                 // 如果MessageFragment不为空，则直接将它显示出来
                 transaction.show(messageFragment);
             }
             break;
        case 1:
          // 当点击了联系人tab时，改变控件的图片和文字颜色
           contactsLayout.setBackgroundColor(0xff0000ff);
          if (contactsFragment == null) {
             // 如果ContactsFragment为空，则创建一个并添加到界面上
             contactsFragment = new ContactsFragment();
             transaction.add(R.id.content, contactsFragment);
          } else {
            // 如果ContactsFragment不为空，则直接将它显示出来
             transaction.show(contactsFragment);
           }
           break;
        case 2:
           // 当点击了动态tab时，改变控件的图片和文字颜色
            newsLayout.setBackgroundColor(0xff0000ff);
            if (newsFragment == null) {
               // 如果NewsFragment为空，则创建一个并添加到界面上
                newsFragment = new NewsFragment();
                transaction.add(R.id.content, newsFragment);
            } else {
              // 如果NewsFragment不为空，则直接将它显示出来
               transaction.show(newsFragment);
            }
            break;
         case 3:
             default:
             // 当点击了设置tab时，改变控件的图片和文字颜色
             settingLayout.setBackgroundColor(0xff0000ff);
             if (settingFragment == null) {
             // 如果SettingFragment为空，则创建一个并添加到界面上
             settingFragment = new SettingFragment();
             transaction.add(R.id.content, settingFragment);
             } else {
             // 如果SettingFragment不为空，则直接将它显示出来
             transaction.show(settingFragment);
            }
           break;
      }
     transaction.commit();
   }
    
   /**
   * 将所有的Fragment都置为隐藏状态。
   *
   * @param transaction
   * 用于对Fragment执行操作的事务
   */
   private void hideFragments(FragmentTransaction transaction) {
         if (messageFragment != null) {
             transaction.hide(messageFragment);
         }
          if (contactsFragment != null) {
             transaction.hide(contactsFragment);
         }
          if (newsFragment != null) {
              transaction.hide(newsFragment);
         }
         if (settingFragment != null) {
            transaction.hide(settingFragment);
         }
   }
    
   /**
   * 清除掉所有的选中状态。
   */
   private void clearSelection() {
         messageLayout.setBackgroundColor(0xffffffff);
         contactsLayout.setBackgroundColor(0xffffffff);
         newsLayout.setBackgroundColor(0xffffffff);
         settingLayout.setBackgroundColor(0xffffffff);
         }

}