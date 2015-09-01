package com.huiwei.communityapp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.adapter.ZWXXItemAdapter;
import com.huiwei.communityapp.info.ZWXXInfo;
import com.huiwei.communityapp.task.ZWXXDetailTask;
import com.huiwei.communityapp.task.ZWXXListTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;
import com.huiwei.communityapp.view.LoadingView;

public class ZWXXActivity extends Activity implements OnClickListener {
	
	private LoadingView loadingView;
	private ListView listView;
	private ZWXXItemAdapter adapter;
	
	List<ZWXXInfo> zwxxList = new ArrayList<ZWXXInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zwxx);
		
		loadingView = (LoadingView)findViewById(R.id.view_loading);
		listView = (ListView)findViewById(R.id.listView);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);

		initListView();
		
		ZWXXListTask task = new ZWXXListTask(this, handler);
		task.execute();
	}

	private void initListView() {
		adapter = new ZWXXItemAdapter(this, zwxxList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ZWXXInfo info = zwxxList.get(position);
				
				Intent intent = new Intent();
				intent.setClass(ZWXXActivity.this, WebActivity.class);
				intent.putExtra("id", info.id);
				startActivity(intent);
			}
		});
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				List<ZWXXInfo> obj = (List<ZWXXInfo>) msg.obj;
				zwxxList = obj;
				adapter.setList(zwxxList);
			} else {
				Toast.makeText(ZWXXActivity.this, R.string.get_zwxx_error, Toast.LENGTH_SHORT).show();
			}
			
			loadingView.setVisibility(View.GONE);
		};
	};
	
	@Override
	public void onResume() {
		
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;

		default:
			break;
		}
		
	}
}
