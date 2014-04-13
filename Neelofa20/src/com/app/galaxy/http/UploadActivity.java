package com.app.galaxy.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.mypage.MyPage;
import com.app.galaxy.ui.utility.Utility;

public class UploadActivity extends android.support.v4.app.Fragment{
	
	
    
	private static final int PICK_IMAGE = 1;
	private static final int PICK_Camera_IMAGE = 2;
	private ImageView imgView;
	private Button upload,gallery;
	private Bitmap bitmap;
	private ProgressDialog dialog;
	Uri imageUri;
	
	MediaPlayer mp=new MediaPlayer();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.img_upload,
				null);
		
		imgView = (ImageView)v. findViewById(R.id.imageView);
		upload = (Button)v. findViewById(R.id.imguploadbtn);
		gallery = (Button) v.findViewById(R.id.opengallery);
		gallery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent gintent = new Intent();
				gintent.setType("image/*");
				gintent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
				Intent.createChooser(gintent, "Select Picture"),
				PICK_IMAGE);
	
			}
		});
		
		upload.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (bitmap == null) {
					Toast.makeText(getActivity(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					dialog = ProgressDialog.show(getActivity(), "Uploading",
							"Please wait...", true);
					new ImageGalleryTask().execute();
				}
			}
		});

	

		return v;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
	}
    
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri selectedImageUri = null;
		String filePath = null;
		switch (requestCode) {
				case PICK_IMAGE:
					if (resultCode == Activity.RESULT_OK) {
						selectedImageUri = data.getData();
					}
					break;
				case PICK_Camera_IMAGE:
					 if (resultCode ==getActivity(). RESULT_OK) {
		 		        //use imageUri here to access the image
		 		    	selectedImageUri = imageUri;
		 		    	Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));
				    } else if (resultCode == getActivity().RESULT_CANCELED) {
		 		        Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT).show();
		 		    } else {
		 		    	Toast.makeText(getActivity(), "Picture was not taken", Toast.LENGTH_SHORT).show();
		 		    }
					 break;
			}
		
			if(selectedImageUri != null){
					try {
						// OI FILE Manager
						String filemanagerstring = selectedImageUri.getPath();
			
						// MEDIA GALLERY
						String selectedImagePath = getPath(selectedImageUri);
			
						if (selectedImagePath != null) {
							filePath = selectedImagePath;
						} else if (filemanagerstring != null) {
							filePath = filemanagerstring;
						} else {
							Toast.makeText(getActivity(), "Unknown path",
									Toast.LENGTH_LONG).show();
							Log.e("Bitmap", "Unknown path");
						}
			
						if (filePath != null) {
							decodeFile(filePath);
						} else {
							bitmap = null;
						}
					} catch (Exception e) {
						Toast.makeText(getActivity(), "Internal error",
								Toast.LENGTH_LONG).show();
						Log.e(e.getClass().getName(), e.getMessage(), e);
					}
			}
	
	}

	class ImageGalleryTask extends AsyncTask<Void, Void, String> {
		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... unsued) {
			
			String mode = StaticUtil.profileMode;
			System.out.println("@@@@@@@@"+mode);
			 Bitmap b =bitmap;
	           
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            b.compress(CompressFormat.PNG, 0, baos);
	   
	            try {
	            	HttpClient client =null;
	            	if(mode.equals("profile")){
	            		client = new HttpClient(URLS.UPLOAD_IMAGE);	
	            	}else if(mode.equals("bg"))
	            	{
	            		client = new HttpClient(URLS.UPLOAD_BG_IMAGE);
	            	}
	                client.connectForMultipart();
	                client.addFormPart("username", Utility.getCredentials(getActivity()).username);
	            //    client.addFormPart("param2", param2);
	                client.addFilePart("file_upload", Utility.getCredentials(getActivity()).username+".jpg", baos.toByteArray());
	                client.finishMultipart();
	                String data = client.getResponse();
	               
	                System.out.println(data);
	                if(mode.equals("profile"))
	                {
	                Utility.setPROFILE_IMAGE_URL(ParseUtilities.Login(URLS.LOGIN_URL+ Utility.getCredentials(getActivity()).username+"&password="+ Utility.getCredentials(getActivity()).password, URLS.MYPAGE_URL,getActivity()));//kishore
	                }
	                else if(mode.equals("bg"))
	                {
	                
	                Utility.setBG_PROFILE_IMAGE_URL(ParseUtilities.Login(URLS.LOGIN_URL+ Utility.getCredentials(getActivity()).username+"&password="+ Utility.getCredentials(getActivity()).password, URLS.MYPAGEBG_URL,getActivity()));
	                }

	                
	            }
	            catch(Throwable t) {
	                t.printStackTrace();
	            }
						return "Success";
			// (null);
		}
		
		@Override
		protected void onProgressUpdate(Void... unsued) {

		}

		@Override
		protected void onPostExecute(String sResponse) {
			try {
				if (dialog.isShowing())
					dialog.dismiss();
			} catch (Exception e) {
				Toast.makeText(getActivity(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
			
			Toast.makeText(getActivity(), "Uploaded Succesfully", 5000).show();
			MyPage fragment2 = new MyPage();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.replace(R.id.img_pload, fragment2);
			fragmentTransaction.commit();

			getActivity().getSupportFragmentManager().beginTransaction().remove(UploadActivity.this).commit();
		}

	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor =getActivity().managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		imgView.setImageBitmap(bitmap);

	}

}

