package com.hobuku.hobu;


import java.io.InputStream;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail_belibooking extends Activity {
	String ip="";
	String kode_beli,GB="avatar.jpg";
	String kode_beli0="";
	
	EditText txtkode_beli;
	EditText txttanggal_beli;
	EditText txtjam_beli;
	EditText txtharga;
	EditText txtkode_anggota;
	EditText txtstatus_beli;
	
	
	EditText txtkode_burung;
	EditText txttanggal_jual;
	
	
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kode_jual = "kode_jual";
	private static final String TAG_tanggal_beli = "tanggal_beli";
	private static final String TAG_jam_beli = "jam_beli";
	private static final String TAG_harga = "harga";
	private static final String TAG_kode_anggota= "kode_anggota";
	private static final String TAG_tanggal_konfirmasi = "tanggal_konfirmasi";
	private static final String TAG_jam_konfirmasi  = "jam_konfirmasi";
	private static final String TAG_bukti_transfer  = "bukti_transfer";
	private static final String TAG_nama_bank = "nama_bank";
	private static final String TAG_catatan = "catatan";
	private static final String TAG_status_beli = "status_beli";

	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_belibooking );
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_beli= (EditText) findViewById(R.id.txtkode_beli);txtkode_beli.setEnabled(false);
		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);txtkode_burung.setEnabled(false);
		txttanggal_jual= (EditText) findViewById(R.id.txttanggal_jual);txttanggal_jual.setEnabled(false);
		
		txttanggal_beli= (EditText) findViewById(R.id.txttanggal_beli);txttanggal_beli.setEnabled(false);
		txtjam_beli= (EditText) findViewById(R.id.txtjam_beli);txtjam_beli.setEnabled(false);
		txtharga= (EditText) findViewById(R.id.txtharga);txtharga.setEnabled(false);
		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);txtkode_anggota.setEnabled(false);
		txtstatus_beli= (EditText) findViewById(R.id.txtstatus_beli);txtstatus_beli.setEnabled(false);


		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_beli0 = i.getStringExtra("pk");
		
		new get().execute();
	

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
			pDialog = new ProgressDialog(Detail_belibooking.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_beli", kode_beli0));
						
						String url=ip+"beli/beli_detail.php";
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
										txtkode_beli.setText(kode_beli0+"-"+myJSON.getString("kode_jual"));
										txtkode_burung.setText(myJSON.getString("jenis_burung")+" - "+myJSON.getString("latin"));
										txttanggal_beli.setText(myJSON.getString(TAG_tanggal_beli));
										txtjam_beli.setText(myJSON.getString(TAG_jam_beli));
										txtharga.setText(myJSON.getString(TAG_harga));
										txttanggal_jual.setText(myJSON.getString("tanggal_jual"));
										txtkode_anggota.setText(myJSON.getString("nama_anggota"));
										txtstatus_beli.setText(myJSON.getString("status_beli"));
										GB=myJSON.getString("gambar");

										
										
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
		protected void onPostExecute(String file_url) {pDialog.dismiss();
		
		if(GB.length()<1){GB="avatar.jpg";}
		String arUrlFoto=ip+"ypathfile/"+GB;
		Log.v("Gambar",arUrlFoto);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		 ImageView bmImage;

		 public DownloadImageTask(ImageView bmImage) {
		     this.bmImage = bmImage;
		 }

		 protected Bitmap doInBackground(String... urls) {
		 String urldisplay = urls[0];
		 Bitmap mIcon11 = null;
		 try {
		   InputStream in = new java.net.URL(urldisplay).openStream();
		   mIcon11 = BitmapFactory.decodeStream(in);
		   }
		 catch (Exception e) {Log.e("Error", e.getMessage());e.printStackTrace();}
		     return mIcon11;
		 }

		 protected void onPostExecute(Bitmap result) {
		  bmImage.setImageBitmap(result); }
		 }
	
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
