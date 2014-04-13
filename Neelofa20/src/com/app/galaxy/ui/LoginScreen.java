package com.app.galaxy.ui;

 

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.galaxy.data.Categories;
import com.app.galaxy.data.SubCategories;
import com.app.galaxy.http.ImageLoader;
import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.utility.CommonUtil;
import com.app.galaxy.ui.utility.Utility;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class LoginScreen extends Activity
{
	private ImageLoader mLoader;
	boolean netstat;
	EditText userText,passEditText;
	private static final long IMAGE_CHANGE_INTERVAL = 4000;
	Handler imgChangeHandler = new Handler();
	int index = 0;
	RelativeLayout relativeLayout;
	ImageView ivbackground;
	
	int clickCount;
	CheckBox checkBox;
	TextView textView1,textView2,register;
	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;
	ProgressBar pd;

	// note that these credentials will differ between live & sandbox environments.
	private static final String CONFIG_CLIENT_ID = "AXzPUhAPLHM14vl3_1HNsqIifgG-FJqFbJIuhvpVLUNR2UPdWSudhbHiR6ox";
	// when testing in sandbox, this is likely the -facilitator email address. 
	private static final String CONFIG_RECEIVER_EMAIL = "galaxyperson2013-facilitator@gmail.com"; 

	String username,password;
	ImageLoader imageLoader;

	ArrayList<String> filenames = new ArrayList<String>();
	int backgroungWidth, backgroundHeight;
	
	// InApp
	//temp fix  boolean mIsSubscriptionActivate = false;
	boolean mIsSubscriptionActivate = true;
	 static final String SKU_PREMIUM =  /*"com.app.galaxysubscription" ;*/ "android.test.purchased"; //"com.app.galaxysubscription";
		int SKU_GAS_VALUE = 0;
		int RC_REQUEST = 10001;
		IabHelper mHelper;
		String TAG = "StartScreen";
		int inAppType;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.loginscreen);

		mLoader = new ImageLoader(this);

		Intent intent = new Intent(this, PayPalService.class);
		//
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
		//
		startService(intent);
		final RelativeLayout relativeLayout1=(RelativeLayout)findViewById(R.id.mainlayout);
		relativeLayout1.setVisibility(View.GONE);
		imageLoader=new ImageLoader(this);
		textView1=(TextView)findViewById(R.id.textView1);
		textView2=(TextView)findViewById(R.id.textView2);
		relativeLayout=(RelativeLayout)findViewById(R.id.login_layout);
		ivbackground = (ImageView) findViewById(R.id.ivbackground);

		pd = (ProgressBar)findViewById(R.id.progressBar1);

		pd.setVisibility(View.INVISIBLE);

		checkBox=(CheckBox)findViewById(R.id.checkBox1);


		userText=(EditText)findViewById(R.id.editText1);
		passEditText=(EditText)findViewById(R.id.editText2);

		Utility.Credentials credentials=Utility.getCredentials(this);
		checkBox.setChecked(credentials.checkBoxStatus);
		userText.setText(credentials.username);
		if(credentials.checkBoxStatus)
			passEditText.setText(credentials.password);

		filenames = Utility.getImageFilenames("sdcard/galaxy/", "");
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		backgroungWidth = displayMetrics.widthPixels;
		backgroundHeight = displayMetrics.heightPixels;
		
		System.out.println("@@@@@@@@@@@@@@@ filenames "+filenames.toString());
		System.out.println("@@@@@@@@@@@@@@@ backgroungWidth "+backgroungWidth+" , backgroundHeight "+backgroundHeight);
		
		if(filenames.size()>0)
		{
//			Drawable d = new BitmapDrawable(getResources(), CommonUtil.decodeSampledBitmapFromFilename(filenames.get(0), backgroungWidth, backgroundHeight));
//			relativeLayout.setBackgroundDrawable(d);
			ivbackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromFilename(filenames.get(0), backgroungWidth, backgroundHeight));
		}
		else
		{
//			Drawable d = new BitmapDrawable(getResources(), CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
//			relativeLayout.setBackgroundDrawable(d);
			ivbackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.background, backgroungWidth, backgroundHeight));
		}
		
		final CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox1);
		final TextView register=(TextView)findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginScreen.this,Registeration.class);
				startActivity(intent);
				//	finish();
			}
		});

		final TextView forgot=(TextView)findViewById(R.id.textView3);
		forgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inputAlertSecondaryDialog(Utility.getCredentials(LoginScreen.this).username);
			}
		});

		imgChangeHandler.postDelayed(run, IMAGE_CHANGE_INTERVAL);


		ImageView ivLoginEnter=(ImageView)findViewById(R.id.imageView1);
		ivLoginEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				netstat=haveNetworkConnection();
				//LocationService locationService=new LocationService(LoginScreen.this);
				showAlertBox();
				System.out.println("network stat: "+netstat);
				
				System.out.println("ivLoginEnter.setOnClickListener: =1===clickCount====>"+clickCount);
				
				Utility.setCredentials(userText.getText().toString(), passEditText.getText().toString(), LoginScreen.this,checkBox.isChecked());
				
				//	Utility.Alert(clickCount+"", LoginScreen.this);
				clickCount++;
				System.out.println("ivLoginEnter.setOnClickListener: =2===clickCount=aftr incr===>"+clickCount);
				if(clickCount==1)
				{
					relativeLayout1.setVisibility(View.VISIBLE);
				}
				if(clickCount>1 && netstat==true)
				{
					System.out.println("ivLoginEnter.setOnClickListener: ===3=clickCount=aftr incr===>"+clickCount);
					clickCount=0;
					hideSoftKeypad(LoginScreen.this, LoginScreen.this.getWindow());
					relativeLayout1.setVisibility(View.GONE);
					if(!(userText.getText().toString().length()>0))
					{
						Utility.Alert("Enter Usename", LoginScreen.this);
						
					}
					else if(!(passEditText.getText().toString().length()>0))
					{
						Utility.Alert("Enter password", LoginScreen.this);
					}
					else
					{
						if(mIsSubscriptionActivate)
						{
							System.out.println("mIsSubscriptionActivate=====");
							subscriptionActivated();
						}
						else
						{
							System.out.println("launchPurchaseFlow=====");
							if(mHelper != null)
								mHelper.launchPurchaseFlow(LoginScreen.this, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener);
						}
						
					}
				}
			}
		});

		
		// InApp
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk4gDay+B7jCBc7f5iEk3zG8L7YIXWvRUzwissHdgqx5TVPKj9jaTVl4z/+xDQEYQbkS8Bkt6zTZjS3Il7gpU83FpP3zqZIjEdCYfQqAoLf4h4H4NwtRG8yTojTFEoI2sdFTfZxR29hvZGdwRIt/tzGolWRg7lyLRO7c314Q/TIM3fByl+IiR/+p1TPDbI1sfKpvkh4L5WMRtIE+jf2qw7I0DvHyoR/AtoHz20UBd2Mo25qKKit6CWyt1V2BXfHBFbXLvZgchaBecN4rwnNIOivpqNQazhXyabeXSa78tcNlaHx6zU95wIwF9Ovi5ZsEdC+Z4kxZBc6V+Qfbm4AZapQIDAQAB";
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.enableDebugLogging(true);
		// Log.d(TAG, "Starting setup.");
		/*
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()
		{
			public void onIabSetupFinished(IabResult result)
			{
				// Log.d(TAG, "Setup finished.");
				if (!result.isSuccess())
				{
					complain("Problem setting up in-app billing: " + result);
					return;
				}
				// Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
		*/

	}

	public void showAlertBox() {
		// TODO Auto-generated method stub
		if(!netstat)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);

			builder.setTitle("Alert !!");
			builder.setMessage("No Internet Connection !!");

			builder.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									Settings.ACTION_WIRELESS_SETTINGS);
							startActivity(intent);

						}
					});

			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							//locationManager.setTestProviderEnabled(locationManager.NETWORK_PROVIDER, true);
							//Toast.makeText(LoginScreen.this, "Network Enabled", Toast.LENGTH_SHORT).show();
							
							finish();
						}
					});

			builder.show();
		}
	}

	
	
	public boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	Runnable run = new Runnable() {
		public void run() {


			//9900076952
			Log.i("INDEX=====", filenames.size()+"");

			if (index == filenames.size())
			{
				index = 0;
				imgChangeHandler.postDelayed(run, IMAGE_CHANGE_INTERVAL);

			}
			else
				if(index>=0&&index < filenames.size())
				{
//					Drawable d = new BitmapDrawable(getResources(), CommonUtil.decodeSampledBitmapFromFilename(filenames.get(index), backgroungWidth, backgroundHeight));
//					relativeLayout.setBackgroundDrawable(d);
					ivbackground.setImageBitmap(CommonUtil.decodeSampledBitmapFromFilename(filenames.get(index), backgroungWidth, backgroundHeight));
					//relativeLayout.setBackgroundDrawable(getDrawable(index++));
					index++;
					imgChangeHandler.postDelayed(run, IMAGE_CHANGE_INTERVAL);

				}
			System.err.println();


		}


	};
	
	public static void hideSoftKeypad(Context _context, Window window) {
		try {
			System.out.println("hideSoftKeypad: ========>");
			IBinder windowToken = window.getCurrentFocus().getWindowToken();
			InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {

		}
	}
	public  void inputAlertSecondaryDialog(final String number)
	{

		final AlertDialog.Builder alert = new AlertDialog.Builder(LoginScreen.this);
		LinearLayout lila1= new LinearLayout(LoginScreen.this);
		lila1.setOrientation(1); //1 is for vertical orientation

		final TextView secondary = new TextView(LoginScreen.this);
		final EditText txtsecondary = new EditText(LoginScreen.this);


		int left = 10;
		int top = 12;
		int right = 10;
		int bottom = 6;

		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.setMargins(left, top, right, bottom);

		txtsecondary.setLayoutParams(params);
		secondary.setLayoutParams(params);

		lila1.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
		secondary.setText("Enter Username");
		lila1.addView(secondary);
		lila1.addView(txtsecondary);    	
		//alert.setIcon(R.drawable.icon);
		alert.setTitle("Forgot Password");
		alert.setView(lila1);

		alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() 
		{   		
			public void onClick(DialogInterface dialog, int whichButton) {
				String phoneSecondary = txtsecondary.getText().toString().trim();
				String	resp=ParseUtilities.getXML(URLS.FORGOT_PASSWORD+phoneSecondary,LoginScreen.this);
				String status=ParseUtilities.register(resp);
				Utility.Alert(status, LoginScreen.this);
			}
		});
		alert.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		alert.show();


	}

	public class LoadMap extends AsyncTask<String, String, String>{

		int loginStatus;
		public LoadMap() {
			// TODO Auto-generated constructor stub


		} 
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setVisibility(View.VISIBLE);

		}
		String xml ;
		@Override
		protected String doInBackground(String... params) {
			try {
				System.out.println("doInBackground:======");
				index=-1;
				String loginResult=ParseUtilities.getXML(URLS.LOGIN_URL+userText.getText().toString()+"&password="+passEditText.getText().toString(),LoginScreen.this);
				Utility.setPROFILE_IMAGE_URL(ParseUtilities.Login(URLS.LOGIN_URL+userText.getText().toString()+"&password="+passEditText.getText().toString(), URLS.MYPAGE_URL, LoginScreen.this));
				Utility.setUSER_RANK(ParseUtilities.Login(URLS.LOGIN_URL+userText.getText().toString()+"&password="+passEditText.getText().toString(), URLS.GET_RANK, LoginScreen.this));
				 Utility.setBG_PROFILE_IMAGE_URL(ParseUtilities.Login(URLS.LOGIN_URL+userText.getText().toString()+"&password="+passEditText.getText().toString(), URLS.MYPAGEBG_URL, LoginScreen.this));//kishore
				Log.i("URL", URLS.LOGIN_URL+userText.getText().toString()+"&password="+passEditText.getText().toString());
				username=userText.getText().toString();
				password=passEditText.getText().toString();
				if(!loginResult.equals(""))
				{

					loginStatus=ParseUtilities.isLogin(loginResult,LoginScreen.this);
					imgChangeHandler.removeCallbacks(run);
					if(loginStatus==1)
					{

						String catResult=ParseUtilities.getXML(URLS.CATEGORIES_URL,LoginScreen.this);
						ArrayList<Categories>categories=ParseUtilities.getCategories(catResult);
						Utility.setCategories(categories);

						Categories category=Utility.getCategories().get(0);
						String subCatResult=ParseUtilities.getXML(URLS.SUB_CATEGORIES_URL+category.catId,LoginScreen.this);
						ArrayList<SubCategories>subCategories=ParseUtilities.getSubCategories(subCatResult,category.catId);

						Utility.setSubCategories(subCategories);
					}

				}


			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent intent=new Intent(LoginScreen.this,MainActivity.class);
			if(Utility.isOnline(LoginScreen.this))
			{
				if(loginStatus==1)
				{
					imgChangeHandler.removeCallbacks(run);

					startActivity(intent);
					finish();
					System.out.println("@@@@@@@@@@@@@@@ ==1====activity finished");
				}
				else if(loginStatus==2)
				{
					System.out.println("@@@@@@@@@@@@@@@ ===2===activity finished");
					callPaypal();
				}
				else if(loginStatus==3)
				{
					Utility.Alert("User Not activated",LoginScreen.this);
				}
				else if(loginStatus==4)
				{
					System.out.println("@@@@@@@@@@@@@@@ ===3===activity finished");
					changePassword();
				}
				else 
				{
					System.out.println("@@@@@@@@@@@@@@@ ==4====activity finished");
					Utility.Alert("No such user found or wrong password entered!  Please re-enter username and password and press the Enter button.", LoginScreen.this);
				}
			}

			try {
				pd.setVisibility(View.INVISIBLE);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		private void changePassword() {
			// TODO Auto-generated method stub
			final EditText input = new EditText(LoginScreen.this);

			input.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);

			new AlertDialog.Builder( LoginScreen.this )
			.setTitle( "Change Password" )
			.setMessage( "Enter New Password" )
			.setPositiveButton( "Submit", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String respString=ParseUtilities.getXML(URLS.CHANGE_PASSWORD+userText.getText().toString()+"&password="+input.getText(),LoginScreen.this);
					System.out.println(URLS.CHANGE_PASSWORD+userText.getText().toString()+"&password="+input.getText());
					String changeResp=ParseUtilities.register(respString);
					System.out.println();
					if(changeResp.equalsIgnoreCase("success"))
					{
						Utility.Alert("Password Changed succesfully",LoginScreen.this);
					}

				}
			})
			.setView(input)
			.show();
		}
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		
		 // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
        	if (resultCode == Activity.RESULT_OK) {
    			PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
    			if (confirm != null) {
    				try {
    					Log.i("paymentExample", confirm.toJSONObject().toString(4));

    					// TODO: send 'confirm' to your server for verification.
    					// see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
    					// for more details.
    					ParseUtilities.paypalSuccess(LoginScreen.this,username,password);
    				} catch (JSONException e) {
    					Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
    				}
    			}
    		}
    		else if (resultCode == Activity.RESULT_CANCELED) {
    			Log.i("paymentExample", "The user canceled.");
    		}
    		else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
    			Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
    		}
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
		
	}

	private void callPaypal() {
		// TODO Auto-generated method stub
		PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("1.75"), "USD", "Activate User");

		Intent intent = new Intent(LoginScreen.this, PaymentActivity.class);

		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

		// It's important to repeat the clientId here so that the SDK has it if Android restarts your 
		// app midway through the payment UI flow.
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AXzPUhAPLHM14vl3_1HNsqIifgG-FJqFbJIuhvpVLUNR2UPdWSudhbHiR6ox");
		intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "");
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

		startActivityForResult(intent, 0);
	}
	
	
	private void subscriptionActivated()
	{
		System.out.println("loadMap=====");
		LoadMap loadMap=new LoadMap();
		loadMap.execute("");
	}

	// InApp @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener()
		{
			public void onQueryInventoryFinished(IabResult result, Inventory inventory)
			{
				if (result.isFailure())
				{
					complain("Failed to query inventory: " + result);
					return;
				}
				
			 Log.d(TAG, "Query inventory was successful.");

            mIsSubscriptionActivate = inventory.hasPurchase(SKU_PREMIUM);
            Log.d(TAG, "User is " + (mIsSubscriptionActivate ? "PREMIUM" : "NOT PREMIUM"));
            if(mIsSubscriptionActivate)
            	subscriptionActivated();
			}
		};

		IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener()
		{
			public void onIabPurchaseFinished(IabResult result, Purchase purchase)
			{
				// Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
				if (result.isFailure())
				{
					alert("Alert", "Purchase failed");
					return;
				}
				
				if (purchase.getSku().equals(SKU_PREMIUM)) {
//	                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
	                subscriptionActivated();
	            }
			}
		};

		private void complain(String message)
		{
			alert("", "Error: " + message);
		}

		private void alert(String title, String message)
		{
			AlertDialog.Builder bld = new AlertDialog.Builder(this);
			bld.setTitle(title);
			bld.setMessage(message);
			bld.setNeutralButton("OK", null);
			bld.create().show();
		}
		// InApp Finished @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
}