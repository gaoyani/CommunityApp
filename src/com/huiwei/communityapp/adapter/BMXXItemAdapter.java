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
import com.huiwei.communityapp.info.BMXXInfo;
import com.huiwei.communityapp.info.NewsInfo;

public class BMXXItemAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private Context mContext;
	private List<BMXXInfo> bmxxInfoList;

	public BMXXItemAdapter(Context context, List<BMXXInfo> bmxxInfoList) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		this.bmxxInfoList = bmxxInfoList;
	}
	
	public void setList(List<BMXXInfo> bmxxInfoList) {
		this.bmxxInfoList = bmxxInfoList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bmxxInfoList.size();
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
			convertView = mInflater.inflate(R.layout.bmxx_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.phoneNumber = (TextView) convertView.findViewById(R.id.tv_phone_number);
			viewHolder.call = (Button) convertView.findViewById(R.id.btn_call);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		BMXXInfo info = bmxxInfoList.get(position);
		viewHolder.name.setText(info.name);
//		viewHolder.phoneNumber.setText(info.phoneNumber);
		
		viewHolder.call.setText(info.phoneNumber);
		viewHolder.call.setTag(position);
		viewHolder.call.setOnClickListener(onClickListener);
	
		return convertView;
	}
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int index = (Integer) v.getTag();
			BMXXInfo info = bmxxInfoList.get(index);

			Intent intent = new Intent(Intent.ACTION_CALL, 
					Uri.parse("tel:"+info.phoneNumber));    
			mContext.startActivity(intent);
		}
	};
	
	public static class ViewHolder {
		TextView name;
		TextView phoneNumber;
		Button call;
	}
}
