package com.app.galaxy.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.app.galaxy.ui.media.Multimedia;
import com.app.galaxy.ui.mypage.MyPage;
import com.app.galaxy.ui.news.News;
import com.app.galaxy.ui.shopping.Shopping;
import com.app.galaxy.ui.social.Social;
import com.app.galaxy.ui.utility.Utility;

public class MainActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;
	private static MainActivity mMainAppUI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.bottom_tabs);
		
		try {
			setThis(this);
			String appvertion = "1";
			Utility.setAPP_VERTION(appvertion);
			
			mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
			mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

			Bundle b = new Bundle();
			b.putString("key", "News");
			mTabHost.addTab(mTabHost.newTabSpec("news").setIndicator("News",getResources().getDrawable(R.drawable.news)),
					News.class, b);
			//
			b = new Bundle();
			b.putString("key", "Media");
			mTabHost.addTab(mTabHost.newTabSpec("media")
					.setIndicator("Media",getResources().getDrawable(R.drawable.media)), Multimedia.class, b);
			b = new Bundle();
			b.putString("key", "Social");
			mTabHost.addTab(mTabHost.newTabSpec("social").setIndicator("Social",getResources().getDrawable(R.drawable.social)),
					Social.class, b);

			b = new Bundle();
			b.putString("key", "Shopping");
			mTabHost.addTab(mTabHost.newTabSpec("shopping").setIndicator("Shopping",getResources().getDrawable(R.drawable.shop)),
					Shopping.class, b);

			b = new Bundle();
			b.putString("key", "MyPage");
			mTabHost.addTab(mTabHost.newTabSpec("myPage").setIndicator("My Page",getResources().getDrawable(R.drawable.settings)),
					MyPage.class, b);
		} catch (Exception e) {
			// TODO: handle exception
		}
		/* final com.actionbarsherlock.app.ActionBar actionBar = getActionBar();
		    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
*/
		    // for each of the sections in the app, add a tab to the action bar.
		 //   actionBar.setTitle("Home");

		
		// setContentView(mTabHost);
	}

	private void setThis(MainActivity mainAppUI) {
		mMainAppUI = mainAppUI;
	}
	
	public static MainActivity getThis(){
		return mMainAppUI;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
