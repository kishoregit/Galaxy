package com.app.galaxy.ui.media;

import java.io.IOException;
import java.util.ArrayList;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.data.Details;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.id;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.news.NewsDetails;
import com.app.galaxy.ui.social.SocialUpdates;
import com.app.galaxy.ui.utility.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class DownloadDetails extends AsyncTask<String, String, String> {

	Context context;
	String itemId;
	Details details;
	String type;

	private com.app.galaxy.http.StreamingMediaPlayer audioStreamer;
	 AddonData addonData;
	FragmentManager fragmentManager;
	public DownloadDetails(Context context,String itemId,String type,FragmentManager fragmentManager, AddonData addonData) {
		// TODO Auto-generated constructor stub
		this.addonData=addonData;
		this.fragmentManager=fragmentManager;
		this.context=context;
		this.itemId=itemId;
		this.type=type;

		System.out.println(type);
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			String response=ParseUtilities.getXML(URLS.ITEMS_DETAILS_URL+itemId, context);
			details=ParseUtilities.getDataDetails(response);

			System.out.println(details.src+":::"+details.type);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}
	ProgressDialog progressDialog;

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//progressDialog=ProgressDialog.show(context, "Loading...", "");
	}@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try {
			//progressDialog.cancel();
			String link;

			if(type.equals("Videos")||type.equals("Audio"))
			{
				Intent intent=new Intent(context,Video.class);
				if(details.type.equals("youtube"))
				{
					Utility.openHelpDoc("http://www.youtube.com/watch?v="+details.src,context);

				}
				else if(details.type.equals("mp4"))
				{
			
					Fragment duedateFrag = new VideoDetails();

					Bundle args = new Bundle();

					args.putString("id", itemId);
					args.putInt("views", addonData.no_of_views);
					args.putInt("comments", addonData.no_of_comments);
					args.putString("type",details.type);
					args.putString("src", details.src);

					FragmentTransaction ft  = fragmentManager.beginTransaction();
					ft.replace(R.id.newsupdates, duedateFrag);
					ft.addToBackStack(null);
					duedateFrag.setArguments(args);
					ft.commit();

				}
				else
				{

					Fragment duedateFrag = new MediaDetails();

					Bundle args = new Bundle();

					args.putString("id", itemId);
					args.putInt("views", addonData.no_of_views);
					args.putInt("comments", addonData.no_of_comments);

					FragmentTransaction ft  = fragmentManager.beginTransaction();
					ft.replace(R.id.newsupdates, duedateFrag);
					ft.addToBackStack(null);
					duedateFrag.setArguments(args);
					ft.commit();
/*					Tutorial3 duedateFrag = new Tutorial3();
					FragmentTransaction ft  =fragmentManager.beginTransaction();
					Bundle args = new Bundle();
					args.putString("medid",URLS.TEMP_ITEMS_IMG_URL+URLS.TEMP_URL_URL+details.src+"."+details.type);
					duedateFrag.setArguments(args);
					ft.replace(R.id.newsupdates, duedateFrag);
					ft.addToBackStack(null);
					ft.commit();
*/				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


}