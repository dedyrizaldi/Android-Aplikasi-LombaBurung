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
import android.app.ProgressDialog;
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

public class Detail_tiketselesai extends Activity {
	String ip="",GB="";
	String kode_tiket;
	String kode_tiket0="";
	
	EditText txtkode_tiket;
	EditText txtkode_lomba;
	EditText txttanggal_beli;
	EditText txtjam_beli;
	EditText txtnomor_gantangan;
	EditText txtstatus;
	EditText txtkode_anggota;
	
	EditText txtnama_lomba;
	EditText txtkelas_lomba;
	EditText txtsponsor;
	EditText txttanggal_lomba;
	EditText txtpemenang,txtpoint_nilai ;
	//	//nama_lomba  kelas_lomba sponsor tanggal_lomba pemenang point_nilai
	
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
	private static final String TAG_nama_lomba= "nama_lomba";
	private static final String TAG_kelas_lomba= "kelas_lomba";
	private static final String TAG_sponsor= "sponsor";
	private static final String TAG_tanggal_lomba= "tanggal_lomba";
	private static final String TAG_pemenang = "pemenang";
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_tiketselesai);
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_tiket = (EditText) findViewById(R.id.txtkode_tiket);txtkode_tiket.setEnabled(false);
		txtkode_lomba= (EditText) findViewById(R.id.txtkode_lomba);txtkode_lomba.setEnabled(false);
		txtjam_beli= (EditText) findViewById(R.id.txtjam_beli);txtjam_beli.setEnabled(false);
		txttanggal_beli= (EditText) findViewById(R.id.txttanggal_beli);txttanggal_beli.setEnabled(false);
		txtnomor_gantangan = (EditText) findViewById(R.id.txtnomor_gantangan);txtnomor_gantangan.setEnabled(false);
		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);txtkode_anggota.setEnabled(false);
		txtnama_lomba= (EditText) findViewById(R.id.txtnama_lomba);txtnama_lomba.setEnabled(false);
		txtkelas_lomba= (EditText) findViewById(R.id.txtkelas_lomba);txtkelas_lomba.setEnabled(false);
		txtsponsor= (EditText) findViewById(R.id.txtsponsor);txtsponsor.setEnabled(false);
		txttanggal_lomba= (EditText) findViewById(R.id.txttanggal_lomba);txttanggal_lomba.setEnabled(false);
		txtpemenang= (EditText) findViewById(R.id.txtpemenang);txtpemenang.setEnabled(false);
		txtstatus= (EditText) findViewById(R.id.txtstatus);txtstatus.setEnabled(false);
		txtpoint_nilai= (EditText) findViewById(R.id.txtpoint_nilai);txtpoint_nilai.setEnabled(false);

		btnHapus = (Button) findViewById(R.id.btnhapus);btnHapus.setText("Kembali");
 
		Intent i = getIntent(); 
		kode_tiket0 = i.getStringExtra("pk");

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
			pDialog = new ProgressDialog(Detail_tiketselesai.this);
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
						
						String url=ip+"tiket/tiket_detailkonfirmasi.php";
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
										txtkode_anggota.setText(myJSON.getString("nama_anggota"));
										txtnama_lomba.setText(myJSON.getString(TAG_nama_lomba));
										txtkelas_lomba.setText(myJSON.getString(TAG_kelas_lomba));
										txtsponsor.setText(myJSON.getString(TAG_sponsor));
										txttanggal_lomba.setText(myJSON.getString(TAG_tanggal_lomba));
										txtpemenang .setText(myJSON.getString(TAG_pemenang));
										txtpoint_nilai.setText(myJSON.getString("point_nilai"));
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
	
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_tiketkonfirmasi.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_tiket = txtkode_tiket.getText().toString();
//			String lkode_lomba= txtkode_lomba.getText().toString();
//			String ljam_beli= txtjam_beli.getText().toString();
//			String ltanggal_beli= txttanggal_beli.getText().toString();
//			String lnomor_gantangan= txtnomor_gantangan.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String lkode_anggota= txtkode_anggota.getText().toString();
//			String lnama_lomba= txtnama_lomba.getText().toString();
//			String lkelas_lomba= txtkelas_lomba.getText().toString();
//			String lsponsor= txtsponsor.getText().toString();
//			String ltanggal_lomba= txttanggal_lomba.getText().toString();
//			String lpemenang = txtpemenang .getText().toString();
//			
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("kode_tiket0", kode_tiket0));
//				params.add(new BasicNameValuePair("kode_tiket", kode_tiket));
//				params.add(new BasicNameValuePair("kode_lomba", lkode_lomba));
//				params.add(new BasicNameValuePair("jam_beli", ljam_beli));
//				params.add(new BasicNameValuePair("tanggal_beli", ltanggal_beli));
//				params.add(new BasicNameValuePair("nomor_gantangan", lnomor_gantangan));
//				params.add(new BasicNameValuePair("status", lstatus));
//				params.add(new BasicNameValuePair("kode_anggota", lkode_anggota));
//				params.add(new BasicNameValuePair("nama_lomba", lnama_lomba));
//				params.add(new BasicNameValuePair("kelas_lomba", lkelas_lomba));
//				params.add(new BasicNameValuePair("sponsor", lsponsor));
//				params.add(new BasicNameValuePair("tanggal_lomba", ltanggal_lomba));
//				params.add(new BasicNameValuePair("pemenang", lpemenang));
//				
//	
//				String url=ip+"tiket/tiket_add.php";
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
//			pDialog = new ProgressDialog(Detail_tiketkonfirmasi.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_tiket = txtkode_tiket.getText().toString();
//			String lkode_lomba= txtkode_lomba.getText().toString();
//			String ljam_beli= txtjam_beli.getText().toString();
//			String ltanggal_beli= txttanggal_beli.getText().toString();
//			String lnomor_gantangan= txtnomor_gantangan.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String lkode_anggota= txtkode_anggota.getText().toString();
//			String lnama_lomba= txtnama_lomba.getText().toString();
//			String lkelas_lomba= txtkelas_lomba.getText().toString();
//			String lsponsor= txtsponsor.getText().toString();
//			String ltanggal_lomba= txttanggal_lomba.getText().toString();
//			String lpemenang = txtpemenang .getText().toString();
//			
//		
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("kode_tiket0", kode_tiket0));
//			params.add(new BasicNameValuePair("kode_tiket", kode_tiket));
//			params.add(new BasicNameValuePair("kode_lomba", lkode_lomba));
//			params.add(new BasicNameValuePair("jam_beli", ljam_beli));
//			params.add(new BasicNameValuePair("tanggal_beli", ltanggal_beli));
//			params.add(new BasicNameValuePair("nomor_gantangan", lnomor_gantangan));
//			params.add(new BasicNameValuePair("status", lstatus));
//			params.add(new BasicNameValuePair("kode_anggota", lkode_anggota));
//			params.add(new BasicNameValuePair("nama_lomba", lnama_lomba));
//			params.add(new BasicNameValuePair("kelas_lomba", lkelas_lomba));
//			params.add(new BasicNameValuePair("sponsor", lsponsor));
//			params.add(new BasicNameValuePair("tanggal_lomba", ltanggal_lomba));
//			params.add(new BasicNameValuePair("pemenang", lpemenang));
//			
//	
//				String url=ip+"tiket/tiket_update.php";
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
//			pDialog = new ProgressDialog(Detail_tiketkonfirmasi.this);
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
//				params.add(new BasicNameValuePair("kode_tiket", kode_tiket0));
//
//				String url=ip+"tiket/tiket_del.php";
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
//	
//	 public void lengkapi(String item){
//		  new AlertDialog.Builder(this)
//		  .setTitle("Lengkapi Data")
//		  .setMessage("Silakan lengkapi data "+item +" !")
//		  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
//			   public void onClick(DialogInterface dlg, int sumthin) {
//				   	finish();
//			   }}).show();
//		 } 
//	 
	 
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
