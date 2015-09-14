package com.huiwei.communityapp.task;

import java.util.ArrayList;
import java.util.List;

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
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;
import com.huiwei.communityapp.utils.UrlConstants;

public class CommunityGoodsTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int page;
	int flag = 0;

	public CommunityGoodsTask(Context context, Handler handler, int page) {
		this.context = context;
		this.handler = handler;
		this.page = page;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();
		String url = UrlConstants.COMMUNITY_GOODS_URL;
		try {
			HttpPost request = new HttpPost(url);
			JSONObject param = new JSONObject();
			param.put("id", Data.memberInfo.userID);
			param.put("p", page);
			
			request.setEntity(new StringEntity(param.toString()));
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(retSrc);
				int errorCode = jsonObject.getInt("status");
				if (errorCode == 1) {
					List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
					JSONArray jsonArray = jsonObject.getJSONArray("splist");
					if (jsonArray.length() == 0) {
						flag = Constants.LOAD_COMPLETE;
					} else {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonGoods = jsonArray.getJSONObject(i);
							GoodsInfo info = new GoodsInfo();
							info.id = jsonGoods.getString("sp_id");
							info.iconUrl = jsonGoods.getString("photo");
							info.name = jsonGoods.getString("pname");
							info.price = jsonGoods.getString("price");
							goodsList.add(info);
						}
						
						message.obj = goodsList;
					}
					
					message.arg1 = jsonObject.getInt("page");
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
