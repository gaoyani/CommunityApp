package com.huiwei.communityapp.activity;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.task.DCWJDetailTask;
import com.huiwei.communityapp.task.ZWXXDetailTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;
import com.huiwei.communityapp.view.LoadingView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class WebActivity extends Activity {

	private WebView webView;
	private LoadingView loadingView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		loadingView = (LoadingView)findViewById(R.id.loading);
		webView = (WebView) findViewById(R.id.webView);
		WebSettings webSettings = webView.getSettings(); 
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
            public void onPageFinished(WebView view, String url) 
            {
				loadingView.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
		});
		
		int webType = getIntent().getIntExtra("web_type", Constants.WEB_ZWXX);
		if (webType == Constants.WEB_ZWXX) {
			ZWXXDetailTask task = new ZWXXDetailTask(this, zwxxDetailHandler, getIntent().getStringExtra("id"));
			task.execute();
		} else if (webType == Constants.WEB_DCWJ) {
			DCWJDetailTask task = new DCWJDetailTask(this, dcwjDetailHandler, getIntent().getStringExtra("id"));
			task.execute();
		} else {
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			webView.setLayoutParams(layoutParams);
			String url = getIntent().getStringExtra("url");
			webView.loadUrl(url);
		}
	}
	
	private Handler zwxxDetailHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				String html = (String)msg.obj;
				webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
			}
		};
	};
	
	private Handler dcwjDetailHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				String url = (String)msg.obj;
				webView.loadUrl(url);
			}
		};
	};
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
    }  
	
    @Override  
    protected void onResume() {  
        super.onResume();  
   }  
    
    @Override  
    protected void onPause() {  
        super.onPause();  
   }  
}


