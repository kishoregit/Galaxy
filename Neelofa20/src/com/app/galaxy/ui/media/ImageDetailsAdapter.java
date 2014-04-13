package com.app.galaxy.ui.media;

import com.app.galaxy.http.ImageLoader;
import com.app.galaxy.ui.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageDetailsAdapter extends PagerAdapter {
	Context context;

	ImageLoader imageLoader;
	String[] GalImagesStr ;


	public ImageDetailsAdapter(Context context,String[] GalImagesStr) {
		this.context = context;
		imageLoader=new ImageLoader(context);
		this.GalImagesStr=GalImagesStr;
	}

	@Override
	public int getCount() {
		return GalImagesStr.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(context);
		
		System.out.println(GalImagesStr[position]);
		imageLoader.DisplayImage(GalImagesStr[position], imageView);
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}
}