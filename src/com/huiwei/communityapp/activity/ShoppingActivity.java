/*****************************************************
 * Copyright(c)2014-2015 北京汇为永兴科技有限公司
 * OrderFragment.java
 * 创建人：高亚妮
 * 日     期：2014-6-20
 * 描     述：订单页面显示及操作文件
 * 版     本：v6.0
 *****************************************************/
package com.huiwei.communityapp.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.adapter.GoodsItemAdapter;
import com.huiwei.communityapp.info.GoodsInfo;
import com.huiwei.communityapp.task.CommunityGoodsTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.view.LoadingView;

public class ShoppingActivity extends Activity implements OnClickListener {
	
	public static final int UNCONFIRM_LIST = 0;
	public static final int CONFIRM_LIST = 1;
	
	private ListView goodsListView;
	private GoodsItemAdapter goodsItemAdapter;
	
	private TextView goodsNum;
	private TextView goodsPrice;
	
	private LoadingView loadingView;
	
	private int totalNum = 0;
	private List<GoodsInfo> goodsList;
	private int curPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		
		goodsNum = (TextView)findViewById(R.id.tv_goods_num);
		goodsPrice = (TextView)findViewById(R.id.tv_goods_price);
		loadingView = (LoadingView)findViewById(R.id.loading_view);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);
		((Button)findViewById(R.id.button_order)).setOnClickListener(this);
		
		goodsList = new ArrayList<GoodsInfo>();
		goodsListView = (ListView)findViewById(R.id.lv_goods);
		goodsItemAdapter = new GoodsItemAdapter(this, handler, goodsList);
		goodsListView.setAdapter(goodsItemAdapter);
		
		getGoodsList();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int num = msg.arg1;
			totalNum += num;
			
			String goodsId = (String) msg.obj;
			GoodsInfo info = Data.findSelGoods(goodsId);
			if (num == 1) {
				if (info == null) {
					info = new GoodsInfo();
					GoodsInfo goodsInfo = findGoods(goodsId);
					info.copy(goodsInfo);
					info.orderNum = num;
					Data.selGoodsList.add(info);
				} else {
					info.orderNum = info.orderNum+1;
				}
			} else if (num == -1){
				if (info != null && info.orderNum == 1) {
					Data.selGoodsList.remove(info);
				} else {
					info.orderNum = info.orderNum-1;
				}
			}	
			
			updatePrice();
		};
	};
	
	private GoodsInfo findGoods(String id) {
		for (GoodsInfo info : goodsList) {
			if (info.id.equals(id)) {
				return info;
			}
		}
		
		return null;
	}
	
	private void updatePrice() {
		goodsNum.setText(String.valueOf(totalNum));
		
		float total = 0;
		for (GoodsInfo info : Data.selGoodsList) {
			float price = Float.parseFloat(info.price)*info.orderNum;
			total += price;
		}

		float result = new BigDecimal(total).setScale(2, 
				BigDecimal.ROUND_HALF_UP).floatValue(); 
		
		goodsPrice.setText(getResources().getString(R.string.rmb)+" "+
				String.valueOf(result)+" "+getResources().getString(R.string.yuan));
	}
	
	private void getGoodsList() {
		for (GoodsInfo info : goodsList) {
			info = null;
		}
		goodsList.clear();
		
		CommunityGoodsTask task = new CommunityGoodsTask(ShoppingActivity.this, goodsHandler, curPage++);
		task.execute();
	}
	
	Handler goodsHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (msg.what == Constants.SUCCESS) {
				curPage = msg.arg1;
				goodsList = (List<GoodsInfo>) msg.obj;
				goodsItemAdapter.setGoodsList(goodsList);
			} else if (msg.what == Constants.LOAD_COMPLETE){
				
			} else if (msg.what == Constants.DATA_ERROR){
				Toast.makeText(ShoppingActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(ShoppingActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
			}
			
			loadingView.setVisibility(View.GONE);
		};
	};
	
	@Override
	public void onResume() {
		if (Data.selGoodsList.size() == 0) {
			goodsItemAdapter.setGoodsList(goodsList);
			totalNum = 0;
			updatePrice();
		}
		
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}	
	
	//按钮的点击消息监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;
			
		case R.id.button_order:{
			if (Data.memberInfo.isLogin) {
				if (Data.selGoodsList.size() == 0) {
					Toast.makeText(ShoppingActivity.this, R.string.please_choose_goods, Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setClass(ShoppingActivity.this, SubmitOrderActivity.class);
					startActivity(intent);
				}
			} else {
				Intent intent = new Intent();
				intent.setClass(ShoppingActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		}
			break;
		
		default:
			break;
		}
	}
}
