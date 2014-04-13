package com.app.galaxy.ui.shopping;

import com.app.galaxy.http.BackgroundLoader;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.MainActivity;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.id;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.news.NewsUpdates;
import com.app.galaxy.ui.news.News.HelpPagerAdapter;
import com.app.galaxy.ui.utility.Utility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.app.galaxy.data.SubCategories;
import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.MainActivity;
import com.app.galaxy.ui.utility.Utility;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
public class Shopping extends Fragment {

	private TextView text;
	private ImageView goButton;
	public static boolean mIsGalaxy = false;
	public static boolean mIsBellavita = false;
	public static boolean mIsKaryaneka = false;
	boolean netstate,netstate1,netstate2,netstate_main;

	public Shopping() {
		// TODO Auto-generated constructor stub

	}
	@Override
	public void onDestroy() {
	System.out.println("Shopping : onDestroy @@@@@@@@@@@@@");
		super.onDestroy();
	}


	@Override
	public void onDestroyView() {
		System.out.println("Shopping : onDestroyView @@@@@@@@@@@@@");
		super.onDestroyView();
	}


	@Override
	public void onLowMemory() {
		System.out.println("Shopping : onLowMemory @@@@@@@@@@@@@");
		super.onLowMemory();
	}


	@Override
	public void onStop() {
		System.out.println("Shopping : onStop @@@@@@@@@@@@@");
		super.onStop();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		
		
		try {
			System.out.println("onViewCreated: =====1=====>"+Utility.getShoppingsubCategories().size());
			if(Utility.isOnline(getActivity()))
			{
				if(!(Utility.getShoppingsubCategories().size()>0))
				{
					System.out.println("onViewCreated: ======2====>");
					DownloadTask downloadTask=new DownloadTask(getActivity(),Utility.getCategories().get(3).catId,view);
					downloadTask.execute("");
				}
				else
				{
					System.out.println("onViewCreated: ====3======>");
					MultiMediaAdapter adapter = new MultiMediaAdapter();
					ViewPager myPager = (ViewPager) view.findViewById(R.id.shopviewpager);
					myPager.setAdapter(adapter);
					myPager.setCurrentItem(0);
					adapter.notifyDataSetChanged();
				}
			}
			else
			{
				Toast.makeText(getActivity(), "No Internet", 5000).show();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	
	private void load(View v) {
		// TODO Auto-generated method stub
		System.out.println("load: =====1=====>");
		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		try {
			if(Utility.isOnline(getActivity()))
			{
				System.out.println("load: =====2====size=>"+Utility.getShoppingsubCategories().size());
				if(!(Utility.getShoppingsubCategories().size()>0))
				{
					System.out.println("load: =====3=====>");
					DownloadTask downloadTask=new DownloadTask(getActivity(),Utility.getCategories().get(3).catId,v);
					downloadTask.execute("");
				}
				else
				{
					System.out.println("load: ======4====>");
					MultiMediaAdapter adapter = new MultiMediaAdapter();
					ViewPager myPager = (ViewPager) v.findViewById(R.id.shopviewpager);
					myPager.setAdapter(adapter);
					myPager.setCurrentItem(0);
					adapter.notifyDataSetChanged();
				}
			}
			else
			{
				//Toast.makeText(getActivity(), "No Internet", 5000).show();
				netstate_main=haveNetworkConnection();
				
				if(!netstate_main)
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

						builder.setTitle("Alert !!");
						builder.setMessage("No Internet Connection !!");

						builder.setPositiveButton("Settings",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(
												Settings.ACTION_WIRELESS_SETTINGS);
										startActivity(intent);

									}
								});

						builder.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										//locationManager.setTestProviderEnabled(locationManager.NETWORK_PROVIDER, true);
										//Toast.makeText(LoginScreen.this, "Network Enabled", Toast.LENGTH_SHORT).show();
										
										//getActivity().finish();
										//Intent in = new Intent(getActivity(),LoginScreen.class);
										//startActivity(in);
										getActivity().finish();
									}
								});

						builder.show();
					}
				

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	ProgressBar progressBar;
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			System.out.println("onCreateView: ==========>");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.shopping,null);
		load(v);
		return v;
	}



	public class DownloadTask extends AsyncTask<String, String, String>{

		Context context;
		String URL;
		String catId;
		View v;
		public DownloadTask(Context context,String catId,View v) {
			// TODO Auto-generated constructor stub

			this.context=context;
			this.catId=catId;
			this.v=v;
		}
		ProgressDialog pd;
		String subCatResult;
		ArrayList<SubCategories>subCategories;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//	progressBar=ProgressDialog.show(context, "","");

			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				System.out.println("doInBackground: =====3=====>");
				subCatResult=ParseUtilities.getXML(URLS.SUB_CATEGORIES_URL+catId,getActivity());
				subCategories=new ArrayList<SubCategories>();
				System.out.println("doInBackground: =====3==subCatResult===>"+subCatResult);
				if(!subCatResult.equals(""))
				{
					subCategories=ParseUtilities.getSubCategories(subCatResult,catId);
					Utility.setShoppingsubCategories(subCategories);
					System.out.println(Utility.getShoppingsubCategories().size()+"HEElloo");

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
				System.out.println("onPostExecute: =====3=subCategories====>"+subCategories);
				progressBar.setVisibility(View.INVISIBLE);

				if(subCategories.size()>0)
				{
					MultiMediaAdapter adapter = new MultiMediaAdapter();
					adapter.notifyDataSetChanged();
					ViewPager myPager = (ViewPager) v.findViewById(R.id.shopviewpager);

					//adapter.notifyDataSetChanged();

					myPager.setAdapter(adapter);
					myPager.setCurrentItem(0);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);


	}
	public class MultiMediaAdapter extends PagerAdapter {

		BackgroundLoader backgroundLoader;
		ProgressBar progressBar;
		ImageView ivbackground;
		public MultiMediaAdapter() {
			backgroundLoader=new BackgroundLoader(getActivity());
		} 
		View view ;
		public Object instantiateItem(View collection, int position) {
			try {
				LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				System.out.println("instantiateItem: ==position=======>"+position);
				int resId = 0;
				switch (position) {
				case 0: {
					resId = R.layout.shoppinghome;
					view = inflater.inflate(resId, null);
					progressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
					ivbackground = (ImageView)view.findViewById(R.id.ivShoppingHomeBackground);
					backgroundLoader.DisplayImage(Utility.getShopcatlist().get(0), ivbackground,progressBar);
					ImageView goButton = (ImageView)view.findViewById(R.id.videobutton);
					goButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							/*netstate=haveNetworkConnection();
	     					
	     					if(!netstate)
	     					{
	     						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	     						builder.setTitle("Alert !!");
	     						builder.setMessage("No Internet Connection !!");

	     						builder.setPositiveButton("Settings",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										Intent intent = new Intent(
	     												Settings.ACTION_WIRELESS_SETTINGS);
	     										startActivity(intent);

	     									}
	     								});

	     						builder.setNegativeButton("No",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										//locationManager.setTestProviderEnabled(locationManager.NETWORK_PROVIDER, true);
	     										//Toast.makeText(LoginScreen.this, "Network Enabled", Toast.LENGTH_SHORT).show();
	     										
	     										//getActivity().finish();
	     										//Intent in = new Intent(getActivity(),LoginScreen.class);
	     										//startActivity(in);
	     										getActivity().finish();
	     									}
	     								});

	     						builder.show();
	     					}*/
							
							URLS.SHOPPING_ID=Utility.getShoppingsubCategories().get(0).subcatId;
							mIsGalaxy = true;
							mIsBellavita = false;
							mIsKaryaneka = false;
							Fragment duedateFrag = new ShoppingUpdates();
							FragmentTransaction ft  = getFragmentManager().beginTransaction();
							ft.replace(R.id.shopping, duedateFrag);
							ft.addToBackStack(null);

							ft.commit();
						}
					});

					break;
				}
				case 1: {

					resId = R.layout.bellavita;
					view = inflater.inflate(resId, null);
					progressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
					
					ivbackground = (ImageView)view.findViewById(R.id.ivBellaVittaBackground);
					backgroundLoader.DisplayImage(Utility.getShopcatlist().get(1), ivbackground,progressBar);
					ImageView goButton = (ImageView)view.findViewById(R.id.belvitabutton);
					goButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							netstate1=haveNetworkConnection();
	     					
	     					if(!netstate1)
	     					{
	     						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	     						builder.setTitle("Alert !!");
	     						builder.setMessage("No Internet Connection !!");

	     						builder.setPositiveButton("Settings",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										Intent intent = new Intent(
	     												Settings.ACTION_WIRELESS_SETTINGS);
	     										startActivity(intent);

	     									}
	     								});

	     						builder.setNegativeButton("No",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										//locationManager.setTestProviderEnabled(locationManager.NETWORK_PROVIDER, true);
	     										//Toast.makeText(LoginScreen.this, "Network Enabled", Toast.LENGTH_SHORT).show();
	     										
	     										//getActivity().finish();
	     										//Intent in = new Intent(getActivity(),LoginScreen.class);
	     										//startActivity(in);
	     										getActivity().finish();
	     									}
	     								});

	     						builder.show();
	     					}
							
							
							
							URLS.SHOPPING_ID=Utility.getShoppingsubCategories().get(0).subcatId;
							mIsGalaxy = false;
							mIsBellavita = true;
							mIsKaryaneka = false;
							Fragment duedateFrag = new ShoppingUpdates();
							FragmentTransaction ft  = getFragmentManager().beginTransaction();
							ft.replace(R.id.shopping, duedateFrag);
							ft.addToBackStack(null);

							ft.commit();
						}
					});

					break;
				}
				case 2: {
					resId = R.layout.karyaneka;
					view = inflater.inflate(resId, null);
					progressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
					ivbackground = (ImageView)view.findViewById(R.id.ivKaryanekaBackground);
					
					backgroundLoader.DisplayImage(Utility.getShopcatlist().get(2), ivbackground,progressBar);
					
					ImageView goButton = (ImageView)view.findViewById(R.id.karyanabutton);
					goButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							
							netstate2=haveNetworkConnection();
	     					
	     					if(!netstate2)
	     					{
	     						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	     						builder.setTitle("Alert !!");
	     						builder.setMessage("No Internet Connection !!");

	     						builder.setPositiveButton("Settings",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										Intent intent = new Intent(
	     												Settings.ACTION_WIRELESS_SETTINGS);
	     										startActivity(intent);

	     									}
	     								});

	     						builder.setNegativeButton("No",
	     								new DialogInterface.OnClickListener() {

	     									@Override
	     									public void onClick(DialogInterface dialog, int which) {
	     										//locationManager.setTestProviderEnabled(locationManager.NETWORK_PROVIDER, true);
	     										//Toast.makeText(LoginScreen.this, "Network Enabled", Toast.LENGTH_SHORT).show();
	     										
	     										//getActivity().finish();
	     										//Intent in = new Intent(getActivity(),LoginScreen.class);
	     										//startActivity(in);
	     										getActivity().finish();
	     									}
	     								});

	     						builder.show();
	     					}
							
							
							URLS.SHOPPING_ID=Utility.getShoppingsubCategories().get(0).subcatId;
							mIsGalaxy = false;
							mIsBellavita = false;
							mIsKaryaneka = true;
							Fragment duedateFrag = new ShoppingUpdates();
							FragmentTransaction ft  = getFragmentManager().beginTransaction();
							ft.replace(R.id.shopping, duedateFrag);
							ft.addToBackStack(null);

							ft.commit();
						}
					});

					break;
				}

				}

				((ViewPager) collection).addView(view, 0);
				progressBar.setVisibility(View.INVISIBLE);

			} catch (Exception e) {
				// TODO: handle exception
			}

			return view;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public int getCount() {
			return Utility.getShopcatlist().size();
		}
	}
	public boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}

}
