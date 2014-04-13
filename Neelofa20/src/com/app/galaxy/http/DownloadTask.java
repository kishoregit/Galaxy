package com.app.galaxy.http;

import java.util.ArrayList;

import com.app.galaxy.data.SubCategories;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<String, String, String>{

	Context context;
	String URL;
	String catId;
	public DownloadTask(Context context,String catId,Activity acticity) {
		// TODO Auto-generated constructor stub

		this.context=context;
		this.catId=catId;
	}
	ProgressDialog pd;
	String subCatResult;
	ArrayList<SubCategories>subCategories;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		pd = ProgressDialog.show(context, "", "", true, true);
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.cancel();
		
	}
}
