package com.huiwei.communityapp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import com.huiwei.communityapp.adapter.BMXXItemAdapter;
import com.huiwei.communityapp.info.BMXXInfo;
import com.huiwei.communityapp.task.BMXXListTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.view.LoadingView;

public class BMXXActivity extends Activity implements OnClickListener {
	
	private LoadingView loadingView;
	private ListView listView;
	private BMXXItemAdapter adapter;
	
	List<BMXXInfo> bmxxList = new ArrayList<BMXXInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmxx);
		
		loadingView = (LoadingView)findViewById(R.id.view_loading);
		listView = (ListView)findViewById(R.id.listView);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);

		initListView();
		
		BMXXListTask task = new BMXXListTask(this, handler);
		task.execute();
	}

	private void initListView() {
		adapter = new BMXXItemAdapter(this, bmxxList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
			}
		});
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				List<BMXXInfo> obj = (List<BMXXInfo>) msg.obj;
				bmxxList = obj;
				adapter.setList(bmxxList);
			} else {
				Toast.makeText(BMXXActivity.this, R.string.get_bmxx_error, Toast.LENGTH_SHORT).show();
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
