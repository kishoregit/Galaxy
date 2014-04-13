package com.app.galaxy.ui;

import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.utility.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Registeration extends Activity 
{
	
	ProgressBar progressBar;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.register);
		
		progressBar=(ProgressBar)findViewById(R.id.progressBar1);
		
		final EditText email=(EditText)findViewById(R.id.email);
		final EditText password=(EditText)findViewById(R.id.password);
		final EditText confirmpassword=(EditText)findViewById(R.id.confirmpassword);

		final EditText firstname=(EditText)findViewById(R.id.firstname);

		final EditText lastname=(EditText)findViewById(R.id.lastname);

		final EditText username=(EditText)findViewById(R.id.username);
		
		ImageView register=(ImageView)findViewById(R.id.registerimg);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String pass=password.getText().toString();
				String confirmpass=confirmpassword.getText().toString();

				if(pass.equals(confirmpass))
				{
					DoRegister doRegister=new DoRegister(URLS.REGISTER+email.getText().toString()+"&password="+password.getText().toString()+"&firstname="+firstname.getText().toString()+"&lastname="+lastname.getText().toString()+"&username="+username.getText().toString());
					doRegister.execute();
				}
				else
				{
					Utility.Alert("Password Mismatch", getApplicationContext());
				}
			}
		});
	};
	ProgressDialog pd;
	class DoRegister extends AsyncTask<String, String, String>
	{
		String result_res="";
		String URL;
		public DoRegister(String URL) {
			// TODO Auto-generated constructor stub
			this.URL=URL;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//pd = ProgressDialog.show(Registeration.this, "", "", true, true);
			progressBar.setVisibility(View.VISIBLE);
			
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response=ParseUtilities.getXML(URL, Registeration.this);
			

			result_res=ParseUtilities.register(response);
			Log.i("TAG REG",result_res);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result_res.equalsIgnoreCase("success"))
			{

				AlertDialog.Builder builder = new AlertDialog.Builder(Registeration.this);
				builder.setMessage("Registeration Succesfull. Please check your mail to activate the account.")
				       .setCancelable(false)
				       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
								finish();
				           }
				       });
				
				builder.show();
				
			}
			else
			{
				Utility.Alert("Error: "+result_res,Registeration.this);

			}
			Log.i("TAG REG",result_res);

			progressBar.setVisibility(View.INVISIBLE);
		}
	}

	
}
