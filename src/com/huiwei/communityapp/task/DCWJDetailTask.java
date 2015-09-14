package com.huiwei.communityapp.task;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.huiwei.commonlib.CommonFunction;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;

public class DCWJDetailTask extends AsyncTask<String, Void, Integer> {
	Context context = null;
	Handler handler = null;
	Message message = null;
	int flag = 0;
	String id;

	public DCWJDetailTask(Context context, Handler handler, String id) {
		this.context = context;
		this.handler = handler;
		this.id = id;
	}

	@Override
	protected Integer doInBackground(String... params) {
		flag = Constants.SUCCESS;
		message = new Message();

		try {
			HttpPost request = new HttpPost(UrlConstants.DCWJ_DETAIL_URL);
			JSONObject param = new JSONObject();
			param.put("q_id", id);
			param.put("mac", CommonFunction.getLocalMacAddress(context));
			
			request.setEntity(new StringEntity(param.toString()));
			TaskHttpClient taskClient = new TaskHttpClient();
			HttpResponse httpResponse = taskClient.client.execute(request);

			int code = httpResponse.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
//				JSONObject jsonObject = new JSONObject(retSrc);
//				message.obj = jsonObject.getString("url");
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
