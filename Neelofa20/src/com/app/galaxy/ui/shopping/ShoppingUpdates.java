package com.app.galaxy.ui.shopping;

import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
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
import com.app.galaxy.data.NewsData;
import com.app.galaxy.data.ShoppingData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.media.MediaUpdatesAdapter;
import com.app.galaxy.ui.news.NewsDetails;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.ItemsAdapter;
import com.app.galaxy.ui.utility.Utility;

@SuppressLint("ValidFragment")
public class ShoppingUpdates extends Shopping{


	String id="";
	private ListView listViewLeft;
	private ListView listViewRight;
	private ItemsAdapter leftAdapter;
	private ItemsAdapter rightAdapter;
	String price[];
	int[] leftViewsHeights;
	int[] rightViewsHeights;
	String mediaID;
	
	ProgressBar progressBar;
	public ShoppingUpdates() {
		// TODO Auto-generated constructor stub
	
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.shoppingupdates,null);
		
		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//			backgroungWidth = displayMetrics.widthPixels;
//			backgroundHeight = displayMetrics.heightPixels;
			backgroungWidth = 200;
			backgroundHeight = 200;
			
			ImageView ivShoppingUpdatesBackground=(ImageView)v.findViewById(R.id.ivShoppingupdatesBackground);
			
			ivShoppingUpdatesBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
			 ImageView imageView=(ImageView)v.findViewById(R.id.imageView1);
			 
			if(Shopping.mIsGalaxy)
				imageView.setBackgroundResource(R.drawable.shoppinggalaxytop);
			else if(Shopping.mIsBellavita)
				imageView.setBackgroundResource(R.drawable.shoppingbellavitta);
			else if(Shopping.mIsKaryaneka)
				imageView.setBackgroundResource(R.drawable.shoppingkaranekatop);
			
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.VISIBLE);

		try {
			NewsDownloadTask newsDownloadTask=new NewsDownloadTask(v);
			newsDownloadTask.execute("");

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return v;
	}
	ArrayList<ShoppingData>mediaUpdates;
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
			progressBar.setVisibility(View.VISIBLE);
			//progressDialog=ProgressDialog.show(getActivity(), "Loading...", "");
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				mediaUpdates=new ArrayList<ShoppingData>();
				String newsResponse=ParseUtilities.getXML(URLS.ITEMS_URL+URLS.SHOPPING_ID,getActivity());
				System.out.println(URLS.ITEMS_URL+URLS.SHOPPING_ID+"");
				if(!newsResponse.equals(""))
					mediaUpdates=ParseUtilities.getShoppingUpdates(newsResponse);
				
				newsResponse=ParseUtilities.getXML(URLS.COUNTRY_API, getActivity());
				Utility.countryList=ParseUtilities.getCountry(newsResponse);
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

				System.out.println(":::::"+mediaUpdates.size());
				Utility.newsImageUrls=new String[mediaUpdates.size()];
				if(mediaUpdates.size()>0)
				{
					description=new String[mediaUpdates.size()];
					addonData=new AddonData[mediaUpdates.size()];
					price=new String[mediaUpdates.size()];
					for (int i = 0; i < mediaUpdates.size(); i++) {
						ShoppingData mediaData=(ShoppingData)mediaUpdates.get(i);
						description[i]=mediaData.title;
						price[i]=mediaData.item_price;
						 Utility.newsImageUrls[i]=URLS.DETAILSIMAGE+mediaData.item_image;
						 Log.i("IMAGE ", Utility.newsImageUrls[i]);
					}
					loadImages(v);
				}
				progressDialog.cancel();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}

		private void loadImages(View v) {
		
			try {
				ShoppingAdapter adapter = new ShoppingAdapter(getActivity(), R.id.imageView1, Utility.newsImageUrls,description,Utility.NEWS_UPDATEMODE,null,price);
				ListView listView=(ListView)v.findViewById(R.id.newsList);

				listView.setAdapter(adapter);
				listView.setCacheColorHint(0);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						// TODO Auto-generated method stub
						Fragment duedateFrag = new OrderDetails();
						
						Bundle args = new Bundle();

						args.putString("price", mediaUpdates.get(arg2).item_price);

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
	
	
	

}

