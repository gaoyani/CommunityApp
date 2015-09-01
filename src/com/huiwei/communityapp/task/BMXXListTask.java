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

import com.huiwei.communityapp.info.BMXXInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class BMXXListTask extends
		AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;

	public BMXXListTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	protected Integer doInBackground(String... params) {
		message = new Message();
		int flag = Constants.SUCCESS;
		
		try {
			HttpPost request = new HttpPost(UrlConstants.BMXX_LIST_URL);
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);
			
			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				List<BMXXInfo> bmxxInfos = new ArrayList<BMXXInfo>();
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONArray jsonArray = new JSONArray(retSrc);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonInfo = jsonArray.getJSONObject(i);
					BMXXInfo info = new BMXXInfo();
					info.name = jsonInfo.getString("name");
					info.phoneNumber = jsonInfo.getString("number");
					info.addTime = jsonInfo.getString("addtime");
					bmxxInfos.add(info);
				}
				
				message.obj = bmxxInfos;
				
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
