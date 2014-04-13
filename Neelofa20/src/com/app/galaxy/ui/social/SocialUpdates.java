package com.app.galaxy.ui.social;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.media.MediaUpdatesAdapter;
import com.app.galaxy.ui.news.NewsDetails;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.ItemsAdapter;
import com.app.galaxy.ui.utility.Utility;

public class SocialUpdates extends Social{


	String socialid="";
	private ListView listViewLeft;
	private ListView listViewRight;
	private ItemsAdapter leftAdapter;
	private ItemsAdapter rightAdapter;

	int[] leftViewsHeights;
	int[] rightViewsHeights;
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.socialupdates,null);
		
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
		
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		
		ImageView imageView=(ImageView)v.findViewById(R.id.imageView1);
		imageView.setImageResource(R.drawable.blogtop);
		
		try {

			socialid=getArguments().getString("socialid");
			NewsDownloadTask newsDownloadTask=new NewsDownloadTask(v);
			newsDownloadTask.execute("");

		} catch (Exception e) {
			// TODO: handle exception
		}

		return v;
	}

	class NewsDownloadTask extends AsyncTask<String,String, String>
	{
		View v;
		String[] description;
		AddonData[] addonData;

		public NewsDownloadTask(View v) {
			// TODO Auto-generated constructor stub
			this.v=v;

		}
		ProgressDialog progressDialog;
		ArrayList<MediaData>mediaUpdates=new ArrayList<MediaData>();
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//progressDialog=ProgressDialog.show(getActivity(), "Loading...", "");
			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				System.out.println(":::::"+URLS.ITEMS_URL+URLS.MEDIA_ID);

				String newsResponse=ParseUtilities.getXML(URLS.ITEMS_URL+socialid,getActivity());
				if(!newsResponse.equals(""))
					mediaUpdates=ParseUtilities.getMediaUpdates(newsResponse);
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
				if(mediaUpdates.size()>0)
				{
					Log.i("TAG",mediaUpdates.size()+"");
					description=new String[mediaUpdates.size()];
					addonData=new AddonData[mediaUpdates.size()];
					for (int i = 0; i < mediaUpdates.size(); i++) {
						MediaData mediaData=(MediaData)mediaUpdates.get(i);
						description[i]=mediaData.title;

						addonData[i]=mediaData.addonData;
					}
					loadImages(v);
				}
				progressBar.setVisibility(View.INVISIBLE);		
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		private void loadImages(View v) {
			// TODO Auto-generated method stub
			try {
				MediaUpdatesAdapter adapter = new MediaUpdatesAdapter(getActivity(), R.id.imageView1, Utility.mediaImageUrls,description,Utility.NEWS_UPDATEMODE,addonData);
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

						args.putString("id", mediaUpdates.get(arg2).id);
						FragmentTransaction ft  = getFragmentManager().beginTransaction();
						ft.replace(R.id.social, duedateFrag);
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

}

