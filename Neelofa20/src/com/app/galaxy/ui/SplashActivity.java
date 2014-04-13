package com.app.galaxy.ui;


// first page


import java.util.ArrayList;

import com.app.galaxy.http.DownLoadLoginImages;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.media.DownloadDetails;
import com.app.galaxy.ui.utility.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity
{
	SharedPreferences myPrefs;
	SharedPreferences.Editor prefsEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash);
		myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		prefsEditor = myPrefs.edit();
		prefsEditor.putBoolean("closed", false);
		prefsEditor.commit();

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//TP_Thread thread=new TP_Thread();
		//	thread.start();
		ArrayList<String> filenames = Utility.getImageFilenames("sdcard/galaxy/", "");
		if(!(filenames.size()>0))
		{
			DownLoadLoginImages dowonlaod=new DownLoadLoginImages(SplashActivity.this);
			dowonlaod.execute("");
		}
		else
		{
			TP_Thread thread=new TP_Thread();
			thread.start();
		}
	}
	
	

	Handler mHandler = new Handler (){
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			Intent intent=new Intent(SplashActivity.this,LoginScreen.class);

			startActivity(intent);
			finish();

		}
	};
	private class TP_Thread extends Thread
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				Thread.sleep(2000);

			} catch (Exception e) {
				// TODO: handle exception
			}
			mHandler.sendEmptyMessage(0);
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
}
