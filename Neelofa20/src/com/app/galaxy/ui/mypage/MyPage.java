package com.app.galaxy.ui.mypage;


import java.net.URL;

import com.app.galaxy.http.ImageLoader;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.StaticUtil;
import com.app.galaxy.http.URLS;
import com.app.galaxy.http.UploadActivity;
import com.app.galaxy.http.Utils;
import com.app.galaxy.ui.MainActivity;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.shopping.ShoppingUpdates;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyPage extends Fragment {
	
	boolean netstate_main;

	private TextView text;
	public MyPage() {
		// TODO Auto-generated constructor stub

	}
	ImageLoader imageLoader;
	
	@Override
	public void onDestroy() {
	System.out.println("MyPage : onDestroy @@@@@@@@@@@@@");
		super.onDestroy();
	}


	@Override
	public void onDestroyView() {
		System.out.println("MyPage : onDestroyView @@@@@@@@@@@@@");
		super.onDestroyView();
	}


	@Override
	public void onLowMemory() {
		System.out.println("MyPage : onLowMemory @@@@@@@@@@@@@");
		super.onLowMemory();
	}


	@Override
	public void onStop() {
		System.out.println("MyPage : onStop @@@@@@@@@@@@@");
		super.onStop();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	ImageView profileImg;
	ImageView mIvBannerImage;
	TextView fanLevel;
	TextView mAppName;
	TextView mAppVertion;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		profileImg=(ImageView)view.findViewById(R.id.imageView2);
		mIvBannerImage=(ImageView)view.findViewById(R.id.imageView1);//kishore

		imageLoader=new ImageLoader(getActivity());
		imageLoader.DisplayImage(URLS.USERS_IMAGE+Utility.getPROFILE_IMAGE_URL(), profileImg);
		imageLoader.DisplayImage(URLS.USERS_BANNER+Utility.getBG_PROFILE_IMAGE_URL(), mIvBannerImage);//kishore
		System.out.println(URLS.USERS_IMAGE+Utility.getPROFILE_IMAGE_URL());
		System.out.println(URLS.USERS_BANNER+Utility.getBG_PROFILE_IMAGE_URL());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.mypage,
				null);
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

		
		int backgroungWidth, backgroundHeight;
		//	ivNewsupdatesBackground
			DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			backgroungWidth = displayMetrics.widthPixels;
			backgroundHeight = displayMetrics.heightPixels;
			ImageView ivMypageBackground =(ImageView)v.findViewById(R.id.ivMypageBackground);
			ivMypageBackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.editback, backgroungWidth, backgroundHeight));
		
		profileImg=(ImageView)v.findViewById(R.id.imageView2);
		mIvBannerImage=(ImageView)v.findViewById(R.id.imageView1);

		imageLoader=new ImageLoader(getActivity());
		imageLoader.DisplayImage(URLS.USERS_BANNER+Utility.getBG_PROFILE_IMAGE_URL(), mIvBannerImage);//kishore
		imageLoader.DisplayImage(URLS.USERS_IMAGE+Utility.getPROFILE_IMAGE_URL(), profileImg);		
		TextView username=(TextView)v.findViewById(R.id.usertext);
		Utility.Credentials credentials=Utility.getCredentials(getActivity());

		username.setText(credentials.username);
		mAppName =(TextView)v.findViewById(R.id.appname);
		mAppVertion =(TextView)v.findViewById(R.id.vertion);
		
		mAppName.setText("Neelofa"); 
		mAppVertion.setText(Utility.getAPP_VERTION()); 
		
		
		fanLevel=(TextView)v.findViewById(R.id.fantext);
		fanLevel.setText("Fan Level: "+Utility.getUSER_RANK()); 
		
		
		LinearLayout change_pass=(LinearLayout)v.findViewById(R.id.password_layout);
		LinearLayout change_profile=(LinearLayout)v.findViewById(R.id.changeprofile);
		LinearLayout change_banner=(LinearLayout)v.findViewById(R.id.changebanner);
		LinearLayout invite=(LinearLayout)v.findViewById(R.id.invite);
		
		LinearLayout logOut=(LinearLayout)v.findViewById(R.id.logout);

		change_pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final EditText input = new EditText(getActivity());

				input.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);

				new AlertDialog.Builder( getActivity() )
				.setTitle( "Change Password" )
				.setMessage( "Enter New Password" )
				.setPositiveButton( "Submit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String respString=ParseUtilities.getXML(URLS.CHANGE_PASSWORD+Utility.getCredentials(getActivity()).username+"&password="+input.getText(), getActivity());
						String changeResp=ParseUtilities.register(respString);
						if(changeResp.equalsIgnoreCase("success"))
						{
							Utility.Alert("Password Changed succesfully", getActivity());
						}
							
					}
				})
				.setView(input)
				.show();
			}
		});

		change_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				StaticUtil.profileMode="profile";
				Fragment duedateFrag = new UploadActivity();
				
				FragmentTransaction ft  = getFragmentManager().beginTransaction();
			
				ft.replace(R.id.mypage, duedateFrag);
				ft.addToBackStack(null);

				ft.commit();
			//	Intent intent=new Intent(getActivity(),UploadActivity.class);
			//	startActivity(intent);
				// uploadAct
			//	Utility.openHelpDoc(URLS.UPLOADIMAGE,getActivity());
			}
		});

		//kishore
		
		
		change_banner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				StaticUtil.profileMode="bg";
				Fragment duedateFrag = new UploadActivity(); //kishore
				FragmentTransaction ft  = getFragmentManager().beginTransaction();
			
				ft.replace(R.id.mypage, duedateFrag);
				ft.addToBackStack(null);

				ft.commit();
			//	Intent intent=new Intent(getActivity(),UploadActivity.class);
			//	startActivity(intent);
				// uploadAct
			//	Utility.openHelpDoc(URLS.UPLOADIMAGE,getActivity());
			}
		});

		
		logOut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.getThis().finish();
			}
		});
		
	/*	change_cover.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Utility.openHelpDoc(URLS.UPLOADIMAGE,getActivity());
				//Intent intent=new Intent(getActivity(),UploadActivity.class);
				//startActivity(intent);
			}
		});*/

		invite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		return v;
	}
	
	/*String respString,rank;
	public class MyapageDetails extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			respString=ParseUtilities.Login(URLS.LOGIN_URL+Utility.getCredentials(getActivity()).username+"&password="+Utility.getCredentials(getActivity()).password, URLS.MYPAGE_URL, getActivity());
			rank=ParseUtilities.Login(URLS.LOGIN_URL+Utility.getCredentials(getActivity()).username+"&password="+Utility.getCredentials(getActivity()).password, URLS.GET_RANK, getActivity());
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressBar.setVisibility(View.INVISIBLE);
			fanLevel.setText("Fan Rank: "+rank);
			System.out.println(URLS.USERS_IMAGE+respString);
			imageLoader.DisplayImage(URLS.USERS_IMAGE+respString, profileImg);
		}
	}*/
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	//
	
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
