package com.app.galaxy.ui.shopping;


import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.galaxy.http.ParseUtilities;
import com.app.galaxy.http.URLS;
import com.app.galaxy.ui.LoginScreen;
import com.app.galaxy.ui.R;
import com.app.galaxy.ui.utility.Utility;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class EnterDetails extends Fragment implements TextWatcher{

	String URL;

	ArrayList<String> countryList=new ArrayList<String>();;
	ArrayList<String> zoneIds=new ArrayList<String>();;;


	ArrayList<String> countryListKeys=new ArrayList<String>();;;
	ArrayList<String> zonekeys=new ArrayList<String>();;;

	String selected_country="";
	String selected_zone="";
	String selected_Shipping_country="";
	String selected_Shipping_zone="";
	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox environments.
	private static final String CONFIG_CLIENT_ID = "AXzPUhAPLHM14vl3_1HNsqIifgG-FJqFbJIuhvpVLUNR2UPdWSudhbHiR6ox";
	// when testing in sandbox, this is likely the -facilitator email address. 
	private static final String CONFIG_RECEIVER_EMAIL = "galaxyperson2013-facilitator@gmail.com"; 
	int index=0;;
	String billing_last_name_str="",billing_first_name_str="",billing_phone_1_str="",billing_phone_2_str="",billing_address_1_str="",billing_address_2_str="",
			billing_city_str="",billing_country_id_str="",billing_zone_id_str="",billing_pincode_str="",shipping_last_name_str="",shipping_first_name_str="",shipping_phone_1_str="",
			shipping_phone_2_str="",shipping_address_1_str="",
			shipping_address_2_str="",shipping_city_str="",shipping_country_id_str="",shipping_zone_id_str="",shipping_pincode_str="",price="";

	EditText billing_last_name,billing_first_name,billing_phone_1,billing_phone_2,billing_address_1,billing_address_2,
	billing_city,billing_pincode,shipping_last_name,shipping_first_name,shipping_phone_1,shipping_phone_2,shipping_address_1,
	shipping_address_2,shipping_city,shipping_pincode;
	ArrayAdapter adapter;
	AutoCompleteTextView shipping_country_id,shipping_zone_id,billing_country_id,billing_zone_id;
	public EnterDetails() {
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.order1,
				null);

		try {
			price=getArguments().getString("price");
			String[] details=getArguments().getStringArray("detailsarray");
			
			billing_first_name_str=details[0];
			billing_last_name_str=details[1];
			billing_phone_1_str=details[2];
			billing_phone_2_str=details[3];
			billing_address_1_str=details[4];
			billing_address_2_str=details[5];
			billing_city_str=details[6];
			billing_pincode_str=details[7];
			billing_country_id_str=details[8];
			billing_zone_id_str=details[9];


			//Shipping

			shipping_first_name=(EditText)v.findViewById(R.id.shipping_first_name);
			shipping_last_name=(EditText)v.findViewById(R.id.shipping_last_name);
			shipping_phone_1=(EditText)v.findViewById(R.id.shipping_phone_1);
			shipping_phone_2=(EditText)v.findViewById(R.id.shipping_phone_2);
			shipping_address_1=(EditText)v.findViewById(R.id.shipping_address_1);
			shipping_address_2=(EditText)v.findViewById(R.id.shipping_address_2);
			shipping_city=(EditText)v.findViewById(R.id.shipping_city);
			shipping_pincode=(EditText)v.findViewById(R.id.shipping_pincode);
			shipping_country_id=(AutoCompleteTextView)v.findViewById(R.id.shipping_country_id);
			shipping_zone_id=(AutoCompleteTextView)v.findViewById(R.id.shipping_zone_id);



			shipping_first_name_str=shipping_first_name.getText().toString().replaceAll(" ","%20");
			shipping_last_name_str=shipping_last_name.getText().toString().replaceAll(" ","%20");
			shipping_phone_1_str=shipping_phone_1.getText().toString().replaceAll(" ","%20");
			shipping_phone_2_str=shipping_phone_2.getText().toString().replaceAll(" ","%20");
			shipping_address_1_str=shipping_address_1.getText().toString().replaceAll(" ","%20");
			shipping_address_2_str=shipping_address_2.getText().toString().replaceAll(" ","%20");
			shipping_city_str=shipping_city.getText().toString().replaceAll(" ","%20");
			shipping_pincode_str=shipping_pincode.getText().toString().replaceAll(" ","%20");
			shipping_country_id_str=shipping_country_id.getText().toString().replaceAll(" ","%20");
			shipping_zone_id_str=shipping_zone_id.getText().toString().replaceAll(" ","%20");

			//AutocompleteView
			//countryList=(String[]) Utility.countryList.values().toArray();

			countryList=getArguments().getStringArrayList("countrylist");
			countryListKeys=getArguments().getStringArrayList("countrykeys");
			
			System.out.println(countryList.size()+"     JDJSLS    "+countryListKeys.size());
			shipping_country_id.addTextChangedListener(this);

			adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,countryList);
			shipping_country_id.setAdapter(adapter);

			shipping_country_id.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String selection = (String) arg0.getItemAtPosition(arg2);
					int pos = -1;

					for (int i = 0; i < countryList.size(); i++) {
						if (countryList.get(i).equals(selection)) {
							pos = i;
							break;
						}
					}
					selected_Shipping_country=countryListKeys.get(pos);

					String response=ParseUtilities.getXML(URLS.ZONE_LIST+selected_Shipping_country, getActivity());
					System.out.println(URLS.ZONE_LIST+selected_Shipping_country);

					Utility.zoneList=ParseUtilities.getZones(response);

					zoneIds=new ArrayList<String>();
					zonekeys=new ArrayList<String>();;

					for (int j = 0; j < Utility.zoneList.size(); j++) {

						zoneIds.add(Utility.zoneList.get(j).zone_name);
						zonekeys.add(Utility.zoneList.get(j).zone_id);

					}

					adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,zoneIds);
					shipping_zone_id.setAdapter(adapter);

				}


			});

			shipping_zone_id.addTextChangedListener(this);
			shipping_zone_id.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					String selection = (String) arg0.getItemAtPosition(arg2);
					int pos = -1;

					for (int i = 0; i < zoneIds.size(); i++) {
						if (zoneIds.get(i).equals(selection)) {
							pos = i;
							break;
						}
					}
					selected_zone=zonekeys.get(pos);
				}
			});



			Button order=(Button)v.findViewById(R.id.registerimg);
			order.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SendOrder sendOrder=new SendOrder();
					sendOrder.execute("");
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			//Utility.Alert(e.getMessage(),getActivity());

			
			
		}
		
		return v;
	}

	ProgressDialog pd;
	String respString;

	public class SendOrder extends AsyncTask<String, String, String>{

		ProgressDialog pd;
		int loginStatus;


		public SendOrder() {
			// TODO Auto-generated constructor stub
			try {
				 URL=URLS.SHOP_URL+
							"orderpayment_amount="+price+"&transaction_details=paypal&order_total="+price+"&billing_last_name="+billing_last_name_str+"&billing_first_name="+
							 billing_first_name_str+
							"&billing_phone_1="+billing_phone_1_str+"&billing_phone_2="+billing_phone_2_str+"&billing_address_1="+billing_address_1_str+"&billing_address_2="+
							 billing_address_2_str+


							"&billing_city="+billing_city_str+"&billing_country_id="+selected_country+"&billing_zone_id="+selected_Shipping_zone+"&billing_pincode="+billing_pincode_str+"&shipping_last_name="+
							shipping_last_name_str+"&shipping_first_name="+shipping_first_name_str+"&shipping_phone_1="+shipping_phone_1_str+

							"&shipping_phone_2="+shipping_phone_1_str+"&shipping_address_1="+shipping_address_1_str+"&shipping_address_2="+shipping_address_2_str+
							"&shipping_city="+shipping_city_str+"&shipping_country_id="+selected_Shipping_country+"&shipping_zone_id="+selected_Shipping_zone+"&shipping_pincode="+
							shipping_pincode_str;

			} catch (Exception e) {
				// TODO: handle exception
			}
		
			 
		} 
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "", "", true, false);
		}
		String xml ;
		@Override
		protected String doInBackground(String... params) {
			try {
				System.out.println(URLS.LOGIN_URL+Utility.getCredentials(getActivity()).username+"&password="+Utility.getCredentials(getActivity()).password);
				respString=ParseUtilities.Login(URLS.LOGIN_URL+Utility.getCredentials(getActivity()).username+"&password="+Utility.getCredentials(getActivity()).password, URL, getActivity());
				System.out.println(URL+"\n"+respString);
				
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				pd.cancel();
				if(!respString.equalsIgnoreCase("Session Expired"))
				{
					callPaypal(price);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	//

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					Log.i("paymentExample", confirm.toJSONObject().toString(4));

					// TODO: send 'confirm' to your server for verification.
					// see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
					// for more details.
					parsePaypalResponse( confirm.toJSONObject().toString(4),respString);
//					public static String ZONE_LIST=

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
	}

	private void parsePaypalResponse(String string,String order_Id) {
		// TODO Auto-generated method stub
		String pay_key="",status="";
		try {
			JSONObject jsonObject=new JSONObject(string);
			JSONObject pay=jsonObject.getJSONObject("proof_of_payment");
			JSONObject adaptive=pay.getJSONObject("adaptive_payment");
			
			
			status=adaptive.getString("payment_exec_status");
			pay_key=adaptive.getString("pay_key");
			
			if(status.equalsIgnoreCase("COMPLETED"))
			{
				String url="http://www.neelofa.mobi/Galaxy/index.php?option=com_api&task=transactionstatus&transaction_id=" +
						pay_key+"&transaction_status=successful&order_id="+order_Id;

			String resp=	ParseUtilities.getXML(url,getActivity());
			Log.i("RESP AFTER PAYMENT",resp);
			Utility.Alert("Order Sent Succesfully", getActivity());
			getActivity().getSupportFragmentManager().beginTransaction().remove(EnterDetails.this).commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void callPaypal(final String price) {
		// TODO Auto-generated method stub
		try {
			PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(price), "USD", "Activate User");

			Intent intent = new Intent(getActivity(), PaymentActivity.class);

			intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

			// It's important to repeat the clientId here so that the SDK has it if Android restarts your 
			// app midway through the payment UI flow.
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AXzPUhAPLHM14vl3_1HNsqIifgG-FJqFbJIuhvpVLUNR2UPdWSudhbHiR6ox");
			intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "lucky.nuchi@gmail.com");
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

			startActivityForResult(intent, 0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		

	}


}