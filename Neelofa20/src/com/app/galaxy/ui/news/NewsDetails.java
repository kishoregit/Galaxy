package com.app.galaxy.ui.news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;

import com.app.galaxy.data.Details;
import com.app.galaxy.data.MediaData;
import com.app.galaxy.http.Comments;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.id;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.utility.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class NewsDetails extends Fragment{

	String id;
	NewsDetailsDownload newsDownloadTask;
	EditText comment_edit_text;
	ProgressBar progressBar;
	int no_of_views=0;
	int no_of_comments=0;
	public NewsDetails() {
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.newsdetails,
				null);

		progressBar=(ProgressBar)v.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		try {
			id=getArguments().getString("id");
			no_of_views=getArguments().getInt("views");
			no_of_comments=getArguments().getInt("comments");
			newsDownloadTask=new NewsDetailsDownload(getActivity(),id,v);
			newsDownloadTask.execute("");

		} catch (Exception e) {
			// TODO: handle exception
		}

		//	EditText editText=
		return v;
	}

	ArrayList<Comments>comments=new ArrayList<Comments>();
	LinearLayout commentsListView;
	DetailsAdapter detailsAdapter;
	TextView header;
	public class NewsDetailsDownload extends AsyncTask<String, String, String> {

		Context context;
		String itemId;
		Details details;
		View v;
		Bitmap bitmap;
		public NewsDetailsDownload(Context context,String itemId,View v) {
			// TODO Auto-generated constructor stub

			this.context=context;
			this.itemId=itemId;
			this.v=v;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				String response=ParseUtilities.getXML(URLS.ITEMS_DETAILS_URL+itemId, context);
				details=ParseUtilities.getDataDetails(response);
				String commentsResp=ParseUtilities.getXML(URLS.COMMENTS_URL+itemId, context);
				comments=ParseUtilities.getComments(commentsResp);
				System.out.println(URLS.DETAILSIMAGE+details.item_image);
				bitmap=Utility.downloadBitmap(URLS.DETAILSIMAGE+details.item_image);
				Log.i("Image Dimensions",bitmap.getHeight()+""+bitmap.getWidth());
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);	

		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressBar.setVisibility(View.INVISIBLE);
			loadUi();
		}
		public void loadUi() {
			// TODO Auto-generated method stub
			try {
				ImageView imageView=(ImageView)v.findViewById(R.id.detailsImageView);
				TextView textView=(TextView)v.findViewById(R.id.detailstext);
				TextView title=(TextView)v.findViewById(R.id.detailstitle);
				TextView date=(TextView)v.findViewById(R.id.date);

				TextView view=(TextView)v.findViewById(R.id.views);
				TextView comment=(TextView)v.findViewById(R.id.commentss);

				view.setText(no_of_views+"");
				comment.setText(no_of_comments+"");


				System.out.println(details.description);
				textView.setText(details.description);
				title.setText(details.title);


				date.setText(Utility.calulateDate(details.created)+" days ago");

				if(bitmap!=null)
				{
					BitmapDrawable drawableBitmap=new BitmapDrawable(bitmap);
					imageView.setImageBitmap(bitmap);
				}

				comment_edit_text=(EditText)v.findViewById(R.id.commentfield);
				comment_edit_text.clearFocus();
				header=(TextView)v.findViewById(R.id.no_of_comments);
				header.setText(comments.size()+"  Comments");
				commentsListView=(LinearLayout)v.findViewById(R.id.commentslistview);
				detailsAdapter=new DetailsAdapter(getActivity(), comments);

				//	commentsListView.setAdapter(detailsAdapter);

				addcomments(comments);
				Button sendbtn=(Button)v.findViewById(R.id.send_btn);
				sendbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						SendComments sendComments=new SendComments(comment_edit_text.getText().toString());
						sendComments.execute("");
					}
				});

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
	class SendComments extends AsyncTask<String, String, String>
	{
		String comment;
		ProgressDialog dialog;
		String response;
		String login;
		public SendComments(String comment) {
			// TODO Auto-generated constructor stub
			this.comment=comment;
		} 
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressBar.setVisibility(View.VISIBLE);	
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				String loginResult=ParseUtilities.Login(URLS.LOGIN_URL+Utility.getCredentials(getActivity()).username+"&password="+Utility.getCredentials(getActivity()).password,URLS.COMMENT_ON_ITEM+id+"&commenttext="+comment,getActivity());

				if(loginResult.equalsIgnoreCase("success"))
				{
					String commentsResp=ParseUtilities.getXML(URLS.COMMENTS_URL+id, getActivity());
					comments=ParseUtilities.getComments(commentsResp);
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
				comment_edit_text.setText("");
				progressBar.setVisibility(View.INVISIBLE);	
				commentsListView.removeAllViews();
				addcomments(comments);
				header.setText(comments.size()+" Comments");
				commentsListView.refreshDrawableState();

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	private void addcomments(ArrayList<Comments>comments) {
		// TODO Auto-generated method stub
		try {
			for (int i = 0; i < comments.size(); i++) {



				Comments comment=(Comments)comments.get(i);

				LinearLayout lytContainer = (LinearLayout) View.inflate(
						getActivity(), R.layout.comment_item, null);
				TextView username =(TextView)lytContainer.findViewById(R.id.username);
				TextView comments_ = (TextView)lytContainer.findViewById(R.id.comments);
				TextView date = (TextView)lytContainer.findViewById(R.id.date);

				username.setText(comment.userName);
				comments_.setText(comment.commentText);
				date.setText(Utility.calulateDate(comment.commentDate)+" days ago");

				commentsListView.addView(lytContainer);

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

		}

	}

	public void addViews(LinearLayout linearLayout,String user,String comment,String date)
	{

	}
}
class CommentTextView extends TextView
{
	public CommentTextView(Context arg0){
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public void settext(String username,String comments, String date)
	{
		setText(username+"\n"+comments);
	}
	public void settext( String date)
	{
		setText(date);
	}
	@Override
	public void setTextColor(int color) {
		// TODO Auto-generated method stub
		super.setTextColor(Color.DKGRAY);
	}


}