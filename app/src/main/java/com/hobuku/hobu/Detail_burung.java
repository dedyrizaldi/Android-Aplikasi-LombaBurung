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

public class Detail_burung extends Activity {
	String ip="";
	String kode_burung;
	String kode_burung0="";
	
	EditText txtkode_burung;
	EditText txtjenis_burung;
	EditText txtlatin;
	EditText txtdeskripsi;
	EditText txtgambar;
	EditText txtketerangan;

	
	
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_jenis_burung = "jenis_burung";
	private static final String TAG_latin = "latin";
	private static final String TAG_deskripsi = "deskripsi";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_keterangan= "keterangan";
	
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_burung );
		
		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_burung = (EditText) findViewById(R.id.txtkode_burung);
		txtjenis_burung= (EditText) findViewById(R.id.txtjenis_burung);
		txtlatin= (EditText) findViewById(R.id.txtlatin);
		txtdeskripsi= (EditText) findViewById(R.id.txtdeskripsi);
		txtgambar = (EditText) findViewById(R.id.txtgambar);
		txtketerangan = (EditText) findViewById(R.id.txtketerangan);
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		Intent i = getIntent(); 
		kode_burung0 = i.getStringExtra("pk");
		
		if(kode_burung0.length()>0){
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
				kode_burung= txtkode_burung.getText().toString();
				String ljenis_burung= txtjenis_burung.getText().toString();
				String ldeskripsi= txtdeskripsi.getText().toString();
				String llatin= txtlatin.getText().toString();
				String lgambar= txtgambar.getText().toString();
				String lketerangan= txtketerangan.getText().toString();
				
				
				if(kode_burung.length()<1){lengkapi("kode_burung");}
				else if(ljenis_burung.length()<1){lengkapi("jenis_burung");}
				else if(ldeskripsi.length()<1){lengkapi("deskripsi");}
				else if(llatin.length()<1){lengkapi("latin");}
				else if(lgambar.length()<1){lengkapi("gambar");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}
				
				
				else{
					if(kode_burung0.length()>0){
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
			pDialog = new ProgressDialog(Detail_burung.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_burung", kode_burung0));
						
						String url=ip+"burung/burung_detail.php";
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
										txtkode_burung.setText(kode_burung0);
										txtjenis_burung.setText(myJSON.getString(TAG_jenis_burung));
										txtlatin.setText(myJSON.getString(TAG_latin));
										txtdeskripsi.setText(myJSON.getString(TAG_deskripsi));
										txtgambar.setText(myJSON.getString(TAG_gambar));
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
			pDialog = new ProgressDialog(Detail_burung.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_burung = txtkode_burung.getText().toString();
			String ljenis_burung= txtjenis_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String llatin= txtlatin.getText().toString();
			String lgambar= txtgambar.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_burung0", kode_burung0));
				params.add(new BasicNameValuePair("kode_burung", kode_burung));
				params.add(new BasicNameValuePair("jenis_burung", ljenis_burung));
				params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
				params.add(new BasicNameValuePair("latin", llatin));
				params.add(new BasicNameValuePair("gambar", lgambar));
				params.add(new BasicNameValuePair("keterangan", lketerangan));
				
				
	
				String url=ip+"burung/burung_add.php";
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
			pDialog = new ProgressDialog(Detail_burung.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_burung = txtkode_burung.getText().toString();
			String ljenis_burung= txtjenis_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String llatin= txtlatin.getText().toString();
			String lgambar= txtgambar.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
			
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_burung0", kode_burung0));
			params.add(new BasicNameValuePair("kode_burung", kode_burung));
			params.add(new BasicNameValuePair("jenis_burung", ljenis_burung));
			params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
			params.add(new BasicNameValuePair("latin", llatin));
			params.add(new BasicNameValuePair("gambar", lgambar));
			params.add(new BasicNameValuePair("keterangan", lketerangan));
			
	
				String url=ip+"burung/burung_update.php";
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
			pDialog = new ProgressDialog(Detail_burung.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_burung", kode_burung0));

				String url=ip+"burung/burung_del.php";
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
