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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class BeliTransfer extends Activity {
	String ip="",namabank="BCA",bukti_transfer="";
	String kode_beli;
	String kode_beli0="";
	String KJ="";
	RadioButton radPa;
	RadioButton radPi;
	RadioButton radPu;
	
	EditText txtkode_beli;
	EditText txtkode_jual;
	EditText txttanggal_beli;
	EditText txtharga;
	EditText txtkode_anggota;
	EditText txtcatatan;
	String gambar, gambar2, gambar3,gambar4,gambar5; //copy ini
	
	Button btnProses,btnUpload;
	Button btnHapus;//btnproses
	
	private ViewFlipper viewFlipper;
	private float lastX;				//copy dua baris ini

String GB="";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kode_jual = "kode_jual";
	private static final String TAG_tanggal_beli = "tanggal_beli";
	private static final String TAG_jam_beli = "jam_beli";
	private static final String TAG_harga = "harga";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_gambar2 = "gambar2";
	private static final String TAG_gambar3 = "gambar3";
	private static final String TAG_gambar4 = "gambar4";
	private static final String TAG_gambar5 = "gambar5";
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
		setContentView(R.layout.belitransfer);
		
		ip=jsonParser.getIP();
		callMarquee();
		

		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper); // copy ini
		
		txtkode_beli= (EditText) findViewById(R.id.txtkode_beli);txtkode_beli.setEnabled(false);
		txtkode_jual= (EditText) findViewById(R.id.txtkode_jual);txtkode_jual.setEnabled(false);
		txttanggal_beli= (EditText) findViewById(R.id.txttanggal_beli);txttanggal_beli.setEnabled(false);
		txtharga= (EditText) findViewById(R.id.txtharga);txtharga.setEnabled(false);
		txtkode_anggota= (EditText) findViewById(R.id.txtkode_anggota);txtkode_anggota.setEnabled(false);
		txtcatatan= (EditText) findViewById(R.id.txtcatatan);txtcatatan.setEnabled(true);
	
		

		radPa= (RadioButton) findViewById(R.id.radPa);
		radPi= (RadioButton) findViewById(R.id.radPi);
		radPu= (RadioButton) findViewById(R.id.radPu);
		
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
		btnUpload= (Button) findViewById(R.id.btnupload);
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
 
		
		Intent i = getIntent(); 
		kode_beli0 = i.getStringExtra("pk");
		bukti_transfer= i.getStringExtra("bukti_transfer");
		
		new get().execute();
		
		
		btnProses.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View arg0) {
	        	new update().execute();
	        }});
		
		
		btnUpload.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View arg0) {
	            Intent i = new Intent(BeliTransfer.this,UploadToServer2.class);
	            i.putExtra("pk", kode_beli0);
				i.putExtra("bukti_transfer", bukti_transfer);
	                startActivity(i); 
	                finish();
	        }});
		
		
		
		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new del().execute();
			}});
		
		if(bukti_transfer.length()<3){
			btnProses.setEnabled(false);
		}
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BeliTransfer.this);
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
									KJ=myJSON.getString("kode_jual");
										txtkode_beli.setText(kode_beli0);
										txtkode_jual.setText(myJSON.getString("kode_burung")+"/"+KJ);
										txttanggal_beli.setText(myJSON.getString(TAG_tanggal_beli) +" "+myJSON.getString(TAG_jam_beli)+" wib");
										txtharga.setText(myJSON.getString(TAG_harga));
										gambar=myJSON.getString(TAG_gambar);
										gambar=myJSON.getString(TAG_gambar2);
										gambar=myJSON.getString(TAG_gambar3);
										gambar=myJSON.getString(TAG_gambar4);
										gambar=myJSON.getString(TAG_gambar5);
										txtkode_anggota.setText(myJSON.getString("nama_anggota"));
										txtcatatan.setText(myJSON.getString(TAG_catatan));
										
										 GB=myJSON.getString("gambar");
										 
										 gambar=myJSON.getString("gambar");
										 gambar2=myJSON.getString("gambar2");
										 gambar3=myJSON.getString("gambar3");
										 gambar4=myJSON.getString("gambar4");
										 gambar5=myJSON.getString("gambar5");
										
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
		String arUrlFoto=ip+"ypathfile/"+gambar;
		String arUrlFoto2=ip+"ypathfile/"+gambar2;
		String arUrlFoto3=ip+"ypathfile/"+gambar3;
		String arUrlFoto4=ip+"ypathfile/"+gambar4;
		String arUrlFoto5=ip+"ypathfile/"+gambar5;


		new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar2)).execute(arUrlFoto2);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar3)).execute(arUrlFoto3);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar4)).execute(arUrlFoto4);
		new DownloadImageTask((ImageView) findViewById(R.id.myGambar5)).execute(arUrlFoto5);

//
//			if(GB.length()<1){GB="avatar.jpg";}
//		 arUrlFoto=ip+"ypathfile/"+GB;
//		Log.v("Gambar",arUrlFoto);
//		new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);

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

	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BeliTransfer.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_beli= kode_beli0;			
			String lcatatan= txtcatatan.getText().toString();
			
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_beli", kode_beli));
			params.add(new BasicNameValuePair("kode_jual", KJ));
			params.add(new BasicNameValuePair("nama_bank", namabank));
			params.add(new BasicNameValuePair("catatan", lcatatan));
			params.add(new BasicNameValuePair("bukti_transfer", bukti_transfer));
			
			
				String url=ip+"beli/beli_updatemember.php";
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
			pDialog = new ProgressDialog(BeliTransfer.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_beli", kode_beli0));

				String url=ip+"beli/beli_del.php";
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
