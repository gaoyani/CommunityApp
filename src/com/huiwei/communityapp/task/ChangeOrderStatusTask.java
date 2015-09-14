package com.huiwei.communityapp.task;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.Data;

public class ChangeOrderStatusTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String orderID;

	public ChangeOrderStatusTask(Context context, Handler handler, String orderID) {
		this.context = context;
		this.handler = handler;
		this.orderID = orderID;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();
		String url = params[0];
		try {
			HttpPost request = new HttpPost(url);
			JSONObject param = new JSONObject();
			param.put("id", Data.memberInfo.userID);
			param.put("oid", orderID);
			
			request.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(retSrc);
				int errorCode = jsonObject.getInt("status");
				if (errorCode != 1) {
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
