package com.huiwei.communityapp.task;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.huiwei.communityapp.info.GoodsInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.utils.UrlConstants;

public class AddOrderTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String username;
	String phoneNumber;
	String address;
	String remark;

	public AddOrderTask(Context context, Handler handler, String username,
			String phoneNumber, String address, String remark) {
		this.context = context;
		this.handler = handler;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.remark = remark;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();
		String url = UrlConstants.ADD_ORDER_URL;
		try {
			HttpPost request = new HttpPost(url);
			JSONObject param = new JSONObject();
			param.put("id", Data.memberInfo.userID);
			param.put("username", username);
			param.put("cellphone", phoneNumber);
			param.put("address", address);
			param.put("remark", remark);
			JSONArray goodsArray = new JSONArray();
			for (GoodsInfo info : Data.selGoodsList) {
				JSONObject jsonGoods = new JSONObject();
				jsonGoods.put("spid", info.id);
				jsonGoods.put("num", info.orderNum);
				jsonGoods.put("price", info.price);
				goodsArray.put(jsonGoods);
			}
			param.put("list", goodsArray);
			
			request.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(retSrc);
				int errorCode = jsonObject.getInt("status");
				if (errorCode == 1) {
					message.obj = jsonObject.getString("oid");
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
