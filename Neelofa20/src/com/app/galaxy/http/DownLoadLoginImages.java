package com.app.galaxy.http;

import java.util.ArrayList;

import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.MainActivity;
import com.app.galaxy.ui.SplashActivity;
import com.app.galaxy.ui.utility.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DownLoadLoginImages extends AsyncTask<String, String, String>{

	ArrayList<String> urls;
	Context context;
	public DownLoadLoginImages(Context context) {
		
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String response=ParseUtilities.getXML(URLS.LOGIN_URLS_SLIDESHOW, context);
		Utility.setLoginImageUrls(ParseUtilities.getLoginImagesUrl(response));
		urls=Utility.getLoginImageUrls();
	//	ArrayList<Bitmap>bitmaps=Utility.getImages("sdcard/galaxy/", "");
		
		//if(Utility.getLoginUrlsFromShared(urls, bitmaps.size(), context))
		for (int i = 0; i < urls.size(); i++) {
			Bitmap bitmap=Utility.downloadBitmap(URLS.LOGIN_IMAGES+Utility.getLoginImageUrls().get(i));
			Utility.savetoSdcard(urls.get(i), context, bitmap,"galaxy");
			//System.out.println(urls.get(i));
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Intent intent=new Intent(context,LoginScreen.class);

		context.startActivity(intent);
		((Activity) context).finish();

	}
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
