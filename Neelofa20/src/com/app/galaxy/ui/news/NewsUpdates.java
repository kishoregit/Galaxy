package com.app.galaxy.ui.news;

import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.data.NewsData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.media.MediaUpdatesAdapter;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.ItemsAdapter;
import com.app.galaxy.ui.utility.Utility;

public class NewsUpdates extends News{


	String id="";
	String[] description;

	ProgressBar progressBar;
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.newsupdates, null);
		int backgroungWidth, backgroundHeight;
	//	ivNewsupdatesBackground
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		backgroungWidth = displayMetrics.widthPixels;
//		backgroundHeight = displayMetrics.heightPixels;
		
		backgroungWidth = 200;
		backgroundHeight = 200;
		
		ImageView ivNewsupdatesBackground=(ImageView)v.findViewById(R.id.ivNewsupdatesBackground);
		
		ivNewsupdatesBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		
		ImageView imageView=(ImageView)v.findViewById(R.id.topimg);
		
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		
		if(URLS.NEWS_ID.equalsIgnoreCase("6"))
		{
			imageView.setImageResource(R.drawable.statementstop);
		}
		else
		{
			imageView.setImageResource(R.drawable.latestnewstop);
		}
		
		progressBar.setVisibility(View.INVISIBLE);
		if(!(newsUpdates!=null&&newsUpdates.size()>0))
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
	ArrayList<NewsData>newsUpdates;
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
			progressBar.setVisibility(View.VISIBLE);

			//	progressDialog=ProgressDialog.show(getActivity(), "Loading...", "");
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			try {
				newsUpdates=new ArrayList<NewsData>();

				System.out.println(":::::"+URLS.ITEMS_URL+URLS.NEWS_ID);

				String newsResponse=ParseUtilities.getXML(URLS.ITEMS_URL+URLS.NEWS_ID,getActivity());
				if(!newsResponse.equals(""))
					newsUpdates=ParseUtilities.getNewsUpdates(newsResponse);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {

				progressBar.setVisibility(View.INVISIBLE);
				System.out.println("IDDDD  size"+newsUpdates.size());

				if(newsUpdates.size()>0)
				{
					System.out.println("IDDDD"+URLS.NEWS_ID);

					description=new String[newsUpdates.size()];
					addonData=new AddonData[newsUpdates.size()];
					for (int i = 0; i < newsUpdates.size(); i++) {
						NewsData mediaData=(NewsData)newsUpdates.get(i);
						description[i]=mediaData.title;

						addonData[i]=mediaData.addonData;
					}
					System.out.println("IDDDD"+URLS.NEWS_ID);

					loadImages(v);
				}

				//	progressDialog.cancel();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}


	}
	private void loadImages(View v) {

		try {
			MediaUpdatesAdapter adapter = new MediaUpdatesAdapter(getActivity(), R.id.imageView1, Utility.newsImageUrls,description,Utility.NEWS_UPDATEMODE,addonData);
			ListView listView=(ListView)v.findViewById(R.id.newsList);

			listView.setAdapter(adapter);
			listView.setCacheColorHint(0);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Fragment duedateFrag = new NewsDetails();

					Bundle args = new Bundle();

					args.putString("id", newsUpdates.get(arg2).id);
					args.putInt("views", addonData[arg2].no_of_views);
					args.putInt("comments", addonData[arg2].no_of_comments);

					FragmentTransaction ft  = getFragmentManager().beginTransaction();
					ft.replace(R.id.newsupdates, duedateFrag);
					ft.addToBackStack(null);
					duedateFrag.setArguments(args);
					ft.commit();
				}
			});
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

