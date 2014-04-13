package com.app.galaxy.ui.social;



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


public class FanItem extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	String[] description;
	int mode;
	String[] usernamw;
	public FanItem(Context context, int textViewResourceId,
			String[] objects,String[] description, String[] usernamw) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		this.description=description;
		this.usernamw=usernamw;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		try {
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(getContext());
				convertView = layoutInflator.inflate(R.layout.fanitem,
						null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
				holder.title = (TextView) convertView .findViewById(R.id.textView1);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
		
			if(getItem(position).contains(".png")||getItem(position).contains(".jpg"))
				mLoader.DisplayImage(getItem(position), holder.imageView);
			
			if(usernamw[position]==null)
				usernamw[position]="No Name";
			holder.title.setText(usernamw[position]+" ("+description[position]+")");
			System.out.println(description[position]);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
		TextView title;
	}
}
