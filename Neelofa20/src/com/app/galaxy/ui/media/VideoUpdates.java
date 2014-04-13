package com.app.galaxy.ui.media;


import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.data.NewsData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.Utility;

@SuppressLint("ValidFragment")
public class VideoUpdates extends MediaUpdates{

	
	ProgressBar progressBar;
	public VideoUpdates(String mediaID) {
		super(mediaID);
		// TODO Auto-generated constructor stub
		this.mediaID=mediaID;
	}
	String id="";
	String[] description;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.newsupdates,
				null);

		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//			backgroungWidth = displayMetrics.widthPixels;
//			backgroundHeight = displayMetrics.heightPixels;
			backgroungWidth = 200;
			backgroundHeight = 200;
			
		ImageView ivGalleryupdatesBackground=(ImageView)v.findViewById(R.id.ivNewsupdatesBackground);
		
		ivGalleryupdatesBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		
		ImageView imageView=(ImageView)v.findViewById(R.id.topimg);
		if(mediaID.equalsIgnoreCase("Audio"))
			imageView.setImageResource(R.drawable.audiotop);
		else if(mediaID.equalsIgnoreCase("Videos"))
			imageView.setImageResource(R.drawable.videostop);
		
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		if(!(mediaUpdates!=null&&mediaUpdates.size()>0))
		{
			NewsDownloadTask newsDownloadTask=new NewsDownloadTask(v);
			newsDownloadTask.execute("");
		}
		else
		{
			loadImages(v);
		}
		return v;
	}
	ArrayList<MediaData>mediaUpdates;
	AddonData[] addonData;

	class NewsDownloadTask extends AsyncTask<String,String, String>
	{
		View v;
		public NewsDownloadTask(View v) {
			// TODO Auto-generated constructor stub
			this.v=v;

		}
		ProgressDialog progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		//	progressDialog=ProgressDialog.show(getActivity(), "Loading...", "");
			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			mediaUpdates=new ArrayList<MediaData>();


			String newsResponse=ParseUtilities.getXML(URLS.ITEMS_URL+URLS.MEDIA_ID,getActivity());
			if(!newsResponse.equals(""))
				mediaUpdates=ParseUtilities.getMediaUpdates(newsResponse);
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(mediaUpdates.size()>0)
			{
				description=new String[mediaUpdates.size()];
				addonData=new AddonData[mediaUpdates.size()];
				for (int i = 0; i < mediaUpdates.size(); i++) {
					MediaData mediaData=(MediaData)mediaUpdates.get(i);
					description[i]=mediaData.title;
					addonData[i]=mediaData.addonData;
				}
				
				System.out.println("Hello "+mediaUpdates.size());
				loadImages(v);
			}
			progressBar.setVisibility(View.INVISIBLE);
		}
		

	}
	private void loadImages(View v) {
	
		VideoScreen adapter = new VideoScreen(getActivity(), R.id.imageView1, Utility.mediaImageUrls,description,Utility.NEWS_UPDATEMODE,addonData);
		ListView listView=(ListView)v.findViewById(R.id.newsList);
		System.out.println(":::::::::::::::::::::::::;Utility.mediaImageUrls"+Utility.mediaImageUrls.length);
		listView.setAdapter(adapter);
		listView.setCacheColorHint(0);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				System.out.println("HELLLOOOOOOOOOOOOO"+mediaID);
				DownloadDetails video=new DownloadDetails(getActivity(), mediaUpdates.get(position).id,mediaID,getFragmentManager(),addonData[position]);
				video.execute("");

			}
		});
		adapter.notifyDataSetChanged();
	}
}

