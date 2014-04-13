package com.app.galaxy.ui.news;


import com.app.galaxy.http.BackgroundLoader;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.id;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.media.MediaUpdates;
import com.app.galaxy.ui.news.NewsUpdates;
import com.app.galaxy.ui.utility.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class News extends Fragment {

	private TextView text;
	private ImageView goButton;

	boolean netstate,netstate1,netstate2,netstate3,netstate_main;
	
	@Override
	public void onDestroy() {
	System.out.println("News : onDestroy @@@@@@@@@@@@@");
		super.onDestroy();
	}


	@Override
	public void onDestroyView() {
		System.out.println("News : onDestroyView @@@@@@@@@@@@@");
		super.onDestroyView();
	}


	@Override
	public void onLowMemory() {
		System.out.println("News : onLowMemory @@@@@@@@@@@@@");
		super.onLowMemory();
	}


	@Override
	public void onStop() {
		System.out.println("News : onStop @@@@@@@@@@@@@");
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		/*View v = LayoutInflater.from(getActivity()).inflate(R.layout.newshome,
				null);
*/		//Toast.makeText(getActivity(),"Created", Toast.LENGTH_SHORT).show();

		load(view) ;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.newshome,null);
		
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
	

		load(v) ;
		return v;
	}
	private void load(View v) {
		// TODO Auto-generated method stub
		try {
			HelpPagerAdapter adapter = new HelpPagerAdapter();
			ViewPager myPager = (ViewPager) v.findViewById(R.id.customviewpager);

			//adapter.notifyDataSetChanged();
			myPager.setAdapter(adapter);
			myPager.setCurrentItem(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	//
	

public class HelpPagerAdapter extends PagerAdapter{
    int drawId = 0;
    BackgroundLoader backgroundLoader;
	ProgressBar progressBar;
	ImageView ivbackground;
    public HelpPagerAdapter(){
    	backgroundLoader=new BackgroundLoader(getActivity());
    }

    public int getCount() {
    	return Utility.getNewscatlist().size();
    	
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        View view = null ;

    	try {
    		LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;
            Bitmap bitamp =null;
            int buttonId=0;
            switch (position) {
            case 0:
                resId = R.layout.latestnews;
                drawId=0;
                view = inflater.inflate(resId, null);
                progressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
                buttonId=R.id.latestnewsbutton;
                ivbackground = (ImageView)view.findViewById(R.id.ivNewsBackground);
                
                backgroundLoader.DisplayImage(Utility.getNewscatlist().get(0), ivbackground,progressBar);
            	
         	   ImageView goButton = (ImageView)view.findViewById(buttonId);
     			goButton.setOnClickListener(new OnClickListener() {

     				@Override
     				public void onClick(View arg0) {
     					// TODO Auto-generated method stub
     					//boolean netstate=new LoginScreen().haveNetworkConnection();
     					//new LoginScreen().showAlertBox();
     					
     					netstate=haveNetworkConnection();
     					
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
     					}
     					URLS.NEWS_ID=Utility.getSubCategories().get(0).subcatId;
     					Fragment duedateFrag = new NewsUpdates();
     					FragmentTransaction ft  = getFragmentManager().beginTransaction();
     					ft.replace(R.id.newsmainpage, duedateFrag);
     					ft.addToBackStack(null);

     					ft.commit();
     				}
     			});
                break;
            case 1:
                resId = R.layout.news;
                drawId=1;
                buttonId=R.id.newsbutton;

                view = inflater.inflate(resId, null);
                progressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
                ivbackground = (ImageView)view.findViewById(R.id.ivNewsButtonBackground);
                backgroundLoader.DisplayImage(Utility.getNewscatlist().get(1), ivbackground,progressBar);
            	
         	   ImageView goButton1 = (ImageView)view.findViewById(buttonId);
         	   goButton1.setOnClickListener(new OnClickListener() {
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
     					
     					
     					
     					URLS.NEWS_ID=Utility.getSubCategories().get(1).subcatId;
     					Fragment duedateFrag = new NewsUpdates();
     					FragmentTransaction ft  = getFragmentManager().beginTransaction();
     					ft.replace(R.id.newsmainpage, duedateFrag);
     					ft.addToBackStack(null);

     					ft.commit();
     				}
     			});
                break;
            
            }

        
        	  
               collection.addView(view, 0);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
        

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);   
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
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
