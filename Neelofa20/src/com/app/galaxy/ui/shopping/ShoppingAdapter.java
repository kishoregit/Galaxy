package com.app.galaxy.ui.shopping;

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


public class ShoppingAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;
	String[] description;
	int mode;
	AddonData[] addonData;
	 String[] price;
	public ShoppingAdapter(Context context, int textViewResourceId,
			String[] objects,String[] description,int mode,AddonData[] addonData, String[] price) {
		super(context, textViewResourceId, objects);
		mLoader = new ImageLoader(context);
		this.description=description;
		this.mode=mode;
		this.addonData=addonData;
		this.price=price;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		try {
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(getContext());
				convertView = layoutInflator.inflate(R.layout.shoppingadapter,
						null);
				holder = new ViewHolder();
				holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
				holder.title = (TextView) convertView .findViewById(R.id.title);
				holder.price = (TextView) convertView .findViewById(R.id.price);
	
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();

			try {
				holder.title.setText(description[position]);
				holder.price .setText("$"+price[position]);
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
		ScaleImageView imageView;
		TextView title;
		TextView price;
		TextView likes;
		TextView comments;



	}
}
