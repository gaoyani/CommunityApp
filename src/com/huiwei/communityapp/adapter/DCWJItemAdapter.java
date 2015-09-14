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
import com.huiwei.communityapp.info.DCWJInfo;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.info.ZWXXInfo;

public class DCWJItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<DCWJInfo> dcwjInfoList;

	public DCWJItemAdapter(Context context, List<DCWJInfo> dcwjInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.dcwjInfoList = dcwjInfoList;
	}
	
	public void setList(List<DCWJInfo> dcwjInfoList) {
		this.dcwjInfoList = dcwjInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (dcwjInfoList == null) {
			return 0;
		}
		return dcwjInfoList.size();
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
			convertView = mInflater.inflate(R.layout.dcwj_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.endTime = (TextView) convertView.findViewById(R.id.tv_end_time);
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		DCWJInfo info = dcwjInfoList.get(position);
		viewHolder.endTime.setText(mContext.getResources().getString(R.string.end_time)+info.endTime);
		viewHolder.title.setText(info.title);
	
		return convertView;
	}
	
	public static class ViewHolder {
		TextView endTime;
		TextView title;
	}
}
