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
import android.os.AsyncTask;
import android.os.Bundle;
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

public class List_lomba extends ListActivity {
String ip="";
int sukses=0;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_lomba = "kode_lomba";
	private static final String TAG_nama_lomba = "nama_lomba";
	private static final String TAG_kelas_lomba = "kelas_lomba";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();
			
		new load().execute();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Detail_lomba.class);
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
			pDialog = new ProgressDialog(List_lomba.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("status_lomba", "Dibuka"));
			JSONObject json = jParser.makeHttpRequest(ip+"lomba/lomba_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String kode_lomba= c.getString(TAG_kode_lomba);
						String nama_lomba = c.getString(TAG_nama_lomba)+"/"+c.getString("tanggal_lomba");
						String kelas_lomba ="Kelas : "+ c.getString(TAG_kelas_lomba)+" (Rp. :"+ c.getString("harga_tiket")+")\nSponsor: "+c.getString("sponsor")+" ("+c.getString("jumlah_gantangan")+" Tiang)";
						
						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_kode_lomba, kode_lomba);
							map.put(TAG_nama_lomba, nama_lomba);
							map.put(TAG_kelas_lomba, kelas_lomba);
						
						arrayList.add(map);
					}
				} else {
//					Intent i = new Intent(getApplicationContext(),Detail_lomba.class);
//					i.putExtra("pk", "");
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(i);
				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					
					ListAdapter adapter = new SimpleAdapter(List_lomba.this, arrayList,R.layout.desain_list, new String[] { TAG_kode_lomba,TAG_nama_lomba, TAG_kelas_lomba,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
					if(sukses==0){
						Toast.makeText(List_lomba.this,  "Maaf data belum tersedia....", Toast.LENGTH_LONG).show();
					}
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
//        	Intent i = new Intent(getApplicationContext(), Detail_lomba.class);
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
