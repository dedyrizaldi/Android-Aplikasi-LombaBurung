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

public class CopyOfDetail_perawatanas extends Activity {
	String ip="";
	String kode_perawatan;
	String kode_perawatan0="";


	EditText txtkode_burung;
	EditText txtnama_perawatan;
	EditText txtdeskripsi;
	EditText txtstatus;
	EditText txttanggal;
	EditText txtketerangan;
	EditText txtkode_perawatan;
	EditText txtgambar;
	String gambar;



	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_perawatan= "kode_burung";
	private static final String TAG_nama_perawatan= "nama_perawatan";
	private static final String TAG_deskripsi = "deskripsi";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_status= "status";
	private static final String TAG_tanggal= "tanggal";
	private static final String TAG_keterangan= "keterangan";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_perawatan);

		ip=jsonParser.getIP();
		callMarquee();

		//txtkode_perawatan= (EditText) findViewById(R.id.txtkode_perawatan);
		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);
		txtdeskripsi= (EditText) findViewById(R.id.txtdeskripsi);
		txtnama_perawatan= (EditText) findViewById(R.id.txtnama_perawatan);
		txtgambar = (EditText) findViewById(R.id.txtgambar);
		txtstatus = (EditText) findViewById(R.id.txtstatus);
		txttanggal= (EditText) findViewById(R.id.txttanggal);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_perawatan0 = i.getStringExtra("pk");

		if(kode_perawatan0.length()>0){
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
				kode_perawatan= txtkode_perawatan.getText().toString();
				String lkode_burung= txtkode_burung.getText().toString();
				String ldeskripsi= txtdeskripsi.getText().toString();
				String lnama_perawatan= txtnama_perawatan.getText().toString();
				String lgambar= txtgambar.getText().toString();
				String lstatus= txtstatus.getText().toString();
				String ltanggal= txttanggal.getText().toString();
				String lketerangan= txtketerangan.getText().toString();


				if(kode_perawatan.length()<1){lengkapi("kode_perawatan");}
				else if(lkode_burung.length()<1){lengkapi("kode_burung");}
				else if(ldeskripsi.length()<1){lengkapi("deskripsi");}
				else if(lnama_perawatan.length()<1){lengkapi("nama_perawatan");}
				else if(lgambar.length()<1){lengkapi("gambar");}
				else if(lstatus.length()<1){lengkapi("status");}
				else if(ltanggal.length()<1){lengkapi("tanggal");}
				else if(lketerangan.length()<1){lengkapi("keterangan ");}

				else{
					if(kode_perawatan0.length()>0){
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
			pDialog = new ProgressDialog(CopyOfDetail_perawatanas.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
					int sukses;
					try {
						List<NameValuePair> params1 = new ArrayList<NameValuePair>();
						params1.add(new BasicNameValuePair("kode_perawatan", kode_perawatan0));

						String url=ip+"perawatan/perawatan_detail.php";
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
										txtkode_perawatan.setText(kode_perawatan0);
										txtkode_burung.setText(myJSON.getString(TAG_perawatan));
										txtnama_perawatan.setText(myJSON.getString(TAG_nama_perawatan));
										txtdeskripsi.setText(myJSON.getString(TAG_deskripsi));
										txtgambar.setText(myJSON.getString(TAG_gambar));
										txtstatus.setText(myJSON.getString(TAG_status));
										txttanggal.setText(myJSON.getString(TAG_tanggal));
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
			pDialog = new ProgressDialog(CopyOfDetail_perawatanas.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_perawatan= txtkode_perawatan.getText().toString();
			String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lnama_perawatan= txtnama_perawatan.getText().toString();
			String lgambar= txtgambar.getText().toString();
			String lstatus= txtstatus.getText().toString();
			String ltanggal= txttanggal.getText().toString();
			String lketerangan= txtketerangan.getText().toString();


				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_perawatan0", kode_perawatan0));
				params.add(new BasicNameValuePair("kode_perawatan", kode_perawatan));
				params.add(new BasicNameValuePair("kode_burung", lkode_burung));
				params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
				params.add(new BasicNameValuePair("nama_perawatan", lnama_perawatan));
				params.add(new BasicNameValuePair("gambar", lgambar));
				params.add(new BasicNameValuePair("satus", lstatus));
				params.add(new BasicNameValuePair("tanggal", ltanggal));
				params.add(new BasicNameValuePair("keterangan", lketerangan ));



				String url=ip+"perawatan/perawatan_add.php";
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
			pDialog = new ProgressDialog(CopyOfDetail_perawatanas.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_perawatan= txtkode_perawatan.getText().toString();
			String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lnama_perawatan= txtnama_perawatan.getText().toString();
			String lgambar= txtgambar.getText().toString();
			String lstatus= txtstatus.getText().toString();
			String ltanggal= txttanggal.getText().toString();
			String lketerangan= txtketerangan.getText().toString();


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_perawatan0", kode_perawatan0));
			params.add(new BasicNameValuePair("kode_perawatan", kode_perawatan));
			params.add(new BasicNameValuePair("kode_burung", lkode_burung));
			params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
			params.add(new BasicNameValuePair("nama_perawatan", lnama_perawatan));
			params.add(new BasicNameValuePair("gambar", lgambar));
			params.add(new BasicNameValuePair("status", lstatus));
			params.add(new BasicNameValuePair("tanggal", ltanggal));
			params.add(new BasicNameValuePair("keterangan", lketerangan ));


				String url=ip+"perawatan/perawatan_update.php";
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
			pDialog = new ProgressDialog(CopyOfDetail_perawatanas.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_perawatan", kode_perawatan0));

				String url=ip+"perawatan/perawatan_del.php";
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
         Calendar cal = Calendar.getInstance();
         int jam = cal.get(Calendar.HOUR);
         int menit= cal.get(Calendar.MINUTE);
         int detik= cal.get(Calendar.SECOND);

         int tgl= cal.get(Calendar.DATE);
         int bln= cal.get(Calendar.MONTH);
         int thn= cal.get(Calendar.YEAR);

         String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
         String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);

         TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
         txtMarquee.setSelected(true);
         String kata="Selamat Datang @lp2maray.com Aplikasi Android  "+stgl+"/"+sjam+" #";
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
