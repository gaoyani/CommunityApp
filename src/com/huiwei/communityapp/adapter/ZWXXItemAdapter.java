package com.huiwei.communityapp.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.info.ZWXXInfo;

public class ZWXXItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<ZWXXInfo> zwxxInfoList;

	public ZWXXItemAdapter(Context context, List<ZWXXInfo> zwxxInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.zwxxInfoList = zwxxInfoList;
	}
	
	public void setList(List<ZWXXInfo> zwxxInfoList) {
		this.zwxxInfoList = zwxxInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zwxxInfoList.size();
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
			convertView = mInflater.inflate(R.layout.zwxx_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		ZWXXInfo info = zwxxInfoList.get(position);
		viewHolder.time.setText(info.time);
		viewHolder.title.setText(info.title);
		viewHolder.content.setText(info.content);
	
		return convertView;
	}
	
	public static class ViewHolder {
		TextView time;
		TextView title;
		TextView content;
	}
}
