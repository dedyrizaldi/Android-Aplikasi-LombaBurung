//package com.example.hobu;
//
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.Html;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class CopyOfDetail_beli extends Activity {
//	String ip="";
//	String kode_beli;
//	String kode_beli0="";
//	
//	EditText txtkode_beli;
//	EditText txtkode_jual;
//	EditText txttanggal_beli;
//	EditText txtjam_beli;
//	EditText txtharga;
//	EditText txtkode_anggota;
//	EditText txttanggal_konfirmasi;
//	EditText txtjam_konfirmasi;
//	EditText txtbukti_transfer;
//	EditText txtnama_bank;
//	EditText txtcatatan;
//	EditText txtstatus_beli;
//	
//	
//	
//	Button btnProses;
//	Button btnHapus;
//
//	private ProgressDialog pDialog;
//	JSONParser jsonParser = new JSONParser();
//
//	private static final String TAG_SUKSES = "sukses";
//	private static final String TAG_record = "record";
//
//	private static final String TAG_kode_jual = "kode_jual";
//	private static final String TAG_tanggal_beli = "tanggal_beli";
//	private static final String TAG_jam_beli = "jam_beli";
//	private static final String TAG_harga = "harga";
//	private static final String TAG_kode_anggota= "kode_anggota";
//	private static final String TAG_tanggal_konfirmasi = "tanggal_konfirmasi";
//	private static final String TAG_jam_konfirmasi  = "jam_konfirmasi";
//	private static final String TAG_bukti_transfer  = "bukti_transfer";
//	private static final String TAG_nama_bank = "nama_bank";
//	private static final String TAG_catatan = "catatan";
//	private static final String TAG_status_beli = "status_beli";
//
//	@Override  
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.detail_beli );
//		
//		ip=jsonParser.getIP();
//		callMarquee();
//		
//		txtkode_beli= (EditText) findViewById(R.id.txtkode_beli);
//		txtkode_jual= (EditText) findViewById(R.id.txtkode_jual);
//		txttanggal_beli= (EditText) findViewById(R.id.txttanggal_beli);
//		txtjam_beli= (EditText) findViewById(R.id.txtjam_beli);
//		txtharga= (EditText) findViewById(R.id.txtharga);
//		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);
//		txttanggal_konfirmasi= (EditText) findViewById(R.id.txttanggal_konfirmasi);
//		txtjam_konfirmasi= (EditText) findViewById(R.id.txtjam_konfirmasi);
//		txtbukti_transfer= (EditText) findViewById(R.id.txtbukti_transfer);
//		txtnama_bank= (EditText) findViewById(R.id.txtnama_bank);
//		txtcatatan= (EditText) findViewById(R.id.txtcatatan);
//		txtstatus_beli= (EditText) findViewById(R.id.txtstatus_beli);
//
//		 
//		
//		
//		btnProses= (Button) findViewById(R.id.btnproses);
//		btnHapus = (Button) findViewById(R.id.btnhapus);
// 
//		Intent i = getIntent(); 
//		kode_beli0 = i.getStringExtra("pk");
//		
//		if(kode_beli0.length()>0){
//			new get().execute();
//			btnProses.setText("Order");
//			btnHapus.setVisibility(View.VISIBLE);
//		}
//		else{
//			btnProses.setText("Add New Data");
//			btnHapus.setVisibility(View.GONE);
//		}
//
////		btnProses.setOnClickListener(new View.OnClickListener() {
////			@Override
////			public void onClick(View arg0) {
////				kode_beli= txtkode_beli.getText().toString();
////				String lkode_jual= txtkode_jual.getText().toString();
////				String ljam_beli= txtjam_beli.getText().toString();
////				String ltanggal_beli= txttanggal_beli.getText().toString();
////				String lharga= txtharga.getText().toString();
////				String lkode_anggota= txtkode_anggota.getText().toString();
////				String ltanggal_konfirmasi= txttanggal_konfirmasi.getText().toString();
////				String ljam_konfirmasi= txtjam_konfirmasi.getText().toString();
////				String lbukti_transfer= txtbukti_transfer.getText().toString();
////				String lnama_bank= txtnama_bank.getText().toString();
////				String lcatatan= txtcatatan.getText().toString();
////				String lstatus_beli= txtstatus_beli.getText().toString();
////				
////				
////				if(kode_beli.length()<1){lengkapi("kode_beli");}
////				else if(lkode_jual.length()<1){lengkapi("kode_jual");}
////				else if(ljam_beli.length()<1){lengkapi("jam_beli");}
////				else if(ltanggal_beli.length()<1){lengkapi("tanggal_beli");}
////				else if(lharga.length()<1){lengkapi("harga");}
////				else if(lkode_anggota.length()<1){lengkapi("kode_anggota");}
////				else if(ltanggal_konfirmasi.length()<1){lengkapi("tanggal_konfirmasi");}
////				else if(ljam_konfirmasi.length()<1){lengkapi("jam_konfirmasi");}
////				else if(lbukti_transfer.length()<1){lengkapi("bukti_transfer");}
////				else if(lnama_bank.length()<1){lengkapi("nama_bank");}
////				else if(lcatatan.length()<1){lengkapi("catatan");}
////				else if(lstatus_beli.length()<1){lengkapi("status_beli");}
////				
////				else{
////					if(kode_beli0.length()>0){
////						new update().execute();
////					}
////					else{
////						new save().execute();
////					}
////				}//else
////				
////			}});
//
//		btnHapus.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}});
//	}
//
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//	class get extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(CopyOfDetail_beli.this);
//			pDialog.setMessage("Load data detail. Silahkan tunggu...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... params) {
//					int sukses;
//					try {
//						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//						params1.add(new BasicNameValuePair("kode_beli", kode_beli0));
//						
//						String url=ip+"beli/beli_detail.php";
//						Log.v("detail",url);
//						JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
//						Log.d("detail", json.toString());
//						sukses = json.getInt(TAG_SUKSES);
//						if (sukses == 1) {
//							JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
//							final JSONObject myJSON = myObj.getJSONObject(0);
//							runOnUiThread(new Runnable() {
//							public void run() {	
//								try {
//										txtkode_beli.setText(kode_beli0);
//										txtkode_jual.setText(myJSON.getString(TAG_kode_jual));
//										txttanggal_beli.setText(myJSON.getString(TAG_tanggal_beli));
//										txtjam_beli.setText(myJSON.getString(TAG_jam_beli));
//										txtharga.setText(myJSON.getString(TAG_harga));
//										txtkode_anggota.setText(myJSON.getString(TAG_kode_anggota));
//										txttanggal_konfirmasi.setText(myJSON.getString(TAG_tanggal_konfirmasi));
//										txtjam_konfirmasi.setText(myJSON.getString(TAG_jam_konfirmasi));
//										txtbukti_transfer.setText(myJSON.getString(TAG_bukti_transfer));
//										txtnama_bank.setText(myJSON.getString(TAG_nama_bank));
//										txtcatatan.setText(myJSON.getString(TAG_catatan));
//										txtstatus_beli.setText(myJSON.getString(TAG_status_beli));
//										
//								} 
//								catch (JSONException e) {e.printStackTrace();}
//							}});
//						}
//						else{
//							// jika id tidak ditemukan
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//			return null;
//		}
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(CopyOfDetail_beli.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_beli= txtkode_beli.getText().toString();
//			String lkode_jual= txtkode_jual.getText().toString();
//			String ljam_beli= txtjam_beli.getText().toString();
//			String ltanggal_beli= txttanggal_beli.getText().toString();
//			String lharga= txtharga.getText().toString();
//			String lkode_anggota= txtkode_anggota.getText().toString();
//			String ltanggal_konfirmasi= txttanggal_konfirmasi.getText().toString();
//			String ljam_konfirmasi= txtjam_konfirmasi.getText().toString();
//			String lbukti_transfer= txtbukti_transfer.getText().toString();
//			String lnama_bank= txtnama_bank.getText().toString();
//			String lcatatan= txtcatatan.getText().toString();
//			String lstatus_beli= txtstatus_beli.getText().toString();
//			
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("kode_beli0", kode_beli0));
//				params.add(new BasicNameValuePair("kode_beli", kode_beli));
//				params.add(new BasicNameValuePair("kode_jual", lkode_jual));
//				params.add(new BasicNameValuePair("jam_beli", ljam_beli));
//				params.add(new BasicNameValuePair("tanggal_beli", ltanggal_beli));
//				params.add(new BasicNameValuePair("harga", lharga));
//				params.add(new BasicNameValuePair("kode_anggota", lkode_anggota));
//				params.add(new BasicNameValuePair("tanggal_konfirmasi", ltanggal_konfirmasi));
//				params.add(new BasicNameValuePair("jam_konfirmasi", ljam_konfirmasi ));
//				params.add(new BasicNameValuePair("bukti_transfer", lbukti_transfer));
//				params.add(new BasicNameValuePair("nama_bank", lnama_bank));
//				params.add(new BasicNameValuePair("catatan", lcatatan));
//				params.add(new BasicNameValuePair("status_beli", lstatus_beli));
//			
//				
//	
//				String url=ip+"beli/beli_add.php";
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
//			pDialog = new ProgressDialog(CopyOfDetail_beli.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_beli= txtkode_beli.getText().toString();
//			String lkode_jual= txtkode_jual.getText().toString();
//			String ltanggal_beli= txttanggal_beli.getText().toString();
//			String ljam_beli= txtjam_beli.getText().toString();
//			String lharga= txtharga.getText().toString();
//			String lkode_anggota= txtkode_anggota.getText().toString();
//			String ltanggal_konfirmasi= txttanggal_konfirmasi.getText().toString();
//			String ljam_konfirmasi= txtjam_konfirmasi.getText().toString();
//			String lbukti_transfer= txtbukti_transfer.getText().toString();
//			String lnama_bank= txtnama_bank.getText().toString();
//			String lcatatan= txtcatatan.getText().toString();
//			String lstatus_beli= txtstatus_beli.getText().toString();
//			
//		
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("kode_beli0", kode_beli0));
//			params.add(new BasicNameValuePair("kode_beli", kode_beli));
//			params.add(new BasicNameValuePair("kode_jual", lkode_jual));
//			params.add(new BasicNameValuePair("tanggal_beli", ltanggal_beli));
//			params.add(new BasicNameValuePair("jam_beli", ljam_beli));
//			params.add(new BasicNameValuePair("harga", lharga));
//			params.add(new BasicNameValuePair("kode_anggota", lkode_anggota));
//			params.add(new BasicNameValuePair("tanggal_konfirmasi", ltanggal_konfirmasi));
//			params.add(new BasicNameValuePair("jam_konfirmasi", ljam_konfirmasi));
//			params.add(new BasicNameValuePair("bukti_transfer", lbukti_transfer));
//			params.add(new BasicNameValuePair("nama_bank", lnama_bank));
//			params.add(new BasicNameValuePair("catatan", lcatatan));
//			params.add(new BasicNameValuePair("status_beli", lstatus_beli));
//			
//	
//				String url=ip+"beli/beli_update.php";
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
//			pDialog = new ProgressDialog(CopyOfDetail_beli.this);
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
//				params.add(new BasicNameValuePair("kode_beli", kode_beli0));
//
//				String url=ip+"beli/beli_del.php";
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
//	 
//	 void callMarquee(){
//         Calendar cal = Calendar.getInstance();          
//         int jam = cal.get(Calendar.HOUR);
//         int menit= cal.get(Calendar.MINUTE);
//         int detik= cal.get(Calendar.SECOND);
//
//         int tgl= cal.get(Calendar.DATE);
//         int bln= cal.get(Calendar.MONTH);
//         int thn= cal.get(Calendar.YEAR);
//
//         String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
//         String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);
//        
//         TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
//         txtMarquee.setSelected(true);
//         String kata="Selamat Datang @lp2maray.com Aplikasi Android  "+stgl+"/"+sjam+" #";
//         String kalimat=String.format("%1$s",TextUtils.htmlEncode(kata));
//         txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat)); 
//  }
//	 
//	 public boolean onKeyDown(int keyCode, KeyEvent event) {
//			if (keyCode == KeyEvent.KEYCODE_BACK) {
//			finish();
//			return true;
//			}
//			return super.onKeyDown(keyCode, event);
//			}
//
//			 
//}