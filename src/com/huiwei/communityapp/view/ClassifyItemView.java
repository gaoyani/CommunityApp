package com.huiwei.communityapp.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.activity.BMXXActivity;
import com.huiwei.communityapp.activity.ZWXXActivity;

public class ClassifyItemView extends RelativeLayout {
	
	public final static int CLASSIFY_ZWXX = 1;
	public final static int CLASSIFY_TZTG = 2;
	public final static int CLASSIFY_DCWJ = 3;
	public final static int CLASSIFY_SQGW = 4;
	public final static int CLASSIFY_ZBSH = 5;
	public final static int CLASSIFY_JJYL = 6;
	public final static int CLASSIFY_YLJK = 7;
	public final static int CLASSIFY_BMXX = 8;
	
	private Context context;
	private TextView classifyName;
	private ImageView classifyIcon;
	private int id;
	private String name;
	
	public ClassifyItemView(Context context) {
		super(context);
		this.context = context;
	}
	
	public ClassifyItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		
		classifyName = (TextView)findViewById(R.id.tv_classify_name);
		classifyIcon = (ImageView)findViewById(R.id.iv_classify_icon);
		
		if (classifyIcon != null) {
			classifyIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent();
					
					switch (id) {
					case CLASSIFY_ZWXX:
						intent.setClass(context, ZWXXActivity.class);
						context.startActivity(intent);
						break;

					case CLASSIFY_TZTG:
						break;

					case CLASSIFY_DCWJ:
						break;

					case CLASSIFY_SQGW:
						break;

					case CLASSIFY_ZBSH:
						break;

					case CLASSIFY_JJYL:
						break;

					case CLASSIFY_YLJK:
						break;

					case CLASSIFY_BMXX:
						intent.setClass(context, BMXXActivity.class);
						context.startActivity(intent);
						break;

					default:
						break;
					}
				}
			});
		}
	}
	
	public void setClassify(int id, String name) {
		this.id = id;
		this.name = name;
		
		classifyName.setText(name);
		switch (id) {
		case CLASSIFY_ZWXX:
			classifyIcon.setBackgroundResource(R.drawable.classify_zwxx);
			break;

		case CLASSIFY_TZTG:
			classifyIcon.setBackgroundResource(R.drawable.classify_tztg);
			break;

		case CLASSIFY_DCWJ:
			classifyIcon.setBackgroundResource(R.drawable.classify_dcwj);
			break;

		case CLASSIFY_SQGW:
			classifyIcon.setBackgroundResource(R.drawable.classify_sqgw);
			break;

		case CLASSIFY_ZBSH:
			classifyIcon.setBackgroundResource(R.drawable.classify_zbsh);
			break;

		case CLASSIFY_JJYL:
			classifyIcon.setBackgroundResource(R.drawable.classify_jjyl);
			break;

		case CLASSIFY_YLJK:
			classifyIcon.setBackgroundResource(R.drawable.classify_yljk);
			break;

		case CLASSIFY_BMXX:
			classifyIcon.setBackgroundResource(R.drawable.classify_bmxx);
			break;

		default:
			break;
		}
	}
}
	
