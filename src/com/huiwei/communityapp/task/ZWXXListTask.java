package com.huiwei.communityapp.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.huiwei.communityapp.info.ZWXXInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class ZWXXListTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;

	public ZWXXListTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();

		try {
			HttpPost request = new HttpPost(UrlConstants.ZWXX_LIST_URL);
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				List<ZWXXInfo> zwxxInfos = new ArrayList<ZWXXInfo>();
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONArray jsonArray = new JSONArray(retSrc);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonInfo = jsonArray.getJSONObject(i);
					ZWXXInfo info = new ZWXXInfo();
					info.id = jsonInfo.getString("Id");
					info.title = jsonInfo.getString("title");
					info.time = jsonInfo.getString("createtime");
					info.author = jsonInfo.getString("creator");
					info.content = jsonInfo.getString("content");
					zwxxInfos.add(info);
				}
				
				message.obj = zwxxInfos;
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
