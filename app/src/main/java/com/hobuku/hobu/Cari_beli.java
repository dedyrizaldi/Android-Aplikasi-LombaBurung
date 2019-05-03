package com.hobuku.hobu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Cari_beli extends Activity{
EditText edCari;
ListView listview;
Button btnCari;

String ip="";

private ProgressDialog pDialog;
JSONParser jParser = new JSONParser();
JSONArray myJSON = null;

ArrayList<HashMap<String, String>> arrayList;
private static final String TAG_SUKSES = "sukses";
private static final String TAG_record = "record";


private static final String TAG_kode_jual= "kode_jual";
private static final String TAG_kode_burung = "kode_burung";
private static final String TAG_tanggal_jual= "tanggal_jual";
private static final String TAG_jam_jual= "jam_jual";
private static final String TAG_harga= "harga";
private static final String TAG_kode_anggota = "kode_anggota";
private static final String TAG_keterangan= "keterangan";
private static final String TAG_deskripsi= "deskripsi";
private static final String TAG_status_jual= "status_jual";


int jd;
String[]arr_kode_jual;
String[]arr_kode_burung;
String[]arr_tanggal_jual;
String[]arr_jam_jual;
String[]arr_harga;
String[]arr_kode_anggota;
String[]arr_keterangan;//..
String[]arr_deskripsi;
String[]arr_status_jual;

int[]arr_gbr;

String[]arr_kode_jual2;
String[]arr_kode_burung2;
String[]arr_tanggal_jual2;
String[]arr_jam_jual2;
String[]arr_harga2;
String[]arr_kode_anggota2;
String[]arr_keterangan2;//..
String[]arr_deskripsi2;
String[]arr_status_jual2;
int[]arr_gbr2;

String[]arr_kode_jual3;
String[]arr_kode_burung3;
String[]arr_tanggal_jual3;
String[]arr_jam_jual3;
String[]arr_harga3;
String[]arr_kode_anggota3;
String[]arr_keterangan3;//..
String[]arr_deskripsi3;
String[]arr_status_jual3;
int[]arr_gbr3;

String kode_anggota="";
int textlength = 0;
ArrayList<String> text_sort = new ArrayList<String>();
ArrayList<Integer> image_sort = new ArrayList<Integer>();

public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.listviewcolorcari);

ip=jParser.getIP(); 

Intent io = this.getIntent();
kode_anggota=io.getStringExtra("kode_anggota");
//myLongi=io.getStringExtra("myLongi");
//myPosisi=io.getStringExtra("myPosisi");


new loads().execute();

btnCari = (Button) findViewById(R.id.btnCari);
edCari = (EditText) findViewById(R.id.edCari);

}


void lanjut(){
	listview = (ListView) findViewById(R.id.listCari);
	listview.setAdapter(new MyCustomAdapter(arr_kode_burung, arr_gbr));
	listview.setOnItemClickListener(new OnItemClickListener()
		{
		   @Override
		   public void onItemClick(AdapterView<?> a, View v, int p, long id)
		   { 
			   
			   Intent i = new Intent(Cari_beli.this, Detail_caribeli.class);
			    i.putExtra("pk", arr_kode_jual[p]);
				i.putExtra("kode_burung", arr_kode_burung[p]);
	 			i.putExtra("tanggal_jual", arr_tanggal_jual[p]);
	 			i.putExtra("jam_jual", arr_jam_jual[p]);
				i.putExtra("harga", arr_harga[p]);
				i.putExtra("kode_anggota", arr_kode_anggota[p]);
				i.putExtra("keterangan", arr_keterangan[p]);//--//
				i.putExtra("deskripsi", arr_deskripsi[p]);
	 			i.putExtra("status_jual", arr_status_jual[p]);
	 			
				startActivity(i);
			    
Toast.makeText(getBaseContext(), "Anda telah memilih no: "+p+"="+ arr_kode_burung[p], Toast.LENGTH_LONG).show();
		        		}  
		        });

	btnCari.setOnClickListener(new OnClickListener()
	{
		public void onClick(View v){textlength = edCari.getText().length();
		text_sort.clear();
		image_sort.clear();
		String scari=edCari.getText().toString().toLowerCase();

		int ada=0;
		for (int i = 0; i < jd; i++)
		{
			String snama=arr_kode_burung[i].toLowerCase();
		if (textlength <= arr_kode_burung[i].length()){
		if (snama.indexOf(scari)>=0)
		{	//huruf yg awalannya sama
			text_sort.add(arr_kode_burung[i]);
			image_sort.add(arr_gbr[i]);
			arr_kode_jual2[ada]=arr_kode_jual[i];
			arr_kode_burung2[ada]=arr_kode_burung[i];
			arr_tanggal_jual2[ada]=arr_tanggal_jual[i];
			arr_jam_jual2[ada]=arr_jam_jual[i];
			arr_kode_burung2[ada]=arr_kode_burung[i];
			arr_harga2[ada]=arr_harga[i];
			arr_kode_anggota2[ada]=arr_kode_anggota[i];
			arr_keterangan2[ada]=arr_keterangan[i];//--//
			arr_deskripsi2[ada]=arr_deskripsi[i];
		   	arr_status_jual[ada]=arr_status_jual[i];
			arr_gbr2[ada]=arr_gbr[i];
		   	
		  
				ada=ada+1;
		}
	  }
	}

		arr_kode_jual3=new String[ada];
		arr_kode_burung3=new String[ada];
		arr_tanggal_jual3=new String[ada];
		arr_jam_jual3=new String[ada];
	arr_harga3=new String[ada];
	arr_kode_anggota3=new String[ada];
	arr_keterangan3=new String[ada];//--//
	arr_gbr3=new int[ada];
	arr_deskripsi3=new String[ada];
	arr_status_jual3=new String[ada];
	for (int i = 0; i < ada; i++)
	{
		
		arr_kode_jual3[i]=arr_kode_jual2[i];
		arr_kode_burung3[i]=arr_kode_burung2[i];
		arr_tanggal_jual3[i]=arr_tanggal_jual2[i];
		arr_jam_jual3[i]=arr_jam_jual2[i];
		arr_harga3[i]=arr_harga2[i];
		arr_kode_anggota3[i]=arr_kode_anggota2[i];
		arr_keterangan3[i]=arr_keterangan2[i];//--//
		arr_gbr3[i]=arr_gbr2[i];
		arr_deskripsi3[i]=arr_deskripsi2[i];
	   	arr_status_jual3[i]=arr_status_jual[i];

	}

		listview.setAdapter(new MyCustomAdapter(text_sort, image_sort));
		listview.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> a, View v, int p, long id) { 
				   
				   	Intent i = new Intent(Cari_beli.this, Detail_caribeli.class);
				    i.putExtra("pk", arr_kode_jual3[p]);
					i.putExtra("kode_burung", arr_kode_burung3[p]);
					i.putExtra("tanggal_jual", arr_tanggal_jual3[p]);
					i.putExtra("jam_jual", arr_jam_jual3[p]);
					i.putExtra("harga", arr_harga3[p]);
					i.putExtra("kode_anggota", arr_kode_anggota3[p]);
					i.putExtra("keterangan", arr_keterangan3[p]);//--//
					i.putExtra("deskripsi", arr_deskripsi3[p]);
		 			i.putExtra("status_jual", arr_status_jual3[p]);
		 			i.putExtra("tanggal_jual", arr_tanggal_jual3[p]);
					startActivity(i);
				   
					
			        		Toast.makeText(getBaseContext(), "Anda telah memilih no "+p+"="+ arr_kode_burung3[p], Toast.LENGTH_LONG).show();
			        	}  
			        });
		}});
	
}

class MyCustomAdapter extends BaseAdapter
		{
			String[] data_text;
			int[] data_image;
		MyCustomAdapter(){}
		
		MyCustomAdapter(String[] text, int[] image){
			data_text = text;
			data_image = image;
		}
		
		MyCustomAdapter(ArrayList<String> text, ArrayList<Integer> image){
			data_text = new String[text.size()];
			data_image = new int[image.size()];
				for (int i = 0; i < text.size(); i++) {
					data_text[i] = text.get(i);
					data_image[i] = image.get(i);
				}
		}
		
		public int getCount(){return data_text.length;}
		public String getItem(int position){return null;}
		public long getItemId(int position){return position;}
		public View getView(int p, View convertView, ViewGroup parent){
			LayoutInflater inflater = getLayoutInflater();
			View row;
			row = inflater.inflate(R.layout.listviewcolordetail, parent, false);
			TextView textview = (TextView) row.findViewById(R.id.txtCari);
			ImageView imageview = (ImageView) row.findViewById(R.id.imgCari);
			textview.setText(data_text[p]);
			imageview.setImageResource(data_image[p]);
			return (row);
			}
		
	}



class loads extends AsyncTask<String, String, String> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Cari_beli.this);
		pDialog.setMessage("Load data. Silahkan Tunggu...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
		JSONObject json = jParser.makeHttpRequest(ip+"jual/jual_showcarinot.php", "GET", params);
		Log.d("show: ", json.toString());
		try {
			int sukses = json.getInt(TAG_SUKSES);
			if (sukses == 1) {
				myJSON = json.getJSONArray(TAG_record);
				
				jd=myJSON.length();
				arr_kode_jual=new String[jd];
				arr_kode_burung=new String[jd];
				arr_harga=new String[jd];
				arr_tanggal_jual=new String[jd];
				arr_jam_jual=new String[jd];
				arr_kode_anggota=new String[jd];
				arr_keterangan=new String[jd];
				arr_deskripsi=new String[jd];
				arr_status_jual=new String[jd];
				arr_gbr=new int[jd];
				
		
				arr_kode_jual2=new String[jd];
				arr_kode_burung2=new String[jd];
				arr_harga2=new String[jd];
				arr_tanggal_jual2=new String[jd];
				arr_jam_jual2=new String[jd];
				arr_kode_anggota2=new String[jd];
				arr_keterangan2=new String[jd];
				arr_deskripsi2=new String[jd];
				arr_status_jual2=new String[jd];
				arr_gbr2=new int[jd];
				
				
				for (int i = 0; i < jd; i++) {
					JSONObject c = myJSON.getJSONObject(i);
					String kode_jual= c.getString(TAG_kode_jual);
					String kode_burung = c.getString("jenis_burung")+" - "+c.getString(TAG_harga);
					String tanggal_jual= c.getString(TAG_tanggal_jual);
					String jam_jual= c.getString(TAG_jam_jual);
					String harga= c.getString(TAG_harga);
					String kode_anggota= c.getString(TAG_kode_anggota);
					String keterangan= c.getString(TAG_keterangan);
					String deskripsi= c.getString(TAG_deskripsi);
					String status_jual= c.getString(TAG_status_jual);
					
					arr_kode_jual[i]=kode_jual;
					arr_kode_burung[i]=kode_burung;
					arr_tanggal_jual[i]=tanggal_jual;
					arr_jam_jual[i]=jam_jual;
					arr_harga[i]=harga;
					arr_kode_anggota[i]=kode_anggota;
					arr_keterangan[i]=keterangan;
					arr_deskripsi[i]=deskripsi;
					arr_status_jual[i]=status_jual;
					arr_gbr[i]=R.drawable.ls;

					
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
				lanjut();
			}
		});}
}



}


