package com.app.galaxy.ui.news;

import java.util.ArrayList;

import com.app.galaxy.http.Comments;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.utility.ScaleImageView;
import com.app.galaxy.ui.utility.Utility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DetailsAdapter extends BaseAdapter {

	Context context;
	ArrayList<Comments>comments=new ArrayList<Comments>();
	public DetailsAdapter(Context context,ArrayList<Comments>comments) {
		// TODO Auto-generated constructor stub
		this.comments=comments;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		try {
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(context);
				convertView = layoutInflator.inflate(R.layout.commentlayout,
						null);
				holder = new ViewHolder();
				holder.comments = (TextView) convertView .findViewById(R.id.comment);
				holder.date = (TextView) convertView .findViewById(R.id.date);
				holder.username = (TextView) convertView .findViewById(R.id.username);

				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			Comments comment=comments.get(position);
			holder.comments.setText(comment.commentText);
			holder.username.setText(comment.userName);
			holder.date.setText(comment.commentDate+"days ago");
			Log.d("date", Utility.calulateDate(comment.commentDate)+"days ago");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView username;

		TextView date;
		TextView comments;

	}

}
