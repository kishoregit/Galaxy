package com.app.galaxy.ui.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.app.galaxy.data.Categories;
import com.app.galaxy.data.CountryData;
import com.app.galaxy.data.SubCategories;
import com.app.galaxy.data.ZoneData;

public class Utility {

	private static SharedPreferences myPrefs;
	private static SharedPreferences.Editor prefsEditor;
	private static ArrayList<Categories>categories=new ArrayList<Categories>();
	private static ArrayList<SubCategories>subCategories=new ArrayList<SubCategories>();
	private static ArrayList<SubCategories>shopsubCategories=new ArrayList<SubCategories>();
	private static String PROFILE_IMAGE_URL;
	private static String USER_RANK;
	private static String APP_VERTION;
	private static String BG_PROFILE_IMAGE_URL;//kishore
	//kishore
	public static String getBG_PROFILE_IMAGE_URL() {
		return BG_PROFILE_IMAGE_URL;
	}
	//kishore
	public static void setBG_PROFILE_IMAGE_URL(String bG_PROFILE_IMAGE_URL) {
		BG_PROFILE_IMAGE_URL = bG_PROFILE_IMAGE_URL;
	}

	public static String getPROFILE_IMAGE_URL() {
		return PROFILE_IMAGE_URL;
	}

	public static void setPROFILE_IMAGE_URL(String pROFILE_IMAGE_URL) {
		PROFILE_IMAGE_URL = pROFILE_IMAGE_URL;
	}

	public static String getUSER_RANK() {
		return USER_RANK;
	}

	public static void setUSER_RANK(String uSER_RANK) {
		USER_RANK = uSER_RANK;
	}

	
	public static String getAPP_VERTION() {
		return APP_VERTION;
	}

	public static void setAPP_VERTION(String aPP_VERTION) {
		APP_VERTION = aPP_VERTION;
	}
	public static ArrayList<SubCategories> getShopsubCategories() {
		return shopsubCategories;
	}

	public static void setShopsubCategories(
			ArrayList<SubCategories> shopsubCategories) {
		Utility.shopsubCategories = shopsubCategories;
	}

	public static ArrayList<SubCategories> getMediasubCategories() {
		return mediasubCategories;
	}

	public static void setMediasubCategories(ArrayList<SubCategories> mediasubCategories) {
		Utility.mediasubCategories = mediasubCategories;
	}

	public static ArrayList<SubCategories> getSocialsubCategories() {
		return socialsubCategories;
	}

	public static void setSocialsubCategories(ArrayList<SubCategories> socialsubCategories) {
		Utility.socialsubCategories = socialsubCategories;
	}
	private static ArrayList<SubCategories>mediasubCategories=new ArrayList<SubCategories>();
	private static ArrayList<SubCategories>socialsubCategories=new ArrayList<SubCategories>();
	private static ArrayList<SubCategories>shoppingsubCategories=new ArrayList<SubCategories>();

	public static ArrayList<SubCategories> getShoppingsubCategories() {
		return shoppingsubCategories;
	}

	public static void setShoppingsubCategories(ArrayList<SubCategories> shoppingsubCategories) {
		Utility.shoppingsubCategories = shoppingsubCategories;
	}
	private static ArrayList<Bitmap> subCategoryBitmaps;
	private static ArrayList<Bitmap> subCategoryMediaBitmaps=new ArrayList<Bitmap>();
	private static ArrayList<Bitmap> subCategorySocialBitmaps=new ArrayList<Bitmap>();
	private static ArrayList<Bitmap> subProductMediaBitmaps=new ArrayList<Bitmap>();

	private static ArrayList<String>newscatlist=new ArrayList<String>();
	private static ArrayList<String>mediacatlist=new ArrayList<String>();
	private static ArrayList<String>socialcatlist=new ArrayList<String>();
	private static ArrayList<String>shopcatlist=new ArrayList<String>();

	
	public static ArrayList<String> getShopcatlist() {
		return shopcatlist;
	}

	
	public static void setShopcatlist(ArrayList<String> shopcatlist) {
		for (int i = 0; i < shopcatlist.size(); i++) {
			System.out.println(shopcatlist.get(i));
		}
		Utility.shopcatlist = shopcatlist;
	}

	public static ArrayList<String> getMediacatlist() {
		return mediacatlist;
	}

	public static void setMediacatlist(ArrayList<String> mediacatlist) {
		Utility.mediacatlist = mediacatlist;
	}

	public static ArrayList<String> getSocialcatlist() {
		return socialcatlist;
	}

	public static void setSocialcatlist(ArrayList<String> socialcatlist) {
		Utility.socialcatlist = socialcatlist;
	}

	public static ArrayList<String> getNewscatlist() {
		return newscatlist;
	}

	public static void setNewscatlist(ArrayList<String> newscatlist) {
		Utility.newscatlist = newscatlist;
	}

	public static ArrayList<Bitmap> getSubProductMediaBitmaps() {
		return subProductMediaBitmaps;
	}

	public static void setSubProductMediaBitmaps(
			ArrayList<Bitmap> subProductMediaBitmaps) {
		Utility.subProductMediaBitmaps = subProductMediaBitmaps;
	}
	private static ArrayList<String>folderUrls;

	public static long calulateDate(String mydate)
	{
		SimpleDateFormat mydate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1;
		try {
			date1 = mydate1.parse(mydate);
			Calendar thatDay = Calendar.getInstance();
			thatDay.set(Calendar.DAY_OF_MONTH,date1.getDate());
			thatDay.set(Calendar.MONTH,date1.getMonth()); // 0-11 so 1 less
			thatDay.set(Calendar.YEAR, date1.getYear());

			Calendar today = Calendar.getInstance();

			long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();

			int year = (int) (diff / 365);
			int rest = (int) (diff % 365);
			int month = rest / 30;
			rest = rest % 30;
			int weeks = rest / 7;
			int days = rest % 7;
			long diffDays = calculateDays(today.getTime(),date1);



			return diffDays;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}



	}

	public static long calculateDays(Date dateEarly, Date dateLater) {
		return (dateEarly.getTime() - dateLater.getTime()) / (24 * 60 * 60 * 1000);
	}
	private static String USER_NAME="";
	public static String getUSER_NAME(Context context) {


		return getSharedPreferences(context).getString("user", "");
	}

	public static void openHelpDoc(String strUrl,Context context) {
		// TODO Auto-generated method stub
		try {
			final String HTTPS = "https://";
			/*final String HTTP = "http://";

			{

				if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
					url = HTTP + url;
				}

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				context.startActivity(intent);

			}*/
			if (!strUrl.startsWith("http://") && !strUrl.startsWith("https://")){
			     strUrl= "http://" + strUrl;
			 }


			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl)));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void setUSER_NAME(String uSER_NAME,Context context) {
		prefsEditor=getSharedPreferences(context).edit();
		prefsEditor.putString("user",uSER_NAME);
	}

	public static ArrayList<CountryData> countryList = new  ArrayList<CountryData>();
	public static ArrayList<ZoneData> zoneList = new  ArrayList<ZoneData>();
	public static ArrayList<Bitmap> getSubCategorySocialBitmaps() {
		return subCategorySocialBitmaps;
	}
	public static void setSubCategorySocialBitmaps(
			ArrayList<Bitmap> subCategorySocialBitmaps) {
		Utility.subCategorySocialBitmaps = subCategorySocialBitmaps;
	}

	private static ArrayList<String>loginImageUrls=new ArrayList<String>();
	public static ArrayList<String> getLoginImageUrls() {
		return loginImageUrls;
	}
	public static void setLoginImageUrls(ArrayList<String> loginImageUrls) {
		Utility.loginImageUrls = loginImageUrls;
	}

	public static String[] mediaImageUrls;
	public static String[] newsImageUrls;
	public static String[] mediaFolderImageUrls;

	public static int NEWS_UPDATEMODE=0;
	public static int MEDIA_UPDATEMODE=1;

	public static ArrayList<Bitmap> getSubCategoryMediaBitmaps() {
		return subCategoryMediaBitmaps;
	}


	public static void setSubCategoryMediaBitmaps(
			ArrayList<Bitmap> subCategoryMediaBitmaps) {
		Utility.subCategoryMediaBitmaps = subCategoryMediaBitmaps;
	}
	public static ArrayList<Bitmap> getsubCategoryBitmaps() {
		return subCategoryBitmaps;
	}
	public static void setsubCategoryBitmaps(ArrayList<Bitmap> bitmaps) {
		Utility.subCategoryBitmaps = bitmaps;
	}
	public static ArrayList<Categories> getCategories() {
		return categories;
	}
	public static void setCategories(ArrayList<Categories> categories) {
		subCategoryBitmaps=new ArrayList<Bitmap>();
		Utility.categories = categories;
	}
	public static ArrayList<SubCategories> getSubCategories() {
		return subCategories;
	}
	public static void setSubCategories(ArrayList<SubCategories> subCategories) {
		Utility.subCategories = subCategories;
	}
	public static Bitmap downloadBitmap(String url) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		//forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			//check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode +
						" while retrieving bitmap from " + url);
				return null;

			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that android understands
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// You Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("ImageDownloader", "Something went wrong while" +
					" retrieving bitmap from " + url + e.toString());
		}

		return null;
	}

	public static void setLoginUrlsInShared(ArrayList<String> urls,Context context)
	{
		for (int i = 0; i < urls.size(); i++) {

			prefsEditor=getSharedPreferences(context).edit();
			prefsEditor.putString("urls"+i,urls.get(i));
			prefsEditor.commit();
			
			//System.out.println(urls.get(i));
		}
	}
	public static boolean getLoginUrlsFromShared(ArrayList<String>url,int size,Context context)
	{
		if(size<=0)
			return true;
		ArrayList<String> arrayList=new ArrayList<String>();
		for (int i = 0; i < url.size(); i++) {

			arrayList.add(getSharedPreferences(context).getString("urls"+i, ""));
			System.out.println(getSharedPreferences(context).getString("urls"+i, ""));
		}
		
		if(containsAll(url,arrayList))
			
			return true;
		else
			return false;
	}
	public static boolean containsAll(ArrayList<String> listA, ArrayList<String> listB) {
		Set<String> listAAsSet = new HashSet<String>(listA);

		for (String string : listB) {
			System.out.println(string);

			if(listAAsSet.contains(string)==false) {
				return false;
			}
		}
		return true;
	}
	public static void Alert(String message,Context mContext)
	{
		try {

			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle("");
			dialog.setPositiveButton("Ok", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			dialog.setMessage(message);
			dialog.show();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static ArrayList<String> getImageFilenames(String path,String imageName)
	{
		ArrayList<String> filenames = new ArrayList<String>();
		File dir = new File( path );    
		String[] fileNames = dir.list(new FilenameFilter() { 
			public boolean accept (File dir, String name) {
				if (new File(dir,name).isDirectory())
					return false;
				return name.toLowerCase().endsWith(".png")||name.toLowerCase().endsWith(".jpg")||name.toLowerCase().endsWith(".jpeg");
			}
		});

		if(fileNames!=null)
		{
			for(String bitmapFileName : fileNames) {
				filenames.add(dir.getPath() + "/" + bitmapFileName);
			}
		}
		return filenames;
	}
	public static String[] getImagesUrls(String path,String imageName)
	{
		File dir = new File( path );    
		String[] fileNames = dir.list(new FilenameFilter() { 
			public boolean accept (File dir, String name) {
				if (new File(dir,name).isDirectory())
					return false;
				return name.toLowerCase().endsWith(".png")||name.toLowerCase().endsWith(".jpg")||name.toLowerCase().endsWith(".jpeg");
			}
		});

//		if(fileNames!=null)
//		{
//			for(String bitmapFileName : fileNames) {
//				Bitmap bmp = BitmapFactory.decodeFile(dir.getPath() + "/" + bitmapFileName);
//				// do something with bitmap
//				bitmaps.add(bmp);
//
//			}
//		}
		return fileNames;
	}

	public static boolean savetoSdcard(String fileName,Context context,Bitmap snapShotImage,String dirname) {
		// TODO Auto-generated method stub
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + 
				"/"+dirname+"/";
		File dir = new File(file_path);
		if(!dir.exists())
			dir.mkdirs();
		File file = new File(dir,fileName);
		try {
			FileOutputStream fOut = new FileOutputStream(file);

			snapShotImage.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			// TODO: handle exception
		}


		return true;
	}
	public static SharedPreferences getSharedPreferences(Context context)
	{
		myPrefs = context.getSharedPreferences("myPrefs", context.MODE_WORLD_READABLE);
		prefsEditor = myPrefs.edit();

		return myPrefs;
	}

	public static Credentials getCredentials(Context context) {
		// TODO Auto-generated method stub
		Credentials credentials=new Credentials();
		credentials.username=getSharedPreferences(context).getString("username", "");
		credentials.password=getSharedPreferences(context).getString("password", "");
		credentials.checkBoxStatus=getSharedPreferences(context).getBoolean("checkBoxStatus", false);

		return credentials;

	}
	public static void setCredentials(String userName,String password,Context context,boolean checkBoxStatus) {
		// TODO Auto-generated method stub
		prefsEditor=getSharedPreferences(context).edit();
		prefsEditor.putString("username",userName );
		prefsEditor.putString("password",password );
		prefsEditor.putBoolean("checkBoxStatus",checkBoxStatus );

		
		prefsEditor.commit();
	}

	public static class Credentials
	{
		public String username="";
		public String password="";
		public boolean checkBoxStatus=false;
	}

	public static boolean isOnline(final Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi =
				connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile =
				connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if( wifi.isConnected() ){

			//	 Toast.makeText(context, "Wifi" , Toast.LENGTH_LONG).show();
			return true;
		}
		else if( mobile.isConnected() ){

			//Toast.makeText(context, "Mobile 3G " , Toast.LENGTH_LONG).show();
			return true;
		}
		else
		{
			((Activity) context).runOnUiThread(new Runnable() {
			      public void run() {
						Toast.makeText(context, "No Network " , 5000).show();
			   }});
			return false;
		}

	}
}
