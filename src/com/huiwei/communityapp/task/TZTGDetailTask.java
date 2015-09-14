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

import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class TZTGDetailTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String nid;

	public TZTGDetailTask(Context context, Handler handler, String nid) {
		this.context = context;
		this.handler = handler;
		this.nid = nid;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();

		try {
			HttpPost request = new HttpPost(UrlConstants.TZTG_LIST_URL);
			TaskHttpClient taskClient = new TaskHttpClient();
			JSONObject param = new JSONObject();
			param.put("n_id", nid);
			
			request.setEntity(new StringEntity(param.toString()));
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				TZTGInfo tztgInfo = new TZTGInfo();
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonInfo = new JSONObject(retSrc);
				tztgInfo.id = jsonInfo.getString("n_id");
				tztgInfo.title = jsonInfo.getString("title");
				tztgInfo.time = jsonInfo.getString("create_time");
				tztgInfo.content = jsonInfo.getString("content");
				tztgInfo.fileName = jsonInfo.getString("attachment");
				tztgInfo.fileUrl = jsonInfo.getString("attachment_url");
				
				message.obj = tztgInfo;
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
