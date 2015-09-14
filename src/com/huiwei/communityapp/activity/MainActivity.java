package com.huiwei.communityapp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.adapter.NewsItemAdapter;
import com.huiwei.communityapp.info.NewsInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.view.ClassifyItemView;
import com.huiwei.communityapp.view.ListViewForScrollView;
import com.huiwei.communityapp.view.RecommendPictrueView;

public class MainActivity extends Activity {
	
	private ListViewForScrollView listViewContent;
	private NewsItemAdapter adapter;
	private RecommendPictrueView recommendLeftView, recommendRightView1, recommendRightView2;
	private LinearLayout layoutRecommend;
	private boolean hasViewMeasured = false;
	private long mExitTime;
	
	List<NewsInfo> newsList = new ArrayList<NewsInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		hasViewMeasured = false;
		listViewContent = (ListViewForScrollView)findViewById(R.id.lv_content);
		recommendLeftView = (RecommendPictrueView)findViewById(R.id.view_recommend_left);
		recommendRightView1 = (RecommendPictrueView)findViewById(R.id.view_recommend_right_1);
		recommendRightView2 = (RecommendPictrueView)findViewById(R.id.view_recommend_right_2);
		
		layoutRecommend = (LinearLayout)findViewById(R.id.layout_recommend);
		
		initButtons();
		initRecommendView();
		initListView();
		
		((Button)findViewById(R.id.btn_login)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if (Data.memberInfo.isLogin) {
					intent.setClass(MainActivity.this, UserCenterActivity.class);
				} else {
					intent.setClass(MainActivity.this, LoginActivity.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				}
				startActivity(intent);
			}
		});
	}
	
	private void initButtons() {
		((ClassifyItemView)findViewById(R.id.classify_view_1)).setClassify(
				ClassifyItemView.CLASSIFY_ZWXX, getResources().getString(R.string.classify_zwxx));
		((ClassifyItemView)findViewById(R.id.classify_view_2)).setClassify(
				ClassifyItemView.CLASSIFY_TZTG, getResources().getString(R.string.classify_tztg));
		((ClassifyItemView)findViewById(R.id.classify_view_3)).setClassify(
				ClassifyItemView.CLASSIFY_DCWJ, getResources().getString(R.string.classify_dcwj));
		((ClassifyItemView)findViewById(R.id.classify_view_4)).setClassify(
				ClassifyItemView.CLASSIFY_SQGW, getResources().getString(R.string.classify_sqgw));
		((ClassifyItemView)findViewById(R.id.classify_view_5)).setClassify(
				ClassifyItemView.CLASSIFY_ZBSH, getResources().getString(R.string.classify_zbsh));
		((ClassifyItemView)findViewById(R.id.classify_view_6)).setClassify(
				ClassifyItemView.CLASSIFY_JJYL, getResources().getString(R.string.classify_jjyl));
		((ClassifyItemView)findViewById(R.id.classify_view_7)).setClassify(
				ClassifyItemView.CLASSIFY_YLJK, getResources().getString(R.string.classify_yljk));
		((ClassifyItemView)findViewById(R.id.classify_view_8)).setClassify(
				ClassifyItemView.CLASSIFY_BMXX, getResources().getString(R.string.classify_bmxx));
	}
	
	private void initRecommendView() {
		ViewTreeObserver vto = layoutRecommend.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (hasViewMeasured == false) {
					int height = layoutRecommend.getWidth() * 65 / (52 * 2);
					int margin = ((LinearLayout.LayoutParams) layoutRecommend
							.getLayoutParams()).leftMargin;
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							layoutRecommend.getWidth(), height);
					lp.setMargins(margin, margin, margin, margin);
					layoutRecommend.setLayoutParams(lp);

					hasViewMeasured = true;
				}
				return true;
			}
		});
		
		recommendLeftView.setInfo(R.drawable.pic1, "");
		recommendRightView1.setInfo(R.drawable.pic2, "");
		recommendRightView2.setInfo(R.drawable.pic3, "");
	}

	private void initListView() {
		fileTestInfo();
		adapter = new NewsItemAdapter(this, newsList);
		listViewContent.setAdapter(adapter);
		listViewContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}
	
	private void fileTestInfo() {
		NewsInfo info1 = new NewsInfo();
		info1.title = "2015园冶杯住宅景观奖启动大会成功召开";
		info1.detail = "7月26日，第七期园冶沙龙暨2015园冶杯住宅景观奖启动大会在北京湖北大厦成功召开,获得了业界的一致好评。 [详情]";
		newsList.add(info1);
		
		NewsInfo info2 = new NewsInfo();
		info2.title = "新型城镇化与生态城镇高峰论坛成功举办";
		info2.detail = "“第五届园冶高峰论坛暨亚洲园林大会”的重量级分会场--新型城镇化与生态城镇高峰论坛于3月21日在北京新大都饭店隆... [详情]";
		newsList.add(info2);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		((Button)findViewById(R.id.btn_login)).setText(getResources().getString(
				Data.memberInfo.isLogin ? R.string.user_center : R.string.login));
	}
	
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - mExitTime > Constants.INTERVAL) {
			Toast.makeText(this, getResources().getString(R.string.exit_app), 
					Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}
}
