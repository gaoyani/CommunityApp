package com.huiwei.communityapp.activity;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.task.ZWXXDetailTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;
import com.huiwei.communityapp.view.LoadingView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {

	private WebView webView;
	private LoadingView loadingView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
		loadingView = (LoadingView)findViewById(R.id.loading);
//		String url = getIntent().getStringExtra("url");
		webView = (WebView) findViewById(R.id.webView);
		
		ZWXXDetailTask task = new ZWXXDetailTask(this, detailHandler, getIntent().getStringExtra("id"));
		task.execute();
	}
	
	private Handler detailHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				String html = (String)msg.obj;
				
				WebSettings webSettings = webView.getSettings(); 
				webSettings.setJavaScriptEnabled(true);
//				webView.loadData(html, "text/html", "utf-8");
				webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
				webView.setWebViewClient(new WebViewClient() {
					@Override
		            public void onPageFinished(WebView view, String url) 
		            {
						loadingView.setVisibility(View.GONE);
		                super.onPageFinished(view, url);
		            }
				});
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


