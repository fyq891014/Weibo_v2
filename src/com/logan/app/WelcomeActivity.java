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
package com.logan.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.logan.R;
import com.logan.util.Utils;
import com.logan.weibo.bean.BaseActivity;
import com.logan.weibo.ui.AccountActivity;
import com.logan.weibo.ui.QAuthorizeActivity;
import com.weibo.net.WeiboException;

import java.io.IOException;

/**
 * 欢迎界面
 *
 * @author Logan
 * @version 1.1
 */
public class WelcomeActivity extends BaseActivity {

    public static final int SETTING_WIFI = 0;
    private Boolean isDBAvaliable = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!Utils.isNetworkOn()) {
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setTitle("无连接")
                                .setMessage("请连接网络")
                                .setPositiveButton("设置",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), SETTING_WIFI);
                                                finish();
                                            }
                                        })
                                .setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }

                                        }).create().show();

                        return;
                    }

                    if (isDBAvaliable) {
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this, AccountActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this, QAuthorizeActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayout());
        checkDBAvailabe();
        showSplashAnimations();

    }

    private void checkDBAvailabe() {
        if (mDBManager.getAccounts().size() != 0)
            isDBAvaliable = true;
    }

    private void showSplashAnimations() {
        SplashAnimationTask task = new SplashAnimationTask(handler);
        Thread thread = new Thread(task);
        thread.start();
    }

    private class SplashAnimationTask implements Runnable {
        Handler handler;
        long startTime = System.currentTimeMillis();
        long endTime;

        public SplashAnimationTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            endTime = startTime;
            while (endTime - startTime < 2000) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //TODO notify ui
                    e.printStackTrace();
                }
                endTime = System.currentTimeMillis();
            }
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.welcome_activity;
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