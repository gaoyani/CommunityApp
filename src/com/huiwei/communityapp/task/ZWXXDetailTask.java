    package com.huiwei.communityapp.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.huiwei.communityapp.info.ZWXXInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class ZWXXDetailTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String id;

	public ZWXXDetailTask(Context context, Handler handler, String id) {
		this.context = context;
		this.handler = handler;
		this.id = id;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();

		try {
			HttpPost request = new HttpPost(UrlConstants.ZWXX_DETAIL_URL);
			JSONObject param = new JSONObject();
			param.put("id", id);
			
			request.setEntity(new StringEntity(param.toString()));
//			HttpGet request = new HttpGet(UrlConstants.ZWXX_LIST_URL+"/id="+id);
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				message.obj = retSrc;
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
