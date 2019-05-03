package com.hobuku.hobu;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Detail_caribeli extends Activity {
	int sukses=0;
	String ip="";
	String kode_jual;
	String kode_jual0="";
	
	EditText txtkode_burung;
	EditText txtdeskripsi;
	EditText txtharga;
	EditText txtketerangan;
	EditText txttanggal_jual;
	EditText txtjam_jual;
	EditText txtkode_anggota;
	EditText txtstatus_jual;
	
	String gambar, gambar2, gambar3, gambar4, gambar5; //copy ini
	

	
	Button btnProses;
	Button btnHapus;
	
	private ViewFlipper viewFlipper;
	private float lastX;				//copy dua baris ini

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_burung = "kode_burung";
	private static final String TAG_deskripsi = "deskripsi";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_gambar2 = "gambar2";
	private static final String TAG_gambar3 = "gambar3";
	private static final String TAG_gambar4 = "gambar4";
	private static final String TAG_gambar5 = "gambar5";
	private static final String TAG_harga = "harga";
	private static final String TAG_keterangan = "keterangan";
	private static final String TAG_tanggal_jual = "tanggal_jual";
	private static final String TAG_jam_jual  = "jam_jual";
	private static final String TAG_kode_anggota= "kode_anggota";
	private static final String TAG_nama_penjual= "nama_penjual";
	private static final String TAG_status_jual = "status_jual";
	
	String kode_anggota, nama_anggota, nama_penjual;
	
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_caribeli);
		
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Detail_caribeli.this);
		   Boolean Registered = sharedPref.getBoolean("Registered", false);
		         if (!Registered){
		             finish();
		         }else {
		        	 kode_anggota= sharedPref.getString("kode_anggota", "");
		        	 nama_anggota = sharedPref.getString("nama_anggota", "");
		}
		
		ip=jsonParser.getIP();
		callMarquee();
		
		
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper); // copy ini
		
		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);txtkode_burung.setEnabled(false);
		txtdeskripsi= (EditText) findViewById(R.id.txtdeskripsi);txtdeskripsi.setEnabled(false);
		txtharga= (EditText) findViewById(R.id.txtharga);txtharga.setEnabled(false);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);txtketerangan.setEnabled(false);
		txttanggal_jual= (EditText) findViewById(R.id.txttanggal_jual);txttanggal_jual.setEnabled(false);
		txtjam_jual= (EditText) findViewById(R.id.txtjam_jual);txtjam_jual.setEnabled(false);
		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);txtkode_anggota.setEnabled(false);
		txtstatus_jual= (EditText) findViewById(R.id.txtstatus_jual);txtstatus_jual.setEnabled(false);
		
		 
		
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_jual0 = i.getStringExtra("pk");
		
			new get().execute();
			

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				String lkode_burung= txtkode_burung.getText().toString();
//				String ldeskripsi= txtdeskripsi.getText().toString();
//				String lharga= txtharga.getText().toString();
//				String lketerangan= txtketerangan.getText().toString();
//				String ltanggal_jual= txttanggal_jual.getText().toString();
//				String ljam_jual= txtjam_jual.getText().toString();
//				String lkode_anggota= txtkode_anggota.getText().toString();
//				String lstatus_jual= txtstatus_jual.getText().toString();
//				
//				
//				if(lkode_burung.length()<1){lengkapi("kode_burung");}
//				else if(ldeskripsi.length()<1){lengkapi("deskripsi");}
//				else if(lharga.length()<1){lengkapi("harga");}
//				else if(lketerangan.length()<1){lengkapi("keterangan");}
//				else if(ltanggal_jual.length()<1){lengkapi("tanggal_jual");}
//				else if(ljam_jual.length()<1){lengkapi("jam_jual");}
//				else if(lkode_anggota.length()<1){lengkapi("kode_anggota");}
//				else if(lstatus_jual.length()<1){lengkapi("status_jual");}
//				
//				else{
//				
						new order().execute();
				//	}
				
				
			}});
		
	
		
		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}
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
		  bmImage.setImageBitmap(result); 
		 
		  
		  
		 }
		 }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_caribeli.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_jual", kode_jual0));
						
						String url=ip+"jual/jual_detail.php";
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
										txtkode_burung.setText(myJSON.getString("jenis_burung"));//+" - "+myJSON.getString("latin"));
										txtdeskripsi.setText(myJSON.getString(TAG_deskripsi));
										gambar=myJSON.getString(TAG_gambar);
										gambar2=myJSON.getString(TAG_gambar2);
										gambar3=myJSON.getString(TAG_gambar3);
										gambar4=myJSON.getString(TAG_gambar4);
										gambar5=myJSON.getString(TAG_gambar5);
										txtharga.setText(myJSON.getString(TAG_harga));
										txtketerangan.setText(myJSON.getString(TAG_keterangan));
										txttanggal_jual.setText(myJSON.getString(TAG_tanggal_jual));
										txtjam_jual.setText(myJSON.getString(TAG_jam_jual));
										txtkode_anggota.setText(myJSON.getString("nama_anggota")+" - "+myJSON.getString(TAG_kode_anggota));
										txtstatus_jual.setText(myJSON.getString(TAG_status_jual));
										
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
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		String arUrlFoto=ip+"ypathfile/"+gambar;
		String arUrlFoto2=ip+"ypathfile/"+gambar2;
		String arUrlFoto3=ip+"ypathfile/"+gambar3;
		String arUrlFoto4=ip+"ypathfile/"+gambar4;
		String arUrlFoto5=ip+"ypathfile/"+gambar5;

		new DownloadImageTask((ImageView) findViewById(R.id.myGambarbeli)).execute(arUrlFoto);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar2beli)).execute(arUrlFoto2);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar3beli)).execute(arUrlFoto3);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar4beli)).execute(arUrlFoto4);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar5beli)).execute(arUrlFoto5);

		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class order extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_caribeli.this);
//			pDialog.setMessage("Proses Order burung ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lharga= txtharga.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			String ltanggal_jual= txttanggal_jual.getText().toString();
			String lnama_anggota= txtkode_anggota.getText().toString();
			
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_jual", kode_jual0));
				params.add(new BasicNameValuePair("harga", lharga));
				params.add(new BasicNameValuePair("keterangan", lketerangan));
				params.add(new BasicNameValuePair("tanggal_jual", ltanggal_jual));
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota ));
				params.add(new BasicNameValuePair("nama_penjual",lnama_anggota));
			
				
	
				String url=ip+"beli/beli_add.php";
				Log.v("add",url);
				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
				Log.d("add", json.toString());
				try {
					 sukses = json.getInt(TAG_SUKSES);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		if(sukses>0){sukses();}
		else{gagal();}
		}
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
	 
	 public void sukses(){
		  new AlertDialog.Builder(this)
		  .setTitle("Order Berhasil")
		  .setMessage("Silakan Konfirasi Pembelian Anda / Cek Keranjang...")
		  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dlg, int sumthin) {
				   finish();
			   }}).show();
		 } 
	 
	 public void gagal(){
		  new AlertDialog.Builder(this)
		  .setTitle("Order Gagal")
		  .setMessage("Silakan Cek Jaringan Anda...")
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
 //copy dari sini sampai bawah 
	 @SuppressLint("ResourceType")
	 public boolean onTouchEvent(MotionEvent touchevent) {
	    	switch (touchevent.getAction()) {
	        
	        case MotionEvent.ACTION_DOWN: 
	        	lastX = touchevent.getX();
	            break;
	        case MotionEvent.ACTION_UP: 
	            float currentX = touchevent.getX();
	            if (lastX < currentX) {
	                if (viewFlipper.getDisplayedChild() == 0)
	                	break;
	                
	                viewFlipper.setInAnimation(this, R.layout.slide_in_from_left);
	                viewFlipper.setOutAnimation(this, R.layout.slide_out_to_right);
	                viewFlipper.showNext();
	             }
	             if (lastX > currentX) {
	            	 if (viewFlipper.getDisplayedChild() == 1)
	            		 break;
	    
	            	 viewFlipper.setInAnimation(this, R.layout.slide_in_from_right);
	            	 viewFlipper.setOutAnimation(this, R.layout.slide_out_to_left);
	                 viewFlipper.showPrevious();
	             }
	             break;
	    	 }
	         return false;
	    }
	// sini
}
