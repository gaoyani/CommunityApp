package com.huiwei.communityapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.info.NewsInfo;

public class NewsItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<NewsInfo> newsInfoList;

	public NewsItemAdapter(Context context, List<NewsInfo> newsInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.newsInfoList = newsInfoList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsInfoList.size();
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
			convertView = mInflater.inflate(R.layout.news_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.detial = (TextView) convertView.findViewById(R.id.tv_detail);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		NewsInfo info = newsInfoList.get(position);
		viewHolder.title.setText(info.title);
		viewHolder.detial.setText(info.detail);
	
		return convertView;
	}
	
	public static class ViewHolder {
		TextView title;
		TextView detial;
	}
}
