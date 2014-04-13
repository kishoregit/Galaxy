package com.app.galaxy.ui.social;


import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.R.layout;
import com.app.galaxy.ui.utility.Utility;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SocialNetwork extends Fragment {

	private TextView text;

	public SocialNetwork() {
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.soc_network,
				null);

	
		LinearLayout facebook=(LinearLayout)v.findViewById(R.id.facebook);
		LinearLayout twitter=(LinearLayout)v.findViewById(R.id.twitter);
		LinearLayout youtube=(LinearLayout)v.findViewById(R.id.youtube);
		LinearLayout insgt=(LinearLayout)v.findViewById(R.id.instagram);
		ImageView imageView=(ImageView)v.findViewById(R.id.imageView1);
		imageView.setImageResource(R.drawable.linkstop);
		LinearLayout web=(LinearLayout)v.findViewById(R.id.web);
		facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utility.openHelpDoc(URLS.fb,getActivity());

			}
		});

		twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utility.openHelpDoc(URLS.twitter,getActivity());
			}
		});

		youtube.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utility.openHelpDoc(URLS.youtube,getActivity());
			}
		});

		insgt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utility.openHelpDoc(URLS.instagram,getActivity());

			}
		});
		
		web.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utility.openHelpDoc(URLS.web,getActivity());

			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	//

}
