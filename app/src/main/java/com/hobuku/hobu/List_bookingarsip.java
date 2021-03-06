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

public class List_bookingarsip extends ListActivity {
String ip="";

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_beli = "kode_beli";
	private static final String TAG_kode_jual = "kode_jual";
	private static final String TAG_tanggal_beli = "tanggal_beli";
	String kode_anggota, nama_anggota;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_listinfo);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(List_bookingarsip.this);
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
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Detail_beliarsip.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);
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
			pDialog = new ProgressDialog(List_bookingarsip.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
			JSONObject json = jParser.makeHttpRequest(ip+"beli/bookingarsip.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String kode_beli= c.getString(TAG_kode_beli);
						String kode_jual = c.getString("jenis_burung")+" Nota Jual:"+c.getString(TAG_kode_jual);
						String tanggal_beli = c.getString(TAG_tanggal_beli)+" - "+c.getString("jam_beli")+" WIB\nPembeli: "+c.getString("nama_anggota");
		
						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_kode_beli, kode_beli);
							map.put(TAG_kode_jual, kode_jual);
							map.put(TAG_tanggal_beli, tanggal_beli);
						
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
					ListAdapter adapter = new SimpleAdapter(List_bookingarsip.this, arrayList,R.layout.desain_list, new String[] { TAG_kode_beli,TAG_kode_jual, TAG_tanggal_beli,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
				}
			});}
	}
	
//	public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "Add New").setIcon(R.drawable.add);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case 1:         
//        	Intent i = new Intent(getApplicationContext(), Detail_beli.class);
//			i.putExtra("pk", "");
//			startActivityForResult(i, 100);
//            return true;
//        }
//        return false;
//    }

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}   
	
}
