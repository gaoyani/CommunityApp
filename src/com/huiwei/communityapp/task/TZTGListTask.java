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

public class TZTGListTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String tid;
	int listPos;

	public TZTGListTask(Context context, Handler handler, String tid, int listPos) {
		this.context = context;
		this.handler = handler;
		this.tid = tid;
		this.listPos = listPos;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();
		message.arg1 = listPos;

		try {
			HttpPost request = new HttpPost(UrlConstants.TZTG_LIST_URL);
			TaskHttpClient taskClient = new TaskHttpClient();
			JSONObject param = new JSONObject();
			param.put("nc_id", tid);
			
			request.setEntity(new StringEntity(param.toString()));
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				List<TZTGInfo> tztgInfos = new ArrayList<TZTGInfo>();
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONArray jsonArray = new JSONArray(retSrc);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonInfo = jsonArray.getJSONObject(i);
					TZTGInfo info = new TZTGInfo();
					info.id = jsonInfo.getString("n_id");
					info.title = jsonInfo.getString("title");
					info.time = jsonInfo.getString("create_time");
					tztgInfos.add(info);
				}
				
				message.obj = tztgInfos;
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
