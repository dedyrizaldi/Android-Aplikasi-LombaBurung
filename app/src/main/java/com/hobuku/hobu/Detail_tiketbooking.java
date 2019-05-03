
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
import android.widget.RadioButton;
import android.widget.TextView;

public class Detail_tiketbooking extends Activity {
	String ip="",GB="",bukti_bayar="";
	String kode_tiket;
	String kode_tiket0="";
	
	EditText txtkode_tiket;
	EditText txtkode_lomba;
	EditText txttanggal_beli;
	EditText txtjam_beli;
	EditText txtnomor_gantangan;
	EditText txtstatus;
	RadioButton radPa;
	RadioButton radPi;
	RadioButton radPu;
	String  namabank="BCA";
	EditText txtcatatan ;
	
	
	Button btnProses,btnUpload;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_lomba = "kode_lomba";
	private static final String TAG_tanggal_beli = "tanggal_beli";
	private static final String TAG_jam_beli = "jam_beli";
	private static final String TAG_nomor_gantangan = "nomor_gantangan";
	private static final String TAG_status = "status";
	private static final String TAG_kode_anggota= "kode_anggota";
	private static final String TAG_tanggal_konfirmasi= "tanggal_konfirmasi";
	private static final String TAG_jam_konfirmasi= "jam_konfirmasi";
	private static final String TAG_bukti_transfer= "bukti_transfer";
	private static final String TAG_nama_bank= "nama_bank";
	private static final String TAG_catatan = "catatan";
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_tiketbooking);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_tiket = (EditText) findViewById(R.id.txtkode_tiket);txtkode_tiket.setEnabled(false);
		txtkode_lomba= (EditText) findViewById(R.id.txtkode_lomba);txtkode_lomba.setEnabled(false);
		txtjam_beli= (EditText) findViewById(R.id.txtjam_beli);txtjam_beli.setEnabled(false);
		txttanggal_beli= (EditText) findViewById(R.id.txttanggal_beli);txttanggal_beli.setEnabled(false);
		txtnomor_gantangan = (EditText) findViewById(R.id.txtnomor_gantangan);txtnomor_gantangan.setEnabled(false);
	
		txtcatatan= (EditText) findViewById(R.id.txtcatatan);txtcatatan.setEnabled(true);
		txtstatus= (EditText) findViewById(R.id.txtstatus);txtstatus.setEnabled(false);
		radPa= (RadioButton) findViewById(R.id.radPa);
		radPi= (RadioButton) findViewById(R.id.radPi);
		radPu= (RadioButton) findViewById(R.id.radPu);
		

		btnUpload= (Button) findViewById(R.id.btnupload);
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_tiket0 = i.getStringExtra("pk");
		bukti_bayar= i.getStringExtra("bukti_bayar");
		
			new get().execute();
			btnUpload.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View arg0) {
		            Intent i = new Intent(Detail_tiketbooking.this,UploadToServerTiket.class);
		        	i.putExtra("pk", kode_tiket0);
					i.putExtra("bukti_bayar", "");
		                startActivity(i); 
		                finish();
		        }});

			btnProses.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new update().execute();
				}});
			
		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
		
		
		
		radPa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				namabank="BCA";
			}});
		radPi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				namabank="Mandiri";
			}});
		radPu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				namabank="BRI";
			}});
		if(bukti_bayar.length()<3){
			btnProses.setEnabled(false);
		}
		
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
		  bmImage.setImageBitmap(result); }
		 }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_tiketbooking.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_tiket", kode_tiket0));
						
						String url=ip+"tiket/tiket_detailkeranjang.php";
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
										txtkode_tiket.setText(kode_tiket0);
										txtkode_lomba.setText("Lomba : "+myJSON.getString("nama_lomba") +"\nBurung : "+myJSON.getString("jenis_burung"));
										txttanggal_beli.setText(myJSON.getString(TAG_tanggal_beli));
										txtjam_beli.setText(myJSON.getString(TAG_jam_beli));
										txtnomor_gantangan.setText(myJSON.getString(TAG_nomor_gantangan));
										txtstatus.setText(myJSON.getString(TAG_status));
									    txtcatatan .setText(myJSON.getString(TAG_catatan));
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


	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_tiketbooking.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lcatatan = txtcatatan .getText().toString();
//
//			if(radPa.isChecked()){namabank="BCA : 53462727272";}
//			else if(radPi.isChecked()){namabank="Bri : 120-34535-234534-44";}
//			else {namabank="Mandiri : 232343453545";}
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_tiket", kode_tiket0));
			params.add(new BasicNameValuePair("nama_bank", namabank));
			params.add(new BasicNameValuePair("catatan", lcatatan));
			params.add(new BasicNameValuePair("bukti_bayar", bukti_bayar));
			
			
	
				String url=ip+"tiket/tiket_update.php";
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
			pDialog = new ProgressDialog(Detail_tiketbooking.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_tiket", kode_tiket0));

				String url=ip+"tiket/tiket_del.php";
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
