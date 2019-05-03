package com.hobuku.hobu;


import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Detail_user extends Activity {
	String ip="";
	String kode_user;
	String kode_user0="";
	
	EditText txtkode_user;
	EditText txtnama_user;
	EditText txtemail;
	EditText txttelpon;
	EditText txtusername;
	EditText txtpassword;
	EditText txtketerangan;
	

	
	
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_user= "nama_user";
	private static final String TAG_email = "email";
	private static final String TAG_telpon = "telpon";
	private static final String TAG_username = "username";
	private static final String TAG_password= "password";
	private static final String TAG_keterangan= "keterangan";

	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_user);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_user= (EditText) findViewById(R.id.txtkode_user);
		txtnama_user= (EditText) findViewById(R.id.txtnama_user);
		txttelpon= (EditText) findViewById(R.id.txttelpon);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txtusername = (EditText) findViewById(R.id.txtusername);
		txtpassword = (EditText) findViewById(R.id.txtpassword);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);
		
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_user0 = i.getStringExtra("pk");
		
		if(kode_user0.length()>0){
			new get().execute();
			btnProses.setText("Update Data");
			btnHapus.setVisibility(View.VISIBLE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				kode_user= txtkode_user.getText().toString();
				String lnama_user= txtnama_user.getText().toString();
				String ltelpon= txttelpon.getText().toString();
				String lemail= txtemail.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lketerangan= txtketerangan.getText().toString();
				
				
				
				if(kode_user.length()<1){lengkapi("kode_user");}
				else if(lnama_user.length()<1){lengkapi("nama_user");}
				else if(ltelpon.length()<1){lengkapi("telpon");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}
		
				
				else{
					if(kode_user0.length()>0){
						new update().execute();
					}
					else{
						new save().execute();
					}
				}//else
				
			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new del().execute();
			}});
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_user.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_user", kode_user0));
						
						String url=ip+"user/user_detail.php";
						Log.v("detail",url);
						JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
						Log.d("detail", json.toString());
						sukses = json.getInt(TAG_SUKSES);
						if (sukses == 1) {
							JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
							final JSONObject myJSON = myObj.getJSONObject(0);
							runOnUiThread(new Runnable() {
							public void run() {	
								try {
										txtkode_user.setText(kode_user0);
										txtnama_user.setText(myJSON.getString(TAG_user));
										txtemail.setText(myJSON.getString(TAG_email));
										txttelpon.setText(myJSON.getString(TAG_telpon));
										txtusername.setText(myJSON.getString(TAG_username));
										txtpassword.setText(myJSON.getString(TAG_password));
										txtketerangan.setText(myJSON.getString(TAG_keterangan));
									
										
								} 
								catch (JSONException e) {e.printStackTrace();}
							}});
						}
						else{
							// jika id tidak ditemukan
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			return null;
		}
		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_user.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_user= txtkode_user.getText().toString();
			String lnama_user= txtnama_user.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
			
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_user0", kode_user0));
				params.add(new BasicNameValuePair("kode_user", kode_user));
				params.add(new BasicNameValuePair("nama_user", lnama_user));
				params.add(new BasicNameValuePair("telpon", ltelpon));
				params.add(new BasicNameValuePair("email", lemail));
				params.add(new BasicNameValuePair("username", lusername));
				params.add(new BasicNameValuePair("password", lpassword));
				params.add(new BasicNameValuePair("keterangan", lketerangan));
				
			
				
	
				String url=ip+"user/user_add.php";
				Log.v("add",url);
				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
				Log.d("add", json.toString());
				try {
					int sukses = json.getInt(TAG_SUKSES);
					if (sukses == 1) {
						Intent i = getIntent();
						setResult(100, i);
						finish();
					} else {
						// gagal update data
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_user.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_user= txtkode_user.getText().toString();
			String lnama_user= txtnama_user.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
			
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_user0", kode_user0));
			params.add(new BasicNameValuePair("kode_user", kode_user));
			params.add(new BasicNameValuePair("nama_user", lnama_user));
			params.add(new BasicNameValuePair("telpon", ltelpon));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));
			params.add(new BasicNameValuePair("keterangan", lketerangan));
		
			
	
				String url=ip+"user/user_update.php";
				Log.v("update",url);
				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
				Log.d("add", json.toString());
				try {
					int sukses = json.getInt(TAG_SUKSES);
					if (sukses == 1) {
						Intent i = getIntent();
						setResult(100, i);
						finish();
					} else {
						// gagal update data
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
	class del extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_user.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_user", kode_user0));

				String url=ip+"user/user_del.php";
				Log.v("delete",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
				Log.d("delete", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;  
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
	 public void lengkapi(String item){
		  new AlertDialog.Builder(this)
		  .setTitle("Lengkapi Data")
		  .setMessage("Silakan lengkapi data "+item +" !")
		  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dlg, int sumthin) {
				   	finish();
			   }}).show();
		 } 
	 
	 void callMarquee(){
	        
         TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
         txtMarquee.setSelected(true);
         String kata=jsonParser.getName();
         String kalimat=String.format("%1$s",TextUtils.htmlEncode(kata));
         txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat)); 
  
}
 
	 
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
			}
			return super.onKeyDown(keyCode, event);
			}   
			 
}
