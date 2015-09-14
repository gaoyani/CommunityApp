package com.huiwei.communityapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiwei.communityapp.R;
import com.huiwei.communityapp.info.TZTGInfo;
import com.huiwei.communityapp.task.TZTGDetailTask;
import com.huiwei.communityapp.utils.Constants;
import com.huiwei.communityapp.utils.UrlConstants;
import com.huiwei.communityapp.view.LoadingView;

public class TZTGDetailActivity extends Activity implements OnClickListener {
	
	private LoadingView loadingView;
	private TZTGInfo tztgInfo;
	
	private AlertDialog progressDialog;
	private ProgressBar progressBar;
	private TextView progressPercent;
	
	private int FileLength;
    private int DownedFileLength = 0;
    private InputStream inputStream;
    private URLConnection connection;
    private OutputStream outputStream;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tztg_detail);
		
		loadingView = (LoadingView)findViewById(R.id.view_loading);
		((ImageView)findViewById(R.id.iv_return)).setOnClickListener(this);
		((Button)findViewById(R.id.button_download)).setOnClickListener(this);
		
		TZTGDetailTask task = new TZTGDetailTask(this, handler, getIntent().getStringExtra("id"));
		task.execute();
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			if (msg.what == Constants.SUCCESS) {
				tztgInfo = (TZTGInfo) msg.obj;
				((TextView)findViewById(R.id.tv_time)).setText(tztgInfo.time);
				((TextView)findViewById(R.id.tv_title)).setText(tztgInfo.title);
				((TextView)findViewById(R.id.tv_content)).setText(tztgInfo.content);
				if (tztgInfo.fileUrl != null && !tztgInfo.fileUrl.equals("")) {
					((RelativeLayout)findViewById(R.id.layout_file)).setVisibility(View.VISIBLE);
					((TextView)findViewById(R.id.tv_file_name)).setText(tztgInfo.fileName);
				}
			} else {
				Toast.makeText(TZTGDetailActivity.this, R.string.get_tztg_error, Toast.LENGTH_SHORT).show();
			}
			
			loadingView.setVisibility(View.GONE);
		};
	};
	
	@Override
	public void onResume() {
		
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_return:
			finish();
			break;
			
		case R.id.button_download: {
			if (progressDialog == null) {
				progressDialog = new AlertDialog.Builder(TZTGDetailActivity.this).create();
				progressDialog.setCancelable(false);
				View view = LayoutInflater.from(this).inflate(R.layout.download_progress_view, null);
				progressPercent = (TextView) view.findViewById(R.id.tv_progress);
				progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
				progressDialog.setView(view);
			} else {
				progressBar.setProgress(0);
				progressPercent.setText("0%");
			}
			
			progressDialog.show();
            
			DownedFileLength=0;
            // TODO Auto-generated method stub
           Thread thread=new Thread(){
             public void run(){
                 try {
                    DownFile(tztgInfo.fileUrl);
                } catch (Exception e) {
                    // TODO: handle exception
                }
             }
           };
           thread.start();
		}
			break;

		default:
			break;
		}
		
	}
	
	private Handler handlerDownload = new Handler()
    {
         public void handleMessage(Message msg)
        {
        if (!Thread.currentThread().isInterrupted()) {
            switch (msg.what) {
            case 0:
                progressBar.setMax(FileLength);
                break;
            case 1:
                progressBar.setProgress(DownedFileLength);
                int x=DownedFileLength*100/FileLength;
                progressPercent.setText(x+"%");
                break;
            case 2:
                Toast.makeText(getApplicationContext(), R.string.download_complate, Toast.LENGTH_LONG).show();
                progressDialog.cancel();
                break;
                 
            default:
                break;
            }
        }  
        }
          
    };
	
	private void DownFile(String urlString)
    {
         
        /*
         * 连接到服务器
         */
        String urlPath = urlString.substring(0, urlString.lastIndexOf("/")+1) ;
        String fileName = urlString.substring(urlString.lastIndexOf("/")+1) ;
		
        try {
             URL url=new URL(urlPath+java.net.URLEncoder.encode(fileName,"UTF-8"));
             connection = url.openConnection();
             if (connection.getReadTimeout()==5) {
                Log.i("---------->", "当前网络有问题");
                // return;
               }
             inputStream=connection.getInputStream();
             
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
        /*
         * 文件的保存路径和和文件名其中Nobody.mp3是在手机SD卡上要保存的路径，如果不存在则新建
         */
        String savePAth = Environment.getExternalStorageDirectory()+"/DownloadFile";
        File file1=new File(savePAth);
        if (!file1.exists()) {
            file1.mkdir();
        }
        String savePathString = Environment.getExternalStorageDirectory()+"/DownloadFile/"+tztgInfo.fileName;
        File file =new File(savePathString);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        }
        /*
         * 向SD卡中写入文件,用Handle传递线程
         */
        Message message=new Message();
        try {
            outputStream=new FileOutputStream(file);
            byte [] buffer=new byte[1024*4];
            FileLength=connection.getContentLength();
            message.what=0;
            handlerDownload.sendMessage(message);
            while (DownedFileLength<FileLength) {
                DownedFileLength+=inputStream.read(buffer);
                outputStream.write(buffer);
                Message message1=new Message();
                message1.what=1;
                handlerDownload.sendMessage(message1);
            }
            Message message2=new Message();
            message2.what=2;
            handlerDownload.sendMessage(message2);
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
