/**   
 * Copyright (c) 2013 by Logan.	
 *   
 * 爱分享-微博客户端，是一款运行在android手机上的开源应用，代码和文档已托管在GitHub上，欢迎爱好者加入
 * 1.授权认证：Oauth2.0认证流程
 * 2.服务器访问操作流程
 * 3.新浪微博SDK和腾讯微博SDK
 * 4.HMAC加密算法
 * 5.SQLite数据库相关操作
 * 6.字符串处理，表情识别
 * 7.JSON解析，XML解析：超链接解析，时间解析等
 * 8.Android UI：样式文件，布局
 * 9.异步加载图片，异步处理数据，多线程  
 * 10.第三方开源框架和插件
 *    
 */
package com.logan.weibo.ui;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.logan.R;
import com.logan.weibo.bean.BaseActivity;
import com.logan.weibo.ui.more.AboutAcitvity;
import com.logan.weibo.ui.more.AccountAcitvity;
import com.logan.weibo.ui.more.FeedbackActivity;
import com.logan.weibo.ui.more.HelpAcitvity;
import com.logan.weibo.ui.more.SettingActivity;
import com.logan.weibo.ui.more.ThemeDialog;
import com.weibo.net.WeiboException;
/**
 * 更多模块
 * @author Logan <a href="https://github.com/Logan676/JustSharePro"/>
 *   
 * @version 1.0 
 *  
 */
public class More extends BaseActivity {
	

	private final String TAG = "More";
	// ----------头部工具栏-----------------------
	private ImageView tweet = null;

	//private ImageView refreshBtn = null;

	private TextView title = null;

	// ----------底部导航栏------------------------
	private View friendTimeLine;

	private View userTimeLine;

	private View userNews;

	private View userInfo;

	private View more;

	private RelativeLayout mycount = null;

	private RelativeLayout theme = null;

	private CheckBox picture = null;

	private RelativeLayout weibo = null;

	private RelativeLayout setting = null;

	private RelativeLayout feedback = null;

	private RelativeLayout help = null;

	private RelativeLayout about = null;
	
	//------------标识符-------------------//
	private Boolean sina =false;
	private Boolean ten =false;

	private FooterClickListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		
		initViews();
		initFooter();
		Intent i = getIntent();
		isSina = i.getBooleanExtra("isSina", false);
		isTencent = i.getBooleanExtra("isTencent", false);
		int currentTag = i.getIntExtra("currentTag", 4);
		setSelectedFooterTab(currentTag);//注意把这个函数放在initFooter函数下面执行
		sina = isSina;
		ten = isTencent;
		Log.v(TAG, "On-isSina= "+isSina);
		Log.v(TAG, "On-isTencent= "+isTencent);
	}

	private void initViews() {
		tweet = (ImageView) findViewById(R.id.weibo_headbar_tweet);
		title = (TextView) findViewById(R.id.weibo_headbar_title);
		tweet.setVisibility(View.INVISIBLE);
		title.setText("更多");
		
		mycount = (RelativeLayout) findViewById(R.id.mycount);
		mycount.setOnClickListener(new MycountListener());

		picture = (CheckBox) findViewById(R.id.pictureOrNot);
		picture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {

				} else {

				}
			}
		});

		theme = (RelativeLayout) findViewById(R.id.theme);
		theme.setOnClickListener(new ThemeListener());

		weibo = (RelativeLayout) findViewById(R.id.weibo);
		weibo.setOnClickListener(new WeiboListener());

		setting = (RelativeLayout) findViewById(R.id.setting);
		setting.setOnClickListener(new SettingListener());

		feedback = (RelativeLayout) findViewById(R.id.feedback);
		feedback.setOnClickListener(new FeedbackListener());

		help = (RelativeLayout) findViewById(R.id.help);
		help.setOnClickListener(new HelpListener());

		about = (RelativeLayout) findViewById(R.id.about);
		about.setOnClickListener(new AboutListener());

	}
	private void initFooter(){
		// -----------------底部导航栏---------------------------
		listener = new FooterClickListener();
		friendTimeLine = findViewById(R.id.weibo_menu_friendTimeLine);
		userTimeLine = findViewById(R.id.weibo_menu_userTimeLine);
		userNews = findViewById(R.id.weibo_menu_userNews);
		userInfo = findViewById(R.id.weibo_menu_myInfo);
		more = findViewById(R.id.weibo_menu_more);
		friendTimeLine.setId(0);
		userTimeLine.setId(1);
		userNews.setId(2);
		userInfo.setId(3);
		more.setId(4);
		friendTimeLine.setOnClickListener(listener);
		userTimeLine.setOnClickListener(listener);
		userNews.setOnClickListener(listener);
		userInfo.setOnClickListener(listener);
		more.setOnClickListener(listener);
	}
	
	protected void setSelectedFooterTab(int i) {
		//mCurFooterTab = i;
		friendTimeLine.setBackgroundResource(0);
		userTimeLine.setBackgroundResource(0);
		userNews.setBackgroundResource(0);
		userInfo.setBackgroundResource(0);
		more.setBackgroundResource(0);
		if (i == 0) {friendTimeLine.setBackgroundResource(R.drawable.weibo_menu_cp_bg_selected);title.setText("微博主页");}
		if (i == 1) {userTimeLine.setBackgroundResource(R.drawable.weibo_menu_cp_bg_selected);title.setText("我的微博");}
		if (i == 2) {userNews.setBackgroundResource(R.drawable.weibo_menu_cp_bg_selected);title.setText("微博动态");}
		if (i == 3) userInfo.setBackgroundResource(R.drawable.weibo_menu_cp_bg_selected);
		if (i == 4) more.setBackgroundResource(R.drawable.weibo_menu_cp_bg_selected);
	}
	
	class AboutListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			intent.setClass(More.this, AboutAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	class FeedbackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(More.this, FeedbackActivity.class);
			More.this.startActivity(intent);
		}

	}

	class HelpListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(More.this, HelpAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	class MycountListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(More.this, AccountAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	class SettingListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			intent.setClass(More.this, SettingActivity.class);
			More.this.startActivity(intent);
		}

	}

	class ThemeListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			//System.out.println("theme");
			Intent intent = new Intent();
			intent.setClass(More.this, ThemeDialog.class);
			More.this.startActivity(intent);
		}

	}

	class WeiboListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder updates = new AlertDialog.Builder(More.this);
			updates.setTitle("检查更新");
			updates.setIcon(R.drawable.icon);
			updates.setMessage("更新新版本将会替换以前的版本。" + "\n"
					+ "\n" + "你确定要继续更新吗？");
			updates.setPositiveButton("更新",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialoginterface,
								int which) {
							// Toast.makeText(this, "你选择了更新", Toast.LENGTH_LONG)
							// .show();
						}
					});
			updates.setNegativeButton("不更新",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialoginterface,
								int which) {
							// Toast.makeText(this, "你选择了不更新",
							// Toast.LENGTH_LONG)
							// .show();
						}
					});
			updates.create().show();
		}

	}
	
	@Override
	public int getLayout() {
		
		return R.layout.weibo_more;
	}
	
	private class FooterClickListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent i = new Intent();
			i.putExtra("isSina", sina);
			i.putExtra("isTencent", ten);
			i.putExtra("currentTag", v.getId());
			i.setClass(getApplicationContext(), UserInfo.class);
			startActivity(i);
			finish();
		}
		
	}

	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(WeiboException e) {
		// TODO Auto-generated method stub
		
	}
}
