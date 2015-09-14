package com.huiwei.communityapp.task;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.huiwei.communityapp.info.GoodsInfo;
import com.huiwei.communityapp.info.OrderDetailInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.utils.UrlConstants;

public class OrderDetailTask extends
		AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	String orderID;

	public OrderDetailTask(Context context, Handler handler, String orderID) {
		this.context = context;
		this.handler = handler;
		this.orderID = orderID;
	}

	@Override
	protected Integer doInBackground(String... params) {
		message = new Message();
		int flag = Constants.SUCCESS;
		
		try {
			HttpPost request = new HttpPost(UrlConstants.ORDER_DETAIL_URL);
			JSONObject param = new JSONObject();
			param.put("oid", orderID);
			param.put("id", Data.memberInfo.userID);
			
			request.setEntity(new StringEntity(param.toString()));
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(retSrc);
				int errorCode = jsonObject.getInt("status");
				if (errorCode == 1) {
					OrderDetailInfo info = new OrderDetailInfo();
					info.id = orderID;
					info.sn = jsonObject.getString("ids");
					info.time = jsonObject.getString("subtime");
					info.totalPrice = jsonObject.getString("tprice");
					info.statusID = Integer.valueOf(jsonObject.getString("ostatus"));
					info.status = jsonObject.getString("ostatusdes");
					info.contactName = jsonObject.getString("callname");
					info.contactNumber = jsonObject.getString("callphone");
					info.contactAddress = jsonObject.getString("calladdr");
					info.remark = jsonObject.getString("remark");
					
					JSONArray jsonArray = jsonObject.getJSONArray("list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonGoods = jsonArray.getJSONObject(i);
						GoodsInfo goodsInfo = new GoodsInfo();
						goodsInfo.name = jsonGoods.getString("spname");
						goodsInfo.price = jsonGoods.getString("price");
						goodsInfo.orderNum = Integer.valueOf(jsonGoods.getString("num"));
						info.goodsList.add(goodsInfo);
					}
					
					message.obj = info;
				} else {
					message.obj = jsonObject.getString("msg");
					flag = Constants.DATA_ERROR;
				}
			} else {
				flag = Constants.NET_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = Constants.OTHER_ERROR;
		}
		
		return flag;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (handler != null) {
			message.what = result;
			handler.sendMessage(message);
		}
	}
}
