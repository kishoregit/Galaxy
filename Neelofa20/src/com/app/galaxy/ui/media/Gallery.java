package com.app.galaxy.ui.media;

import com.app.galaxy.data.AddonData;
import com.app.galaxy.http.ImageLoader;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.utility.ScaleImageView;
import com.app.galaxy.ui.utility.Utility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Gallery extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	String[] description;
	int mode;
	AddonData[] addonData;
	public Gallery(Context context, int textViewResourceId,
			String[] objects,String[] description,int mode,AddonData[] addonData) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		this.description=description;
		this.mode=mode;
		this.addonData=addonData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		try {
			ViewHolder holder;
			
			
					
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(getContext());
				convertView = layoutInflator.inflate(R.layout.gallery,
						null);
				holder = new ViewHolder();
				
				holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
				holder.date = (TextView) convertView .findViewById(R.id.date);
				holder.likes = (TextView) convertView .findViewById(R.id.views);
				holder.comments = (TextView) convertView .findViewById(R.id.comments);
				holder.title = (TextView) convertView .findViewById(R.id.title);
				holder.linearLayout=(LinearLayout)convertView .findViewById(R.id.textlayout);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();

			mLoader.DisplayImage(getItem(position), holder.imageView);
			if(addonData!=null)
			{
				holder.linearLayout.setVisibility(View.VISIBLE);
				holder.date.setVisibility(View.VISIBLE);
				holder.likes.setVisibility(View.VISIBLE);
				holder.comments.setVisibility(View.VISIBLE);
				holder.title.setVisibility(View.VISIBLE);
				holder.date.setText(Utility.calulateDate(addonData[position].created_date)+" days ago");
				holder.likes.setText(addonData[position].no_of_views+"");
				holder.comments.setText(addonData[position].no_of_comments+"");
				holder.title.setText(description[position]);

				System.out.println(addonData[position].no_of_views+"::"+addonData[position].no_of_comments);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
		TextView title;
		TextView date;
		TextView likes;
		TextView comments;

		LinearLayout linearLayout;

	}
}
