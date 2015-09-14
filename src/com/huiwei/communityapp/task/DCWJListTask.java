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

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.info.DCWJInfo;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.info.TZTGTypeInfo;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class DCWJListTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;

	public DCWJListTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();

		try {
			HttpPost request = new HttpPost(UrlConstants.DCWJ_LIST_URL);
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				List<DCWJInfo> dcwjDoingInfos = new ArrayList<DCWJInfo>();
				List<DCWJInfo> dcwjhistoryInfos = new ArrayList<DCWJInfo>();
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObject = new JSONObject(retSrc);
				JSONArray jsonDoingArray = jsonObject.getJSONArray("ing");
				for (int i = 0; i < jsonDoingArray.length(); i++) {
					JSONObject jsonInfo = jsonDoingArray.getJSONObject(i);
					DCWJInfo info = new DCWJInfo();
					info.id = jsonInfo.getString("q_id");
					info.title = jsonInfo.getString("qname");
					info.startTime = jsonInfo.getString("start_time");
					info.endTime = jsonInfo.getString("end_time");
					dcwjDoingInfos.add(info);
				}
				
				JSONArray jsonHistoryArray = jsonObject.getJSONArray("ed");
				for (int i = 0; i < jsonHistoryArray.length(); i++) {
					JSONObject jsonInfo = jsonHistoryArray.getJSONObject(i);
					DCWJInfo info = new DCWJInfo();
					info.id = jsonInfo.getString("q_id");
					info.title = jsonInfo.getString("qname");
					info.startTime = jsonInfo.getString("start_time");
					info.endTime = jsonInfo.getString("end_time");
					dcwjhistoryInfos.add(info);
				}
				
				List<List<DCWJInfo>> listArray = new ArrayList<List<DCWJInfo>>();
				listArray.add(dcwjDoingInfos);
				listArray.add(dcwjhistoryInfos);
				
				message.obj = listArray;
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
