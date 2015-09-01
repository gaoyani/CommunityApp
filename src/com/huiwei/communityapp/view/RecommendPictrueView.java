package com.huiwei.communityapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.huiwei.communityapp.R;

public class RecommendPictrueView extends RelativeLayout {
	
	private Context context;
	
	public RecommendPictrueView(Context context) {
		super(context);
		this.context = context;
	}
	
	public RecommendPictrueView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void setInfo(int resourceID, String title) {
		((ImageView)findViewById(R.id.iv_picture)).setScaleType(ScaleType.CENTER_CROP);
		((ImageView)findViewById(R.id.iv_picture)).setBackgroundResource(resourceID);
		((TextView)findViewById(R.id.tv_title)).setText(title);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
	
	
}
	
