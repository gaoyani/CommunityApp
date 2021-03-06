package com.huiwei.communityapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiwei.communityapp.R;

public class LoadingView extends RelativeLayout {
	
	private Context context;
	private TextView loadingText;
	
	public LoadingView(Context context) {
		super(context);
		this.context = context;
	}
	
	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		
		loadingText = (TextView)findViewById(R.id.tv_text);
	}
	
	public void setLoadingText(String str) {
		loadingText.setText(str);
	}
}
	
