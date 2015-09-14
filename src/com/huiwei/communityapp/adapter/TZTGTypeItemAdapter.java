package com.huiwei.communityapp.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.activity.TZTGDetailActivity;
import com.huiwei.communityapp.activity.TZTGListActivity;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.info.TZTGTypeInfo;
import com.huiwei.communityapp.task.TZTGListTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.view.ListViewForScrollView;

public class TZTGTypeItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<TZTGTypeInfo> tztgTypeInfoList;

	public TZTGTypeItemAdapter(Context context, List<TZTGTypeInfo> tztgTypeInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.tztgTypeInfoList = tztgTypeInfoList;
	}
	
	public void setList(List<TZTGTypeInfo> tztgTypeInfoList) {
		this.tztgTypeInfoList = tztgTypeInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tztgTypeInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.tztg_type_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.type = (Button) convertView.findViewById(R.id.button_type);
			viewHolder.listView = (ListViewForScrollView) convertView.findViewById(R.id.listView);
			viewHolder.pb = (ProgressBar) convertView.findViewById(R.id.pb);
			viewHolder.adapter = new TZTGItemAdapter(mContext, null);
			viewHolder.listView.setAdapter(viewHolder.adapter);
			viewHolder.listView.setOnItemClickListener(itemClickListener);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		TZTGTypeInfo info = tztgTypeInfoList.get(position);
		viewHolder.type.setText(info.name);
		viewHolder.listView.setTag(position);
		viewHolder.listView.setVisibility(info.isListVisible ? View.VISIBLE : View.GONE);
		viewHolder.pb.setVisibility(info.isLoadingList ? View.VISIBLE : View.GONE);
		if (info.isListVisible) {
			viewHolder.adapter.setList(info.tztgList);
		}
		
		viewHolder.type.setTag(position);
		viewHolder.type.setOnClickListener(clickListener);
	
		return convertView;
	} 
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			TZTGTypeInfo info = tztgTypeInfoList.get((Integer) v.getTag());
			info.isListVisible = !info.isListVisible;
			if (info.isListVisible) {
				info.isLoadingList = true;
				TZTGListTask task = new TZTGListTask(mContext, handler, info.id, (Integer)v.getTag());
				task.execute();
			}
			
			notifyDataSetChanged();
		}
	};
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			TZTGTypeInfo info = tztgTypeInfoList.get((Integer) parent.getTag());
			Intent intent = new Intent();
			intent.putExtra("id", info.tztgList.get(position).id);
			intent.setClass(mContext, TZTGDetailActivity.class);
			mContext.startActivity(intent);
		}
	};
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			TZTGTypeInfo info = tztgTypeInfoList.get(msg.arg1);
			if (msg.what == Constants.SUCCESS) {
				List<TZTGInfo> obj = (List<TZTGInfo>) msg.obj;
				info.tztgList = obj;
			} else {
				Toast.makeText(mContext, R.string.get_tztg_error, Toast.LENGTH_SHORT).show();
			}
			
			info.isLoadingList = false;
			notifyDataSetChanged();
		};
	};
	
	public static class ViewHolder {
		Button type;
		ListViewForScrollView listView;
		TZTGItemAdapter adapter;
		ProgressBar pb;
	}
}
