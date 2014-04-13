package com.app.galaxy.ui.media;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.galaxy.ui.R;
import com.app.galaxy.ui.media.Multimedia.DownloadTask;
import com.app.galaxy.ui.media.Multimedia.MultiMediaAdapter;
import com.app.galaxy.ui.utility.Utility;

public class Tutorial3 extends android.support.v4.app.Fragment {

	private Button streamButton;
	
	private ImageButton playButton;
	
	private TextView textStreamed;
	
	private boolean isPlaying;
	
	private com.app.galaxy.http.StreamingMediaPlayer audioStreamer;
	public String medid;
	public Tutorial3() {
		// TODO Auto-generated constructor stub
	}
    @Override
	public void onCreate(Bundle icicle) {
    	
		super.onCreate(icicle);

    } 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.tutorial3,
				null);
		
		medid=getArguments().getString("medid");

        initControls(v);

		return v;
	}
    private void initControls(final View v) {
    	textStreamed = (TextView) v.findViewById(R.id.text_kb_streamed);
	/*	streamButton = (Button) v.findViewById(R.id.button_stream);
		streamButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startStreamingAudio(v);
        }});
*/
		playButton = (ImageButton)v. findViewById(R.id.button_play);
		playButton.setEnabled(false);
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (audioStreamer.getMediaPlayer().isPlaying()) {
					audioStreamer.getMediaPlayer().pause();
					playButton.setImageResource(R.drawable.play);
				} else {
					audioStreamer.getMediaPlayer().start();
					audioStreamer.startPlayProgressUpdater();
					playButton.setImageResource(R.drawable.pause);
				}
				isPlaying = !isPlaying;
        }});
    }
    
    private void startStreamingAudio(View v) {
    	try { 
    		final ProgressBar progressBar = (ProgressBar)v. findViewById(R.id.progress_bar);
    		if ( audioStreamer != null) {
    			audioStreamer.interrupt();
    		}
    		audioStreamer = new com.app.galaxy.http.StreamingMediaPlayer(getActivity(),textStreamed, playButton,progressBar);
    		//audioStreamer.startStreaming("http://www.pocketjourney.com/downloads/pj/tutorials/audio.mp3",1717, 214);
    		audioStreamer.startStreaming(medid,5208, 216);
    		streamButton.setEnabled(false);
    	} catch (IOException e) {
	    	Log.e(getClass().getName(), "Error starting to stream audio.", e);            		
    	}
    	    	
    }
}
