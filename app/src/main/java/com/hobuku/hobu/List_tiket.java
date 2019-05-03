package com.hobuku.hobu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class List_tiket extends ListActivity {
	String[]arpos;
	int jd=0;
	
String ip="",status="";
int sudah=0;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_tiket = "kode_tiket";
	private static final String TAG_kode_lomba = "kode_lomba";
	private static final String TAG_jenis_burung = "jenis_burung";
	String kode_anggota, nama_anggota;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();
			
		Intent i = getIntent(); 
		status= i.getStringExtra("status");
		
		
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(List_tiket.this);
		 Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered){
            finish();
        }else {
       	 kode_anggota= sharedPref.getString("kode_anggota", "");
       	 nama_anggota = sharedPref.getString("nama_anggota", "");
        }
		
        new load().execute();
        
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String st=arpos[position];
				if(status.equalsIgnoreCase("Selesai")){	
					String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
					Intent i = new Intent(getApplicationContext(), Detail_tiketselesai.class);//Detail_tiket
					i.putExtra("pk", pk);
					startActivityForResult(i, 100);
				}
				else if(st.equalsIgnoreCase("Booking") ){	
					String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
					Intent i = new Intent(getApplicationContext(), Detail_tiketbooking.class);//Detail_tiket
					i.putExtra("pk", pk);
					i.putExtra("bukti_bayar", "");
					startActivityForResult(i, 100);
				}
				else if(st.equalsIgnoreCase("Batal") ){	
					String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
					Intent i = new Intent(getApplicationContext(), Detail_tiketbatal.class);//Detail_tiket
					i.putExtra("pk", pk);
					startActivityForResult(i, 100);
				}
				else {	
					//Konfirmasi
					String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
					Intent i = new Intent(getApplicationContext(), Detail_tiketkonfirmasi.class);//Detail_tiket
					i.putExtra("pk", pk);
					startActivityForResult(i, 100);
				}

			}});
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {// jika result code 100
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	}

	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(List_tiket.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
			params.add(new BasicNameValuePair("status", status));
			JSONObject json = jParser.makeHttpRequest(ip+"tiket/tiket_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				sudah=sukses;
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					jd=myJSON.length();
					arpos=new String[jd];
					for (int i = 0; i < jd; i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String kode_tiket= c.getString(TAG_kode_tiket);
						String kode_lomba = c.getString("nama_lomba")+" /Kelas: "+ c.getString("kelas_lomba")+"/"+c.getString("kode_lomba");
						String jenis_burung = "Jenis:"+c.getString(TAG_jenis_burung) +" - "+c.getString("latin")+"\n"+c.getString("gab");
						arpos[i]=c.getString("status");
						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_kode_tiket, kode_tiket);
							map.put(TAG_kode_lomba, kode_lomba);
							map.put(TAG_jenis_burung, jenis_burung);
						
						arrayList.add(map);
					}
				} 
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					
					if(sudah==0){
						Toast.makeText(List_tiket.this,  "Maaf Data belum tersedia", Toast.LENGTH_LONG).show();
					}
					ListAdapter adapter = new SimpleAdapter(List_tiket.this, arrayList,R.layout.desain_list, new String[] { TAG_kode_tiket,TAG_kode_lomba, TAG_jenis_burung,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
				}
			});}
	}

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}   
	
}
