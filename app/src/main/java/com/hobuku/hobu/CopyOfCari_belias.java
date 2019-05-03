//package com.example.hobu;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class CopyOfCari_belias extends Activity{
//EditText edCari;
//ListView listview;
//Button btnCari;
//
//String ip="";
//
//private ProgressDialog pDialog;
//JSONParser jParser = new JSONParser();
//JSONArray myJSON = null;
//
//ArrayList<HashMap<String, String>> arrayList;
//private static final String TAG_SUKSES = "sukses";
//private static final String TAG_record = "record";
//
//
//private static final String TAG_kode_beli= "kode_beli";
//private static final String TAG_kode_jual = "kode_jual";
//private static final String TAG_tanggal_beli= "tanggal_beli";
//private static final String TAG_jam_beli= "jam_beli";
//private static final String TAG_harga= "harga";
//private static final String TAG_kode_anggota = "kode_anggota";
//private static final String TAG_tanggal_konfirmasi= "tanggal_konfirmasi";
//private static final String TAG_jam_konfirmasi= "jam_konfirmasi";
//private static final String TAG_bukti_transfer= "bukti_transfer";
//private static final String TAG_nama_bank= "nama_bank";
//private static final String TAG_catatan= "catatan";
//private static final String TAG_status_beli= "status_beli";
//
//
//int jd;
//String[]arr_kode_beli;
//String[]arr_kode_jual;
//String[]arr_tanggal_beli;
//String[]arr_jam_beli;
//String[]arr_harga;
//String[]arr_kode_anggota;
//String[]arr_tanggal_konfirmasi;//..
//String[]arr_jam_konfirmasi;
//String[]arr_bukti_transfer;
//String[]arr_nama_bank;//..
//String[]arr_catatan;
//String[]arr_status_beli;
//
//int[]arr_gbr;
//
//String[]arr_kode_beli2;
//String[]arr_kode_jual2;
//String[]arr_tanggal_beli2;
//String[]arr_jam_beli2;
//String[]arr_harga2;
//String[]arr_kode_anggota2;
//String[]arr_tanggal_konfirmasi2;//..
//String[]arr_jam_konfirmasi2;
//String[]arr_bukti_transfer2;
//String[]arr_nama_bank2;//..
//String[]arr_catatan2;
//String[]arr_status_beli2;
//int[]arr_gbr2;
//
//String[]arr_kode_beli3;
//String[]arr_kode_jual3;
//String[]arr_tanggal_beli3;
//String[]arr_jam_beli3;
//String[]arr_harga3;
//String[]arr_kode_anggota3;
//String[]arr_tanggal_konfirmasi3;//..
//String[]arr_jam_konfirmasi3;
//String[]arr_bukti_transfer3;
//String[]arr_nama_bank3;//..
//String[]arr_catatan3;
//String[]arr_status_beli3;
//int[]arr_gbr3;
//
//
//int textlength = 0;
//ArrayList<String> text_sort = new ArrayList<String>();
//ArrayList<Integer> image_sort = new ArrayList<Integer>();
//
//public void onCreate(Bundle savedInstanceState){
//super.onCreate(savedInstanceState);
//setContentView(R.layout.listviewcolorcari);
//
//ip=jParser.getIP(); 
//
////Intent io = this.getIntent();
////myLati=io.getStringExtra("myLati");
////myLongi=io.getStringExtra("myLongi");
////myPosisi=io.getStringExtra("myPosisi");
//
//
//new loads().execute();
//
//btnCari = (Button) findViewById(R.id.btnCari);
//edCari = (EditText) findViewById(R.id.edCari);
//
//}
//
//
//void lanjut(){
//	listview = (ListView) findViewById(R.id.listCari);
//	listview.setAdapter(new MyCustomAdapter(arr_kode_jual, arr_gbr));
//	listview.setOnItemClickListener(new OnItemClickListener()
//		{
//		   @Override
//		   public void onItemClick(AdapterView<?> a, View v, int p, long id)
//		   { 
//			   
//			   Intent i = new Intent(CopyOfCari_belias.this, Detail_beli.class);
//			    i.putExtra("pk", arr_kode_beli[p]);
//				i.putExtra("kode_jual", arr_kode_jual[p]);
//	 			i.putExtra("tanggal_beli", arr_tanggal_beli[p]);
//	 			i.putExtra("jam_beli", arr_jam_beli[p]);
//				i.putExtra("harga", arr_harga[p]);
//				i.putExtra("kode_anggota", arr_kode_anggota[p]);
//				i.putExtra("tanggal_konfirmasi", arr_tanggal_konfirmasi[p]);//--//
//				i.putExtra("jam_konfirmasi", arr_jam_konfirmasi[p]);
//				i.putExtra("bukti_transfer", arr_bukti_transfer[p]);
//				i.putExtra("nama_bank", arr_nama_bank[p]);
//	 			i.putExtra("catataan", arr_catatan[p]);
//	 			i.putExtra("status_beli", arr_status_beli[p]);
//	 			
//				startActivity(i);
//			    
//Toast.makeText(getBaseContext(), "Anda telah memilih no: "+p+"="+ arr_kode_jual[p], Toast.LENGTH_LONG).show();
//		        		}  
//		        });
//
//	btnCari.setOnClickListener(new OnClickListener()
//	{
//		public void onClick(View v){textlength = edCari.getText().length();
//		text_sort.clear();
//		image_sort.clear();
//		String scari=edCari.getText().toString().toLowerCase();
//
//		int ada=0;
//		for (int i = 0; i < jd; i++)
//		{
//			String snama=arr_kode_jual[i].toLowerCase();
//		if (textlength <= arr_kode_jual[i].length()){
//		if (snama.indexOf(scari)>=0)
//		{	//huruf yg awalannya sama
//			text_sort.add(arr_kode_jual[i]);
//			image_sort.add(arr_gbr[i]);
//			arr_kode_beli2[ada]=arr_kode_beli[i];
//			arr_kode_jual2[ada]=arr_kode_jual[i];
//			arr_tanggal_beli2[ada]=arr_tanggal_beli[i];
//			arr_jam_beli2[ada]=arr_jam_beli[i];
//			arr_kode_jual2[ada]=arr_kode_jual[i];
//			arr_harga2[ada]=arr_harga[i];
//			arr_kode_anggota2[ada]=arr_kode_anggota[i];
//			arr_tanggal_konfirmasi2[ada]=arr_tanggal_konfirmasi[i];//--//
//			arr_jam_konfirmasi2[ada]=arr_jam_konfirmasi[i];
//			arr_nama_bank2[ada]=arr_nama_bank[i];//--//
//			arr_gbr2[ada]=arr_gbr[i];
//		   	arr_bukti_transfer2[ada]=arr_bukti_transfer[i];
//		   	arr_catatan2[ada]=arr_catatan[i];
//		   	arr_status_beli[ada]=arr_status_beli[i];
//		   	
//		  
//				ada=ada+1;
//		}
//	  }
//	}
//
//		arr_kode_beli3=new String[ada];
//		arr_kode_jual3=new String[ada];
//		arr_tanggal_beli3=new String[ada];
//		arr_jam_beli3=new String[ada];
//	arr_harga3=new String[ada];
//	arr_kode_anggota3=new String[ada];
//	arr_tanggal_konfirmasi3=new String[ada];//--//
//	arr_nama_bank3=new String[ada];//--//
//	arr_gbr3=new int[ada];
//	arr_jam_konfirmasi3=new String[ada];
//	arr_bukti_transfer3=new String[ada];
//	arr_catatan3=new String[ada];
//	arr_status_beli3=new String[ada];
//	for (int i = 0; i < ada; i++)
//	{
//		
//		arr_kode_beli3[i]=arr_kode_beli2[i];
//		arr_kode_jual3[i]=arr_kode_jual2[i];
//		arr_tanggal_beli3[i]=arr_tanggal_beli2[i];
//		arr_jam_beli3[i]=arr_jam_beli2[i];
//		arr_harga3[i]=arr_harga2[i];
//		arr_kode_anggota3[i]=arr_kode_anggota2[i];
//		arr_tanggal_konfirmasi3[i]=arr_tanggal_konfirmasi2[i];//--//
//		arr_nama_bank3[i]=arr_nama_bank2[i];//--//
//		arr_gbr3[i]=arr_gbr2[i];
//		arr_jam_konfirmasi3[i]=arr_jam_konfirmasi2[i];
//	   	arr_bukti_transfer3[i]=arr_bukti_transfer2[i];
//	   	arr_catatan3[i]=arr_catatan[i];
//	   	arr_status_beli3[i]=arr_status_beli[i];
//
//	}
//
//		listview.setAdapter(new MyCustomAdapter(text_sort, image_sort));
//		listview.setOnItemClickListener(new OnItemClickListener() {
//			   @Override
//			   public void onItemClick(AdapterView<?> a, View v, int p, long id) { 
//				   
//				   	Intent i = new Intent(CopyOfCari_belias.this, Detail_beli.class);
//				    i.putExtra("pk", arr_kode_beli3[p]);
//					i.putExtra("kode_jual", arr_kode_jual3[p]);
//					i.putExtra("tanggal_beli", arr_tanggal_beli3[p]);
//					i.putExtra("jam_beli", arr_jam_beli3[p]);
//					i.putExtra("harga", arr_harga3[p]);
//					i.putExtra("kode_anggota", arr_kode_anggota3[p]);
//					i.putExtra("tanggal_konfirmasi", arr_tanggal_konfirmasi3[p]);//--//
//					i.putExtra("nama_bank", arr_nama_bank3[p]);
//					i.putExtra("jam_konfirmasi", arr_jam_konfirmasi3[p]);
//					i.putExtra("bukti_transfer", arr_bukti_transfer3[p]);
//					i.putExtra("nama_bank", arr_nama_bank3[p]);
//		 			i.putExtra("catataan", arr_catatan3[p]);
//		 			i.putExtra("status_beli", arr_status_beli3[p]);
//		 			i.putExtra("tanggal_beli", arr_tanggal_beli3[p]);
//					startActivity(i);
//				   
//					
//			        		Toast.makeText(getBaseContext(), "Anda telah memilih no "+p+"="+ arr_kode_jual3[p], Toast.LENGTH_LONG).show();
//			        	}  
//			        });
//		}});
//	
//}
//
//class MyCustomAdapter extends BaseAdapter
//		{
//			String[] data_text;
//			int[] data_image;
//		MyCustomAdapter(){}
//		
//		MyCustomAdapter(String[] text, int[] image){
//			data_text = text;
//			data_image = image;
//		}
//		
//		MyCustomAdapter(ArrayList<String> text, ArrayList<Integer> image){
//			data_text = new String[text.size()];
//			data_image = new int[image.size()];
//				for (int i = 0; i < text.size(); i++) {
//					data_text[i] = text.get(i);
//					data_image[i] = image.get(i);
//				}
//		}
//		
//		public int getCount(){return data_text.length;}
//		public String getItem(int position){return null;}
//		public long getItemId(int position){return position;}
//		public View getView(int p, View convertView, ViewGroup parent){
//			LayoutInflater inflater = getLayoutInflater();
//			View row;
//			row = inflater.inflate(R.layout.listviewcolordetail, parent, false);
//			TextView textview = (TextView) row.findViewById(R.id.txtCari);
//			ImageView imageview = (ImageView) row.findViewById(R.id.imgCari);
//			textview.setText(data_text[p]);
//			imageview.setImageResource(data_image[p]);
//			return (row);
//			}
//		
//	}
//
//
//
//class loads extends AsyncTask<String, String, String> {
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		pDialog = new ProgressDialog(CopyOfCari_belias.this);
//		pDialog.setMessage("Load data. Silahkan Tunggu...");
//		pDialog.setIndeterminate(false);
//		pDialog.setCancelable(false);
//		pDialog.show();
//	}
//	protected String doInBackground(String... args) {
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		JSONObject json = jParser.makeHttpRequest(ip+"beli/beli_show.php", "GET", params);
//		Log.d("show: ", json.toString());
//		try {
//			int sukses = json.getInt(TAG_SUKSES);
//			if (sukses == 1) {
//				myJSON = json.getJSONArray(TAG_record);
//				
//				jd=myJSON.length();
//				arr_kode_beli=new String[jd];
//				arr_kode_jual=new String[jd];
//				arr_harga=new String[jd];
//				arr_tanggal_beli=new String[jd];
//				arr_jam_beli=new String[jd];
//				arr_kode_anggota=new String[jd];
//				arr_tanggal_konfirmasi=new String[jd];
//				arr_nama_bank=new String[jd];
//				arr_jam_konfirmasi=new String[jd];
//				arr_bukti_transfer=new String[jd];
//				arr_nama_bank=new String[jd];
//				arr_catatan=new String[jd];
//				arr_status_beli=new String[jd];
//				arr_gbr=new int[jd];
//				
//		
//				arr_kode_beli2=new String[jd];
//				arr_kode_jual2=new String[jd];
//				arr_harga2=new String[jd];
//				arr_tanggal_beli2=new String[jd];
//				arr_jam_beli2=new String[jd];
//				arr_kode_anggota2=new String[jd];
//				arr_tanggal_konfirmasi2=new String[jd];
//				arr_nama_bank2=new String[jd];
//				arr_jam_konfirmasi2=new String[jd];
//				arr_bukti_transfer2=new String[jd];
//				arr_nama_bank2=new String[jd];
//				arr_catatan2=new String[jd];
//				arr_status_beli2=new String[jd];
//				arr_gbr2=new int[jd];
//				
//				
//				for (int i = 0; i < jd; i++) {
//					JSONObject c = myJSON.getJSONObject(i);
//					String kode_beli= c.getString(TAG_kode_beli);
//					String kode_jual = c.getString(TAG_kode_jual)+" - "+c.getString("jenis_burung");
//					String tanggal_beli= c.getString(TAG_tanggal_beli);
//					String jam_beli= c.getString(TAG_jam_beli);
//					String harga= c.getString(TAG_harga);
//					String kode_anggota= c.getString(TAG_kode_anggota);
//					String tanggal_konfirmasi= c.getString(TAG_tanggal_konfirmasi);
//					String jam_konfirmasi= c.getString(TAG_jam_konfirmasi);
//					String bukti_transfer= c.getString(TAG_bukti_transfer);
//					String nama_bank= c.getString(TAG_nama_bank);
//					String catatan= c.getString(TAG_catatan);
//					String status_beli= c.getString(TAG_status_beli);
//					
//					arr_kode_beli[i]=kode_beli;
//					arr_kode_jual[i]=kode_jual;
//					arr_tanggal_beli[i]=tanggal_beli;
//					arr_jam_beli[i]=jam_beli;
//					arr_harga[i]=harga;
//					arr_kode_anggota[i]=kode_anggota;
//					arr_tanggal_konfirmasi[i]=tanggal_konfirmasi;
//					arr_nama_bank[i]=nama_bank;
//					arr_jam_konfirmasi[i]=jam_konfirmasi;
//					arr_bukti_transfer[i]=bukti_transfer;
//					arr_catatan[i]=catatan;
//					arr_status_beli[i]=status_beli;
//					arr_gbr[i]=R.drawable.ls;
//
//					
//				}
//			} 
//		} 
//		catch (JSONException e) {e.printStackTrace();}
//		return null;
//	}
//
//	protected void onPostExecute(String file_url) {
//		pDialog.dismiss();
//		runOnUiThread(new Runnable() {
//			public void run() {
//				lanjut();
//			}
//		});}
//}
//
//
//
//}
//
//
