package com.app.galaxy.ui.media;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.app.galaxy.ui.R;

public class Video extends Activity {
	MediaPlayer mediaplayer; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		setContentView(R.layout.video);

		try {
			Bundle bundle=getIntent().getExtras();

			String LINK =bundle.getString("videopath");	
			final VideoView videoView =(VideoView)findViewById(R.id.surface_view);
			MediaController mc = new MediaController(this);

			videoView.setMediaController(mc);
			videoView.requestFocus();
			mc.setAnchorView(videoView);
			mc.setMediaPlayer(videoView);
			Uri video = Uri.parse(LINK);
			videoView.setVideoURI(video);

			final ProgressDialog	mProgress = ProgressDialog.show(this, "Loading", "wait");

			videoView.setOnPreparedListener(new OnPreparedListener() {

				public void onPrepared(MediaPlayer arg0) {
					mProgress.dismiss();
					videoView.start();
				}
			});
			
			mProgress.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		//videoView.start();
	}
}