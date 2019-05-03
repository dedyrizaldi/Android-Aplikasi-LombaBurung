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

public class Detail_anggota extends Activity {
	String ip="";
	String kode_anggota;
	String kode_anggota0="";
	
	EditText txtkode_anggota;
	EditText txtnama_anggota;
	EditText txtjenis_kelamin;
	EditText txtalamat;
	EditText txttelpon;
	EditText txtemail;
	EditText txtusername;
	EditText txtpassword;
	EditText txtstatus;
	EditText txtketerangan;
	
	
	
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_nama_anggota= "nama_anggota";
	private static final String TAG_jenis_kelamin = "jenis_kelamin";
	private static final String TAG_alamat = "alamat";
	private static final String TAG_telpon = "telpon";
	private static final String TAG_email = "email";
	private static final String TAG_username = "username";
	private static final String TAG_password  = "password";
	private static final String TAG_status = "status";
	private static final String TAG_keterangan = "keterangan";
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_anggota);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);
		txtnama_anggota= (EditText) findViewById(R.id.txtnama_anggota);
		txtjenis_kelamin= (EditText) findViewById(R.id.txtjenis_kelamin);
		txtalamat= (EditText) findViewById(R.id.txtalamat);
		txttelpon= (EditText) findViewById(R.id.txttelpon);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txtusername= (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);
		txtstatus= (EditText) findViewById(R.id.txtstatus);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);

		 
		
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_anggota0 = i.getStringExtra("pk");
		
		if(kode_anggota0.length()>0){
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
				kode_anggota= txtkode_anggota.getText().toString();
				String lnama_anggota= txtnama_anggota.getText().toString();
				String lalamat= txtalamat.getText().toString();
				String ljenis_kelamin= txtjenis_kelamin.getText().toString();
				String ltelpon= txttelpon.getText().toString();
				String lemail= txtemail.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lstatus= txtstatus.getText().toString();
				String lketerangan= txtketerangan.getText().toString();
				
				
				if(kode_anggota.length()<1){lengkapi("kode_anggota");}
				else if(lnama_anggota.length()<1){lengkapi("nama_anggota");}
				else if(lalamat.length()<1){lengkapi("alamat");}
				else if(ljenis_kelamin.length()<1){lengkapi("jenis_kelamin");}
				else if(ltelpon.length()<1){lengkapi("telpon");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else if(lstatus.length()<1){lengkapi("status");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}
				
				else{
					if(kode_anggota0.length()>0){
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
			pDialog = new ProgressDialog(Detail_anggota.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_anggota", kode_anggota0));
						
						String url=ip+"anggota/anggota_detail.php";
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
										txtkode_anggota.setText(kode_anggota0);
										txtnama_anggota.setText(myJSON.getString(TAG_nama_anggota));
										txtjenis_kelamin.setText(myJSON.getString(TAG_jenis_kelamin));
										txtalamat.setText(myJSON.getString(TAG_alamat));
										txttelpon.setText(myJSON.getString(TAG_telpon));
										txtemail.setText(myJSON.getString(TAG_email));
										txtusername.setText(myJSON.getString(TAG_username));
										txtpassword.setText(myJSON.getString(TAG_password));
										txtstatus.setText(myJSON.getString(TAG_status));
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
			pDialog = new ProgressDialog(Detail_anggota.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_anggota= txtkode_anggota.getText().toString();
			String lnama_anggota= txtnama_anggota.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String ljenis_kelamin= txtjenis_kelamin.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lstatus= txtstatus.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_anggota0", kode_anggota0));
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
				params.add(new BasicNameValuePair("nama_anggota", lnama_anggota));
				params.add(new BasicNameValuePair("alamat", lalamat));
				params.add(new BasicNameValuePair("jenis_kelamin", ljenis_kelamin));
				params.add(new BasicNameValuePair("telpon", ltelpon));
				params.add(new BasicNameValuePair("email", lemail));
				params.add(new BasicNameValuePair("username", lusername));
				params.add(new BasicNameValuePair("password", lpassword ));
				params.add(new BasicNameValuePair("status", lstatus));
				params.add(new BasicNameValuePair("keterangan", lketerangan ));
			
				
	
				String url=ip+"anggota/anggota_add.php";
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
			pDialog = new ProgressDialog(Detail_anggota.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_anggota= txtkode_anggota.getText().toString();
			String lnama_anggota= txtnama_anggota.getText().toString();
			String ljenis_kelamin= txtjenis_kelamin.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword  .getText().toString();
			String lstatus= txtstatus .getText().toString();
			String lketerangan = txtketerangan .getText().toString();
			
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_anggota0", kode_anggota0));
			params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
			params.add(new BasicNameValuePair("nama_anggota", lnama_anggota));
			params.add(new BasicNameValuePair("jenis_kelamin", ljenis_kelamin));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("telpon", ltelpon));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));
			params.add(new BasicNameValuePair("status", lstatus));
			params.add(new BasicNameValuePair("keterangan", lketerangan));
			
	
				String url=ip+"anggota/anggota_update.php";
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
			pDialog = new ProgressDialog(Detail_anggota.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota0));

				String url=ip+"anggota/anggota_del.php";
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
