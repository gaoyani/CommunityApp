/*****************************************************
 * Copyright(c)2014-2015 北京汇为永兴科技有限公司
 * OrderFragment.java
 * 创建人：高亚妮
 * 日     期：2014-6-20
 * 描     述：订单页面显示及操作文件
 * 版     本：v6.0
 *****************************************************/
package com.huiwei.communityapp.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.task.LogoutTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.view.LoadingView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserCenterActivity extends Activity implements OnClickListener {
	
	public static final int UNCONFIRM_LIST = 0;
	public static final int CONFIRM_LIST = 1;
	
	private TextView realName, userName, phontNumber, email, address, community;
	
	private LoadingView loadingView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_center);
		
		realName = (TextView)findViewById(R.id.tv_real_name);
		userName = (TextView)findViewById(R.id.tv_user_name);
		phontNumber = (TextView)findViewById(R.id.tv_phone_number);
		email = (TextView)findViewById(R.id.tv_email);
		address = (TextView)findViewById(R.id.tv_address);
		community = (TextView)findViewById(R.id.tv_community);
		loadingView = (LoadingView)findViewById(R.id.loading_view);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);
		((Button)findViewById(R.id.button_logout)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.layout_order)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.layout_edit_password)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.layout_setting)).setOnClickListener(this);
		
		setInfo();
	}
	
	@Override
	public void onResume() {
//		startSyncOrderTimer();
		super.onResume();
	}
	
	@Override
	public void onPause() {
//		stopSyncOrder();
		super.onPause();
	}
	
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (msg.what == Constants.SUCCESS) {
				
			}
		};
	};
	
	private void setInfo() {
		realName.setText(Data.memberInfo.realName);
		userName.setText("用户名："+Data.memberInfo.userName);
		phontNumber.setText("联系电话："+Data.memberInfo.phontNumber);
		email.setText("email："+Data.memberInfo.email);
		address.setText("联系地址："+Data.memberInfo.address);
		community.setText("所在社区："+Data.findCommunityInfo(Data.memberInfo.communityID).name);
	}
	
	//按钮的点击消息监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;
			
		case R.id.button_logout: {
			LogoutTask task = new LogoutTask(UserCenterActivity.this, logoutHandler);
			task.execute();
		}
			break;
			
		case R.id.layout_order:
			Intent intent = new Intent();
			intent.setClass(UserCenterActivity.this, OrderManagerActivity.class);
			startActivity(intent);
			break;
			
		case R.id.layout_edit_password:
			break;
			
		case R.id.layout_setting:
			break;
		
		default:
			break;
		}
	}
	
	private Handler logoutHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (msg.what == Constants.SUCCESS) {
//				Intent intent = new Intent();
//				intent.setClass(UserCenterActivity.this, LoginActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
				finish();
			} else {
				Toast.makeText(UserCenterActivity.this, R.string.logout_error, Toast.LENGTH_SHORT).show();
			}
		};
	};
}
