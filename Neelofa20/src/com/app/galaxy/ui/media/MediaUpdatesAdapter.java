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
import android.widget.TextView;


public class MediaUpdatesAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	String[] description;
	int mode;
	AddonData[] addonData;
	public MediaUpdatesAdapter(Context context, int textViewResourceId,
			String[] objects,String[] description,int mode,AddonData[] addonData) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		this.description=description;
		this.mode=mode;
		this.addonData=addonData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		try {
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(getContext());
				convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
						null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView .findViewById(R.id.imageView1);
				holder.title = (TextView) convertView .findViewById(R.id.title);
				holder.date = (TextView) convertView .findViewById(R.id.date);
				holder.comments = (TextView) convertView .findViewById(R.id.no_of_comments);
				holder.likes = (TextView) convertView .findViewById(R.id.ratings);

				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();

			try {
				holder.title.setText(description[position]);
				if(addonData!=null)
				{
					holder.likes.setVisibility(View.VISIBLE);
					holder.comments.setVisibility(View.VISIBLE);
					holder.date.setVisibility(View.VISIBLE);
					holder.likes.setText(addonData[position].no_of_views+"");
					holder.comments.setText(addonData[position].no_of_comments+"");
					holder.date.setText(Utility.calulateDate(addonData[position].created_date)+" days ago");
					
				}
				else
				{
					holder.likes.setVisibility(View.INVISIBLE);
					holder.comments.setVisibility(View.INVISIBLE);
					holder.date.setVisibility(View.INVISIBLE);
					
				}
				mLoader.DisplayImage(getItem(position), holder.imageView);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		


		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView title;
		TextView date;
		TextView likes;
		TextView comments;



	}
}
