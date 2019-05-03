package com.hobuku.hobu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.RadioButton;
import android.widget.TextView;

public class Daftar extends Activity {
	String ip="",JK="Laki-laki";
	
	EditText txtnama_anggota;
	EditText txtalamat;
	EditText txttelpon;
	EditText txtemail;
	EditText txtusername;
	EditText txtpassword;
	EditText txtnama_bank;
	EditText txtno_rekening;
	
	
	RadioButton radPa;
	RadioButton radPi;
	
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtnama_anggota= (EditText) findViewById(R.id.txtnama_anggota);
		txtalamat= (EditText) findViewById(R.id.txtalamat);
		txttelpon= (EditText) findViewById(R.id.txttelpon);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txtusername= (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);
		radPa= (RadioButton) findViewById(R.id.radPa);
		radPi= (RadioButton) findViewById(R.id.radPi);
		txtnama_bank= (EditText) findViewById(R.id.txtnama_bank);
		txtno_rekening= (EditText) findViewById(R.id.txtno_rekening);
		 
		radPa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				JK="Laki-laki";
			}});
		radPi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				JK="Perempuan";
			}});
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		
		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lnama_anggota= txtnama_anggota.getText().toString();
				String lalamat= txtalamat.getText().toString();
				String ltelpon= txttelpon.getText().toString();
				String lemail= txtemail.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lnama_bank= txtnama_bank.getText().toString();
				String lno_rekening= txtno_rekening.getText().toString();
				
				
				if(lnama_anggota.length()<1){lengkapi("nama_anggota");}
				else if(lalamat.length()<1){lengkapi("alamat");}
				else if(JK.length()<1){lengkapi("jenis_kelamin");}
				else if(ltelpon.length()<1){lengkapi("telpon");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else if(lnama_bank.length()<1){lengkapi("nama_bank");}
				else if(lno_rekening.length()<1){lengkapi("no_rekening");}
				
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

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Daftar.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lnama_anggota= txtnama_anggota.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lnama_bank= txtnama_bank.getText().toString();
			String lno_rekening= txtno_rekening.getText().toString();
			
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("nama_anggota", lnama_anggota));
				params.add(new BasicNameValuePair("alamat", lalamat));
				params.add(new BasicNameValuePair("jenis_kelamin", JK));
				params.add(new BasicNameValuePair("telpon", ltelpon));
				params.add(new BasicNameValuePair("email", lemail));
				params.add(new BasicNameValuePair("username", lusername));
				params.add(new BasicNameValuePair("password", lpassword ));
				params.add(new BasicNameValuePair("nama_bank", lnama_bank ));
				params.add(new BasicNameValuePair("no_rekening", lno_rekening ));
			
				
	
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
			pDialog = new ProgressDialog(Daftar.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lnama_anggota= txtnama_anggota.getText().toString();
			String ljenis_kelamin= JK;
			String lalamat= txtalamat.getText().toString();
			String ltelpon= txttelpon.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword  .getText().toString();		
			String lnama_bank= txtnama_bank  .getText().toString();			
			String lno_rekening=txtno_rekening .getText().toString();
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nama_anggota", lnama_anggota));
			params.add(new BasicNameValuePair("jenis_kelamin", ljenis_kelamin));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("telpon", ltelpon));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));
			params.add(new BasicNameValuePair("nama_bank", lnama_bank));
			params.add(new BasicNameValuePair("no_rekning", lno_rekening));
			
	
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
