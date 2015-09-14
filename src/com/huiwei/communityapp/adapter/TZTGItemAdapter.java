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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.info.ZWXXInfo;

public class TZTGItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<TZTGInfo> tztgInfoList;

	public TZTGItemAdapter(Context context, List<TZTGInfo> tztgInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.tztgInfoList = tztgInfoList;
	}
	
	public void setList(List<TZTGInfo> tztgInfoList) {
		this.tztgInfoList = tztgInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (tztgInfoList == null) {
			return 0;
		}
		return tztgInfoList.size();
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
			convertView = mInflater.inflate(R.layout.tztg_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		TZTGInfo info = tztgInfoList.get(position);
		viewHolder.time.setText(info.time);
		viewHolder.title.setText(info.title);
	
		return convertView;
	}
	
	public static class ViewHolder {
		TextView time;
		TextView title;
	}
}
