package com.hobuku.hobu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.*;
import java.util.*;
import java.net.*;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;
import android.widget.*;

public class Album extends Activity {
	String ip="";
	int jd=0;
	String[]arGB;
	String[]arNM;

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_jual = "kode_jual";
	private static final String TAG_kode_burung = "kode_burung";
	private static final String TAG_harga = "harga";
	
	
    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	ip=jParser.getIP();
        this.imageGrid = (GridView) findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();

    	new load().execute();
    	

    }

    

	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Album.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			//params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
			//params.add(new BasicNameValuePair("status", "Tersedia"));
			JSONObject json = jParser.makeHttpRequest(ip+"jual/jual_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					jd=myJSON.length();
					arGB=new String[jd];
					arNM=new String[jd];
					
					for (int i = 0; i < jd; i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String kode_jual= c.getString(TAG_kode_jual);
						String kode_burung = c.getString("jenis_burung");
						String harga = "Rp. "+c.getString(TAG_harga) +"/"+c.getString("tanggal_jual")+"\nKet : "+ c.getString("keterangan");
						
						String gb= c.getString("gambar");
						if(gb.length()<3){gb="avatar.jpg";}
						
						arGB[i]=gb;
						arNM[i]= kode_burung;
							
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
			   baca();

				}
			});}
	}
	
	
	void baca(){
		  try {
			  //jd=10
	            for(int i = 0; i < 10; i++) {//ip+"ypathfile/"+arGB[i]
	                this.bitmapList.add(urlImageToBitmap("http://chittagongit.com//images/android-icon/android-icon-5.jpg"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        this.imageGrid.setAdapter(new ImageAdapter(this, this.bitmapList));
	}
	
    private Bitmap urlImageToBitmap(String imageUrl) throws Exception {
        Bitmap result = null;
        URL url = new URL(imageUrl);
        if(url != null) {
            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return result;
    }

}