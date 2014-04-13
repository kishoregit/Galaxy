package com.app.galaxy.ui.social;

import java.util.ArrayList;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.FanData;
import com.app.galaxy.data.FolderDetails;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.media.Gallery;
import com.app.galaxy.ui.social.Social.DownloadTask;
import com.app.galaxy.ui.social.Social.MultiMediaAdapter;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.Utility;
import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Fans extends Social
{
	public Fans() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	ProgressBar progressBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fanlist,
				null);
		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			backgroungWidth = displayMetrics.widthPixels;
			backgroundHeight = displayMetrics.heightPixels;
		
		ImageView ivFansListBackground=(ImageView)v.findViewById(R.id.ivFansListBackground);
		
		ivFansListBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		
		ImageView imageView=(ImageView)v.findViewById(R.id.imageView1);
		imageView.setImageResource(R.drawable.fanstop);
		try {
			NewsDownloadTask newsDownloadTask=new NewsDownloadTask(v);
			newsDownloadTask.execute("");

		} catch (Exception e) {
			// TODO: handle exception
		}
	
		return v;
	}
	ArrayList<FanData>fansUpdates;
	class NewsDownloadTask extends AsyncTask<String,String, String>
	{
		View v;
		FolderDetails mediaData;
		String[] description;
		String[] usernamw;

		String[] imageUrls;
		public NewsDownloadTask(View v) {
			// TODO Auto-generated constructor stub
			this.v=v;

		}
	
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
				fansUpdates=new ArrayList<FanData>();
				String newsResponse=ParseUtilities.getXML(URLS.SOCIAL_FANS_URL,getActivity());
				
				if(!newsResponse.equals(""))
					fansUpdates=ParseUtilities.getFanImages(newsResponse);
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
				if(fansUpdates.size()>0)
				{
					description=new String[fansUpdates.size()];
					usernamw=new String[fansUpdates.size()];
					imageUrls=new String[fansUpdates.size()];
					for (int i = 0; i < fansUpdates.size(); i++) {
						
						description[i]=fansUpdates.get(i).stars;
						usernamw[i]=fansUpdates.get(i).username;

						imageUrls[i]=fansUpdates.get(i).profile_picture;
					}
					loadImages();
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		private void loadImages() {
			// TODO Auto-generated method stub
			try {
				StaggeredGridView gridView = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);

				int margin = getResources().getDimensionPixelSize(R.dimen.margin);

				gridView.setItemMargin(margin); // set the GridView margin

				gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 

				FanItem adapter = new FanItem(getActivity(), R.id.imageView1,imageUrls,description,usernamw);

				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(StaggeredGridView parent, View view, int position,
							long id) {
						// TODO Auto-generated method stub
						
					}
				});
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}



	}
	
	
	

}
