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
import com.huiwei.communityapp.adapter.TZTGItemAdapter;
import com.huiwei.communityapp.adapter.TZTGTypeItemAdapter;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.info.TZTGTypeInfo;
import com.huiwei.communityapp.task.TZTGListTask;
import com.huiwei.communityapp.task.TZTGTypeListTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.view.LoadingView;

public class TZTGListActivity extends Activity implements OnClickListener {
	
	private LoadingView loadingView;
	private ListView listView;
	private TZTGTypeItemAdapter adapter;
	
	List<TZTGTypeInfo> tztgTypeList = new ArrayList<TZTGTypeInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tztg_list);
		
		loadingView = (LoadingView)findViewById(R.id.view_loading);
		listView = (ListView)findViewById(R.id.listView);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);

		initListView();
		
		TZTGTypeListTask task = new TZTGTypeListTask(this, handler);
		task.execute();
	}

	private void initListView() {
		adapter = new TZTGTypeItemAdapter(this, tztgTypeList);
		listView.setAdapter(adapter);
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				List<TZTGTypeInfo> obj = (List<TZTGTypeInfo>) msg.obj;
				tztgTypeList = obj;
				adapter.setList(tztgTypeList);
			} else {
				Toast.makeText(TZTGListActivity.this, R.string.get_tztg_error, Toast.LENGTH_SHORT).show();
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
