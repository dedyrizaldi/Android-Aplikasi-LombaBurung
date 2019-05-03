package com.hobuku.hobu;


import java.util.ArrayList;
import java.util.Calendar;
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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Order_tiket extends Activity {
	String ip="";
	String kode_lomba;
	String kode_lomba0="";
	
	EditText txtkode_lomba;
//	EditText txtkode_burung;
	EditText txtnama_lomba;
	
	EditText txtnomor_gantangan;

	
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	String kode_anggota, nama_anggota;

	private static final String TAG_nama_lomba = "nama_lomba";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_tiket);
		
		
		ip=jsonParser.getIP();
		callMarquee();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Order_tiket.this);
		 Boolean Registered = sharedPref.getBoolean("Registered", false);
         if (!Registered){
             finish();
         }else {
        	 kode_anggota= sharedPref.getString("kode_anggota", "");
        	 nama_anggota = sharedPref.getString("nama_anggota", "");
         }
		
		txtkode_lomba = (EditText) findViewById(R.id.txtkode_lomba);txtkode_lomba.setEnabled(false);
//		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);txtkode_burung.setEnabled(false);
		txtnama_lomba= (EditText) findViewById(R.id.txtnama_lomba);txtnama_lomba.setEnabled(false);
		
		txtnomor_gantangan   = (EditText) findViewById(R.id.txtnomor_gantangan);
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_lomba0 = i.getStringExtra("kode_lomba");
		
//		if(kode_lomba0.length()>0){
			new get().execute();
//			btnProses.setText("Update Data");
//			btnHapus.setVisibility(View.VISIBLE);
//		}
//		else{
//			btnProses.setText("Add New Data");
//			btnHapus.setVisibility(View.GONE);
//		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String nomor_gantangan= txtnomor_gantangan.getText().toString();
				
				if(txtnomor_gantangan.length()<1){lengkapi("Nomor Gantangan");}
				
				else{
						new save().execute();
					}
			}});



		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Order_tiket.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_lomba", kode_lomba0));
						String url=ip+"lomba/lomba_detail.php";
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
										txtkode_lomba.setText(kode_lomba0);
//										txtkode_burung.setText(myJSON.getString("jenis_burung"));
										txtnama_lomba.setText(myJSON.getString(TAG_nama_lomba));
										
										
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
			pDialog = new ProgressDialog(Order_tiket.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_lomba = txtkode_lomba.getText().toString();
			String lnama_lomba= txtnama_lomba.getText().toString();
			String lnomor_gantangan=txtnomor_gantangan.getText().toString();
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_lomba", kode_lomba0));
				params.add(new BasicNameValuePair("nomor_gantangan", lnomor_gantangan));
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
				
	
				String url=ip+"tiket/tiket_add.php";
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

	 public void lengkapi(String item){
		  new AlertDialog.Builder(this)
		  .setTitle("Lengkapi Data")
		  .setMessage("Silakan lengkapi data "+item +" !")
		  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dlg, int sumthin) {
			   }}).show();
		 } 
	 
	 
	 void callMarquee(){
         Calendar cal = Calendar.getInstance();          
         int jam = cal.get(Calendar.HOUR);
         int menit= cal.get(Calendar.MINUTE);
         int detik= cal.get(Calendar.SECOND);

         int tgl= cal.get(Calendar.DATE);
         int bln= cal.get(Calendar.MONTH)+1;
         int thn= cal.get(Calendar.YEAR);

         String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
         String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);
        
         TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
         txtMarquee.setSelected(true);
         String kata="Selamat Datang Aplikasi Android  "+stgl+"/"+sjam+" #";
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
