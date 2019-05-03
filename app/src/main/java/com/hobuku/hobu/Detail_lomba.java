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

public class Detail_lomba extends Activity {
	String ip="";
	String kode_lomba;
	String kode_lomba0="",nama_lomba,kelas,tanggal;
	
	EditText txtkode_lomba;
	EditText txtkode_burung;
	EditText txtnama_lomba;
	EditText txtkelas_lomba;
	EditText txtharga_tiket ;
	EditText txtsponsor;
	EditText txtpersyaratan;
	EditText txtdeskripsi ;
	EditText txttanggal_lomba;
	EditText txtjam_lomba;
	EditText txtstatus_lomba;
	EditText txtjumlah_gantangan;
	
	ImageView myGambar;
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";


	private static final String TAG_kode_burung = "kode_burung";
	private static final String TAG_nama_lomba = "nama_lomba";
	private static final String TAG_kelas_lomba = "kelas_lomba";
	private static final String TAG_harga_tiket = "harga_tiket";
	private static final String TAG_sponsor = "sponsor";	
	private static final String TAG_persyaratan = "persyaratan";
	private static final String TAG_deskripsi = "deskripsi";
	private static final String TAG_tanggal_lomba= "tanggal_lomba";
	private static final String TAG_jam_lomba= "jam_lomba";
	private static final String TAG_status_lomba= "status_lomba";
	private static final String TAG_jumlah_gantangan= "jumlah_gantangan";
	private static final String TAG_kode_anggota= "kode_anggota";
	private static final String TAG_point_nilai = "point_nilai  ";
	private static final String TAG_keterangan  = "keterangan";
	
	String gambar;
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_lomba2);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_lomba = (EditText) findViewById(R.id.txtkode_lomba);txtkode_lomba.setEnabled(false);
		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);txtkode_burung.setEnabled(false);
		txtnama_lomba= (EditText) findViewById(R.id.txtnama_lomba);txtnama_lomba.setEnabled(false);
		txtkelas_lomba= (EditText) findViewById(R.id.txtkelas_lomba);txtkelas_lomba.setEnabled(false);
		txtharga_tiket = (EditText) findViewById(R.id.txtharga_tiket);txtharga_tiket.setEnabled(false);
		txtsponsor= (EditText) findViewById(R.id.txtsponsor);txtsponsor.setEnabled(false);
		txtpersyaratan = (EditText) findViewById(R.id.txtpersyaratan );txtpersyaratan.setEnabled(false);
		txtdeskripsi= (EditText) findViewById(R.id.txtdeskripsi);txtdeskripsi.setEnabled(false);
		txttanggal_lomba= (EditText) findViewById(R.id.txttanggal_lomba);txttanggal_lomba.setEnabled(false);
		txtjam_lomba= (EditText) findViewById(R.id.txtjam_lomba);txtjam_lomba.setEnabled(false);
		txtstatus_lomba= (EditText) findViewById(R.id.txtstatus_lomba);txtstatus_lomba.setEnabled(false);
		txtjumlah_gantangan= (EditText) findViewById(R.id.txtjumlah_gantangan);txtjumlah_gantangan.setEnabled(false);
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_lomba0 = i.getStringExtra("pk");
		new get().execute();

			btnProses.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View arg0) {
	            Intent i = new Intent(Detail_lomba.this,Gantangan.class);
	            i.putExtra("kode_lomba", kode_lomba0);
	            i.putExtra("nama_lomba", nama_lomba);
	            i.putExtra("kelas", kelas);
	            i.putExtra("tanggal", tanggal);
	                startActivity(i); 
	                
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
		  bmImage.setImageBitmap(result); }
		 }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_lomba.this);
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
										txtkode_burung.setText(myJSON.getString("jenis_burung"));
										txtnama_lomba.setText(myJSON.getString(TAG_nama_lomba));
										txtkelas_lomba.setText(myJSON.getString(TAG_kelas_lomba));
										txtharga_tiket.setText(myJSON.getString(TAG_harga_tiket));
										txtsponsor.setText(myJSON.getString(TAG_sponsor));
										txtpersyaratan.setText(myJSON.getString(TAG_persyaratan));
										txtdeskripsi.setText(myJSON.getString(TAG_deskripsi));
										txttanggal_lomba.setText(myJSON.getString(TAG_tanggal_lomba));
										txtjam_lomba.setText(myJSON.getString(TAG_jam_lomba));
										txtstatus_lomba.setText(myJSON.getString(TAG_status_lomba));
										txtjumlah_gantangan.setText(myJSON.getString(TAG_jumlah_gantangan));
										gambar=myJSON.getString("gambar_brg");
											nama_lomba=myJSON.getString(TAG_nama_lomba);
											kelas=myJSON.getString(TAG_kelas_lomba);
											tanggal=myJSON.getString(TAG_tanggal_lomba)+" "+myJSON.getString(TAG_jam_lomba)+" wib";
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
		if(gambar.length()<1){gambar="avatar.jpg";}
		String arUrlFoto=ip+"ypathfile/"+gambar;
		Log.v("Gambar",arUrlFoto);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_lomba.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_lomba = txtkode_lomba.getText().toString();
//			String lkode_burung= txtkode_burung.getText().toString();
//			String lkelas_lomba= txtkelas_lomba.getText().toString();
//			String lnama_lomba= txtnama_lomba.getText().toString();
//			String lharga_tiket= txtharga_tiket.getText().toString();
//			String lsponsor= txtsponsor.getText().toString();
//			String lpersyaratan= txtpersyaratan.getText().toString();
//			String ldeskripsi= txtdeskripsi.getText().toString();
//			String ltanggal_lomba=txttanggal_lomba.getText().toString();
//			String ljam_lomba=txtjam_lomba.getText().toString();
//			String lstatus_lomba= txtstatus_lomba.getText().toString();
//			String ljumlah_gantangan=txtjumlah_gantangan.getText().toString();
//				
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("kode_lomba0", kode_lomba0));
//				params.add(new BasicNameValuePair("kode_lomba", kode_lomba));
//				params.add(new BasicNameValuePair("kode_burung", lkode_burung));
//				params.add(new BasicNameValuePair("kelas_lomba", lkelas_lomba));
//				params.add(new BasicNameValuePair("nama_lomba", lnama_lomba));
//				params.add(new BasicNameValuePair("harga_tiket", lharga_tiket));
//				params.add(new BasicNameValuePair("sponsor", lsponsor));
//				params.add(new BasicNameValuePair("persyaratan", lpersyaratan));
//				params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
//				params.add(new BasicNameValuePair("tanggal_lomba", ltanggal_lomba ));
//				params.add(new BasicNameValuePair("jam_lomba", ljam_lomba));
//				params.add(new BasicNameValuePair("status_lomba", lstatus_lomba));
//				params.add(new BasicNameValuePair("jumlah_gantangan", ljumlah_gantangan));
//					
//	
//				String url=ip+"lomba/lomba_add.php";
//				Log.v("add",url);
//				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//				Log.d("add", json.toString());
//				try {
//					int sukses = json.getInt(TAG_SUKSES);
//					if (sukses == 1) {
//						Intent i = getIntent();
//						setResult(100, i);
//						finish();
//					} else {
//						// gagal update data
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//
//	class update extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_lomba.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_lomba = txtkode_lomba.getText().toString();
//			String lkode_burung= txtkode_burung.getText().toString();
//			String lkelas_lomba= txtkelas_lomba.getText().toString();
//			String lnama_lomba= txtnama_lomba.getText().toString();
//			String lharga_tiket= txtharga_tiket.getText().toString();
//			String lsponsor= txtsponsor.getText().toString();
//			String lpersyaratan= txtpersyaratan.getText().toString();
//			String ldeskripsi= txtdeskripsi.getText().toString();
//			String ltanggal_lomba=txttanggal_lomba .getText().toString();
//			String ljam_lomba=txtjam_lomba.getText().toString();
//			String lstatus_lomba= txtstatus_lomba.getText().toString();
//			String ljumlah_gantangan=txtjumlah_gantangan.getText().toString();
//		
//			
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("kode_lomba0", kode_lomba0));
//			params.add(new BasicNameValuePair("kode_lomba", kode_lomba));
//			params.add(new BasicNameValuePair("kode_burung", lkode_burung));
//			params.add(new BasicNameValuePair("kelas_lomba", lkelas_lomba));
//			params.add(new BasicNameValuePair("nama_lomba", lnama_lomba));
//			params.add(new BasicNameValuePair("harga_tiket", lharga_tiket));
//			params.add(new BasicNameValuePair("sponsor", lsponsor));
//			params.add(new BasicNameValuePair("persyaratan", lpersyaratan));
//			params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
//			params.add(new BasicNameValuePair("tanggal_lomba", ltanggal_lomba ));
//			params.add(new BasicNameValuePair("jam_lomba", ljam_lomba));
//			params.add(new BasicNameValuePair("status_lomba", lstatus_lomba));
//			params.add(new BasicNameValuePair("jumlah_gantangan", ljumlah_gantangan));
//		
//				String url=ip+"lomba/lomba_update.php";
//				Log.v("update",url);
//				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//				Log.d("add", json.toString());
//				try {
//					int sukses = json.getInt(TAG_SUKSES);
//					if (sukses == 1) {
//						Intent i = getIntent();
//						setResult(100, i);
//						finish();
//					} else {
//						// gagal update data
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//	
//	class del extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_lomba.this);
//			pDialog.setMessage("Menghapus data...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//
//		protected String doInBackground(String... args) {
//			int sukses;
//			try {
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("kode_lomba", kode_lomba0));
//
//				String url=ip+"lomba/lomba_del.php";
//				Log.v("delete",url);
//				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
//				Log.d("delete", json.toString());
//				sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				}
//			} 
//			catch (JSONException e) {e.printStackTrace();}
//			return null;  
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
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
