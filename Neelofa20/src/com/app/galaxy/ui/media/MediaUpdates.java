package com.app.galaxy.ui.media;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.ItemsAdapter;
import com.app.galaxy.ui.utility.Utility;
import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class MediaUpdates extends Multimedia{


	String id="";
	private ListView listViewLeft;
	private ListView listViewRight;
	private ItemsAdapter leftAdapter;
	private ItemsAdapter rightAdapter;

	int[] leftViewsHeights;
	int[] rightViewsHeights;
	String mediaID;

	ProgressBar progressBar;
	@SuppressLint("ValidFragment")
	public MediaUpdates(String mediaID) {
		// TODO Auto-generated constructor stub
		this.mediaID=mediaID;
	}
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.galleryupdates,null);

		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			backgroungWidth = 200;
			backgroundHeight = 200;
		
		ImageView ivGalleryupdatesBackground=(ImageView)v.findViewById(R.id.ivgalleryupdatesBackground);
		
		ivGalleryupdatesBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		ImageView imageView=(ImageView)v.findViewById(R.id.imageView1);
		if(mediaID.equals("Images"))
			
		imageView.setImageResource(R.drawable.photostop);
		
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		try {
			NewsDownloadTask newsDownloadTask=new NewsDownloadTask(v);
			newsDownloadTask.execute("");

		} catch (Exception e) {
			// TODO: handle exception
		}

		return v;
	}
	
	
	ArrayList<MediaData>mediaUpdates;
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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				mediaUpdates=new ArrayList<MediaData>();
				String newsResponse=ParseUtilities.getXML(URLS.ITEMS_URL+URLS.MEDIA_ID,getActivity());
				System.out.println(newsResponse);
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
				System.out.println(":::::"+mediaUpdates.size());

				if(mediaUpdates.size()>0)
				{
					description=new String[mediaUpdates.size()];
					addonData=new AddonData[mediaUpdates.size()];
					for (int i = 0; i < mediaUpdates.size(); i++) {
						MediaData mediaData=(MediaData)mediaUpdates.get(i);
						description[i]=mediaData.title;

						addonData[i]=mediaData.addonData;
					}
					loadImages();
				}
				progressBar.setVisibility(View.INVISIBLE);

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

				Gallery adapter = new Gallery(getActivity(), R.id.imageView1, Utility.mediaImageUrls,description,Utility.MEDIA_UPDATEMODE,addonData);

				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(StaggeredGridView parent, View view, int position,
							long id) {
						// TODO Auto-generated method stub
						if(mediaID.equals("Videos")||mediaID.equals("Audio"))
						{

							System.out.println(":::::::::::"+mediaID);
							Fragment duedateFrag = new VideoUpdates(mediaUpdates.get(position).id);
							FragmentTransaction ft  = getFragmentManager().beginTransaction();
							ft.replace(R.id.media, duedateFrag);
							ft.addToBackStack(null);

							ft.commit();
							/*
							DownloadDetails video=new DownloadDetails(getActivity(), mediaUpdates.get(position).id,mediaID);
							video.execute("");
							 */					}
						else
						{
							Fragment duedateFrag = new ImageDetails();

							Bundle args = new Bundle();
							args.putString("id",mediaUpdates.get(position).id);
							args.putInt("views", addonData[position].no_of_views);
							args.putInt("comments", addonData[position].no_of_comments);

							FragmentTransaction ft  = getFragmentManager().beginTransaction();
							duedateFrag.setArguments(args);

							ft.replace(R.id.media, duedateFrag);
							ft.addToBackStack(null);

							ft.commit();
						}
					}
				});
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}



	}




}

