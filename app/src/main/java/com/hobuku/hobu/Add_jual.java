package com.hobuku.hobu;


import java.util.ArrayList;
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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add_jual extends Activity {
	TextView txt1;
	String ip="",gbupload="",gbupload2="",gbupload3="",gbupload4="",gbupload5="";
	String kode_jual;
	String kode_jual0="";
	String kode_burung,deskripsi,harga,keterangan;
	EditText txtkode_burung;
	EditText txtdeskripsi;
	EditText txtharga;
	EditText txtketerangan;
	
	String gambar;
	
	Button btnProses,btnupload2,btnupload3,btnupload4,btnupload5,btnupload;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	
	String kode_anggota, nama_anggota;
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_jual);
		
		
		Intent i = getIntent(); 
		gbupload= i.getStringExtra("gbupload");
		gbupload2= i.getStringExtra("gbupload2");
		gbupload3= i.getStringExtra("gbupload3");
		gbupload4= i.getStringExtra("gbupload4");
		gbupload5= i.getStringExtra("gbupload5");
		
		kode_burung= i.getStringExtra("kode_burung");
		deskripsi= i.getStringExtra("deskripsi");
		harga= i.getStringExtra("harga");
		keterangan= i.getStringExtra("keterangan");
		
           
           
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Add_jual.this);
		   Boolean Registered = sharedPref.getBoolean("Registered", false);
		         if (!Registered){
		             finish();
		         }else {
		        	 kode_anggota= sharedPref.getString("kode_anggota", "");
		        	 nama_anggota = sharedPref.getString("nama_anggota", "");
		}

		ip=jsonParser.getIP();
		callMarquee();
		
		txtkode_burung= (EditText) findViewById(R.id.txtkode_burung);txtkode_burung.setText(kode_burung);
		txtdeskripsi= (EditText) findViewById(R.id.txtdeskripsi);txtdeskripsi.setText(deskripsi);
		txtharga= (EditText) findViewById(R.id.txtharga);txtharga.setText(harga);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);txtketerangan.setText(keterangan);
		
		txt1= (TextView) findViewById(R.id.txt);
		
		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);
		
		btnupload=(Button)findViewById(R.id.btnuploadjual);
		btnupload.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        	String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lharga= txtharga.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
            Intent i = new Intent(Add_jual.this,UploadToServer.class);
            i.putExtra("gbupload", gbupload);
            i.putExtra("gbupload2", gbupload2);
            i.putExtra("gbupload3", gbupload3);
            i.putExtra("gbupload4", gbupload4);
			i.putExtra("gbupload5", gbupload5);
            i.putExtra("gb", "1");
            
            i.putExtra("kode_burung", lkode_burung);
            i.putExtra("deskripsi", ldeskripsi);
            i.putExtra("harga", lharga);
            i.putExtra("keterangan", lketerangan);
            
            startActivity(i);
            finish();
        }});
		
		
		
		btnupload2=(Button)findViewById(R.id.btnupload2jual);
		btnupload2.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        	String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lharga= txtharga.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
            Intent i = new Intent(Add_jual.this,UploadToServer.class);
            i.putExtra("gbupload", gbupload);
            i.putExtra("gbupload2", gbupload2);
            i.putExtra("gbupload3", gbupload3);
			i.putExtra("gbupload4", gbupload4);
			i.putExtra("gbupload5", gbupload5);
			i.putExtra("gb", "2");
            i.putExtra("kode_burung", lkode_burung);
            i.putExtra("deskripsi", ldeskripsi);
            i.putExtra("harga", lharga);
            i.putExtra("keterangan", lketerangan);
            
            startActivity(i);
			finish();
        }});
		
		
		btnupload3=(Button)findViewById(R.id.btnupload3jual);
		btnupload3.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        	String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lharga= txtharga.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
            Intent i = new Intent(Add_jual.this,UploadToServer.class);
            i.putExtra("gbupload", gbupload);
            i.putExtra("gbupload2", gbupload2);
            i.putExtra("gbupload3", gbupload3);
			i.putExtra("gbupload4", gbupload4);
			i.putExtra("gbupload5", gbupload5);
			i.putExtra("gb", "3");
            i.putExtra("kode_burung", lkode_burung);
            i.putExtra("deskripsi", ldeskripsi);
            i.putExtra("harga", lharga);
            i.putExtra("keterangan", lketerangan);
            startActivity(i);
			finish();
        }});

		btnupload4=(Button)findViewById(R.id.btnupload4jual);
		btnupload4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String lkode_burung= txtkode_burung.getText().toString();
				String ldeskripsi= txtdeskripsi.getText().toString();
				String lharga= txtharga.getText().toString();
				String lketerangan= txtketerangan.getText().toString();

				Intent i = new Intent(Add_jual.this,UploadToServer.class);
				i.putExtra("gbupload", gbupload);
				i.putExtra("gbupload2", gbupload2);
				i.putExtra("gbupload3", gbupload3);
				i.putExtra("gbupload4", gbupload4);
				i.putExtra("gbupload5", gbupload5);
				i.putExtra("gb", "4");

				i.putExtra("kode_burung", lkode_burung);
				i.putExtra("deskripsi", ldeskripsi);
				i.putExtra("harga", lharga);
				i.putExtra("keterangan", lketerangan);
				startActivity(i);
				finish();
			}});

		btnupload5=(Button)findViewById(R.id.btnupload5jual);
		btnupload5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String lkode_burung= txtkode_burung.getText().toString();
				String ldeskripsi= txtdeskripsi.getText().toString();
				String lharga= txtharga.getText().toString();
				String lketerangan= txtketerangan.getText().toString();

				Intent i = new Intent(Add_jual.this,UploadToServer.class);
				i.putExtra("gbupload", gbupload);
				i.putExtra("gbupload2", gbupload2);
				i.putExtra("gbupload3", gbupload3);
				i.putExtra("gbupload4", gbupload4);
				i.putExtra("gbupload5", gbupload5);
				i.putExtra("gb", "5");

				i.putExtra("kode_burung", lkode_burung);
				i.putExtra("deskripsi", ldeskripsi);
				i.putExtra("harga", lharga);
				i.putExtra("keterangan", lketerangan);
				startActivity(i);
				finish();
			}});
 
		
if(gbupload.length()<1){
	btnProses.setEnabled(false);
	btnProses.setText("Simpan");
}
		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lkode_burung= txtkode_burung.getText().toString();
				String ldeskripsi= txtdeskripsi.getText().toString();
				String lharga= txtharga.getText().toString();
				String lketerangan= txtketerangan.getText().toString();
				
				
				if(lkode_burung.length()<1){lengkapi("kode_burung");}
				else if(ldeskripsi.length()<1){lengkapi("deskripsi");}
				else if(lharga.length()<1){lengkapi("harga");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}
				
				else{
						new save().execute();
					}

			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
		
		String gabg=gbupload+" "+gbupload2+" "+gbupload3+" "+gbupload4+" "+gbupload5+" ";
		txt1.setText(gabg);
	}
	
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Add_jual.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lkode_burung= txtkode_burung.getText().toString();
			String ldeskripsi= txtdeskripsi.getText().toString();
			String lharga= txtharga.getText().toString();
			String lketerangan= txtketerangan.getText().toString();
			
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_burung", lkode_burung));
				params.add(new BasicNameValuePair("gbupload", gbupload));
				params.add(new BasicNameValuePair("gbupload2", gbupload2));
				params.add(new BasicNameValuePair("gbupload3", gbupload3));
				params.add(new BasicNameValuePair("gbupload4", gbupload4));
				params.add(new BasicNameValuePair("gbupload5", gbupload5));
				params.add(new BasicNameValuePair("deskripsi", ldeskripsi));
				params.add(new BasicNameValuePair("harga", lharga));
				params.add(new BasicNameValuePair("keterangan", lketerangan));
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota ));
			
				

				String url=ip+"jual/jual_add.php";
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
						// gagal update dat
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();
		 Intent i = new Intent(Add_jual.this,List_jual.class);
         startActivity(i);
		finish();
		
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
