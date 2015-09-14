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
import com.huiwei.communityapp.adapter.DCWJItemAdapter;
import com.huiwei.communityapp.info.BMXXInfo;
import com.huiwei.communityapp.info.DCWJInfo;
import com.huiwei.communityapp.task.DCWJListTask;
import com.huiwei.communityapp.utils.Constants;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DCWJActivity extends Activity implements OnClickListener {
	
	public static final int DOING_LIST = 0;
	public static final int HISTORY_LIST = 1;
	
	private ListView listView;
	private ImageView tabDoingBG, tabHistoryBG;
	private LoadingView loadingView;
	private DCWJItemAdapter adapter;
	private int type = DOING_LIST;
	
	private List<DCWJInfo> doingDCWJList = new ArrayList<DCWJInfo>();
	private List<DCWJInfo> historyDCWJList = new ArrayList<DCWJInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dcwj);
	
		loadingView = (LoadingView)findViewById(R.id.view_loading);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);
		
		initGroupBtns();
		initListView();
		
		DCWJListTask task = new DCWJListTask(this, handler);
		task.execute();
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
	
	private void initGroupBtns() {
		((TextView)findViewById(R.id.tv_doing)).setOnClickListener(this);
		((TextView)findViewById(R.id.tv_history)).setOnClickListener(this);
		
		tabDoingBG = (ImageView)findViewById(R.id.iv_doing);
		tabHistoryBG = (ImageView)findViewById(R.id.iv_history);
	}
	
	
	private void initListView() {
		listView = (ListView)findViewById(R.id.listView);
		adapter = new DCWJItemAdapter(this, doingDCWJList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DCWJInfo selDCWJInfo = doingDCWJList.get(position);
				if (type == HISTORY_LIST) {
					selDCWJInfo = historyDCWJList.get(position);
				}
				
				Intent intent = new Intent();
				intent.putExtra("id", selDCWJInfo.id);
				intent.putExtra("web_type", Constants.WEB_DCWJ);
				intent.setClass(DCWJActivity.this, WebActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (msg.what == Constants.SUCCESS) {
				List<List<DCWJInfo>> obj = (List<List<DCWJInfo>>) msg.obj;
				doingDCWJList = obj.get(0);
				historyDCWJList = obj.get(1);
				updateListView();
			} else {
				Toast.makeText(DCWJActivity.this, R.string.get_dcwj_error, Toast.LENGTH_SHORT).show();
			}
			
			loadingView.setVisibility(View.GONE);
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;
			
		case R.id.tv_doing: {
			type = DOING_LIST;
			updateListView();
			resetTypeBtn();
		}
			break;
		case R.id.tv_history: {
			type = HISTORY_LIST;
			updateListView();
			resetTypeBtn();
		}
			break;

		default:
			break;
		}
	}
	
	private void resetTypeBtn() {
		tabDoingBG.setBackgroundColor(getResources().getColor(type == DOING_LIST ? 
				R.color.tab_selected : R.color.tab_normal));
		tabHistoryBG.setBackgroundColor(getResources().getColor(type == HISTORY_LIST ? 
				R.color.tab_selected : R.color.tab_normal));
	}
	
	private void updateListView() {
		if (type == DOING_LIST) {
			adapter.setList(doingDCWJList);
		} else {
			adapter.setList(historyDCWJList);
		}
	}
}
