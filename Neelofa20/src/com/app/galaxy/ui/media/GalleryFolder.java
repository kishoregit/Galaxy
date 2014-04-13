package com.app.galaxy.ui.media;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.FolderDetails;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.news.NewsDetails;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.ItemsAdapter;
import com.app.galaxy.ui.utility.Utility;
import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;


@SuppressLint("ValidFragment")
public class GalleryFolder extends Multimedia{


	String id="";
	private ListView listViewLeft;
	private ListView listViewRight;
	private ItemsAdapter leftAdapter;
	private ItemsAdapter rightAdapter;

	int[] leftViewsHeights;
	int[] rightViewsHeights;
	String mediaID;
	@SuppressLint("ValidFragment")
	public GalleryFolder(String mediaID) {
		// TODO Auto-generated constructor stub
		this.mediaID=mediaID;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	ProgressBar progressBar;
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.galleryupdates,
				null);
		

		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			backgroungWidth = displayMetrics.widthPixels;
			backgroundHeight = displayMetrics.heightPixels;
		
		ImageView ivGalleryupdatesBackground=(ImageView)v.findViewById(R.id.ivgalleryupdatesBackground);
		
		ivGalleryupdatesBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		
		
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
	ArrayList<FolderDetails>mediaUpdates;
	class NewsDownloadTask extends AsyncTask<String,String, String>
	{
		View v;
		FolderDetails mediaData;
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
			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				mediaUpdates=new ArrayList<FolderDetails>();
				String newsResponse=ParseUtilities.getXML(URLS.MEDIA_FOLDER_URL+mediaID,getActivity());
				
				if(!newsResponse.equals(""))
				{
					mediaUpdates=ParseUtilities.getFoldeImages(newsResponse);
				}
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
				System.out.println(mediaUpdates.size());

				if(mediaUpdates.size()>0)
				{
					description=new String[mediaUpdates.size()];
					addonData=new AddonData[mediaUpdates.size()];
					for (int i = 0; i < mediaUpdates.size(); i++) {
						{
							mediaData=(FolderDetails)mediaUpdates.get(i);
							Utility.mediaFolderImageUrls=mediaData.src;
						}
					}
					
					loadImages(mediaUpdates);
				}
				progressBar.setVisibility(View.INVISIBLE);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		private void loadImages(final ArrayList<FolderDetails> mediaUpdates) {
			// TODO Auto-generated method stub
			try {
				StaggeredGridView gridView = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);

				int margin = getResources().getDimensionPixelSize(R.dimen.margin);

				gridView.setItemMargin(margin); // set the GridView margin

				gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 

				Gallery adapter = new Gallery(getActivity(), R.id.imageView1,mediaData.src,description,Utility.MEDIA_UPDATEMODE,null);

				gridView.setAdapter(adapter);
				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(StaggeredGridView parent, View view, int position,
							long id) {
						// TODO Auto-generated method stub
						/*Fragment duedateFrag = new NewsDetails();

						Bundle args = new Bundle();
						System.out.println(mediaUpdates.size());

						args.putString("id", mediaUpdates.get(position).folderImgId);
						FragmentTransaction ft  = getFragmentManager().beginTransaction();
						ft.replace(R.id.galleryupdates, duedateFrag);
						ft.addToBackStack(null);
						duedateFrag.setArguments(args);
						ft.commit();*/
					}
				});
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		}



	}
	
	
	

}

