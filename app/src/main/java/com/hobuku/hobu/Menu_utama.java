package com.hobuku.hobu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

public class Menu_utama extends Activity {
	ImageView btnprofil, btninfo, btnjual, btnbeli, btnarsipburung, btnkeluar, btnperawatan,btnbantuan;
	String kode_anggota, nama_anggota,pk="";

	 String ip="",kategori="",PESAN="";
	    JSONParser jParser = new JSONParser();
	    JSONArray myJSON = null;
	    int adabaru=0;
	    Handler mHandler;
	    private static final String TAG_SUKSES = "sukses";
	    private static final String TAG_record = "record";
	    SharedPrefManager sharedPrefManager;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_utama);
		ip=jParser.getIP();
		sharedPrefManager = new SharedPrefManager(this);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menu_utama.this);
		   Boolean Registered = sharedPref.getBoolean("Registered", false);
		         if (!Registered){
		             finish();
		         }else {
		        	 kode_anggota= sharedPref.getString("kode_anggota", "");
		        	 nama_anggota = sharedPref.getString("nama_anggota", "");
		         }



		btnprofil=(ImageView)findViewById(R.id.btnprofil); 
		btnprofil.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_utama.this,Profil_anggota.class);
          i.putExtra("pk", kode_anggota);
                startActivity(i); 
        }});
		
		
		btnperawatan=(ImageView)findViewById(R.id.btnperawatan); 
		btnperawatan.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_utama.this,List_perawatan.class);
            i.putExtra("pk", kode_anggota);
                startActivity(i); 
        }});
		
		btninfo=(ImageView)findViewById(R.id.btninfo); 
		btninfo.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_utama.this,Menu_info.class);
                startActivity(i); 
        }});
		
		btnjual=(ImageView)findViewById(R.id.btnjual); 
		btnjual.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_utama.this,List_jual.class);
                startActivity(i); 
        }});
		
		btnbeli=(ImageView)findViewById(R.id.btnbeli); 
		btnbeli.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_utama.this,Menu_beli.class);
                startActivity(i); 
        }});
		
		btnbantuan=(ImageView)findViewById(R.id.btnbantuan);
		btnbantuan.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {

//			finish();
			Logout();
//            Intent i = new Intent(Menu_utama.this,Bantuan.class);
//                startActivity(i);
        }});
		
		
		
		
	      this.mHandler = new Handler();
	        m_Runnable.run();


	    }

	    private final Runnable m_Runnable = new Runnable()    {
	        public void run()  {
	        //    Toast.makeText(Menu_utama.this,"cek in runnable",Toast.LENGTH_SHORT).show();
	            // addNotification();
	            new load().execute();
	            Menu_utama.this.mHandler.postDelayed(m_Runnable,5000);
	        }
	    };

	    private void addNotification1() {
	    	
	    	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    	v.vibrate(400);
	    	
	    	
	        Toast.makeText(Menu_utama.this,"Info "+PESAN,Toast.LENGTH_SHORT).show();
	        NotificationCompat.Builder builder =
	                new NotificationCompat.Builder(this)
	                        .setSmallIcon(R.drawable.admin)
	                        .setContentTitle("Info Pembeli ")
	                        .setContentText(PESAN);

	        Intent notificationIntent = new Intent(this, Notif.class);//eventList
	        notificationIntent.putExtra("pk", pk);
	        notificationIntent.putExtra("PESAN", PESAN);

	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	        builder.setContentIntent(contentIntent);
	        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        manager.notify(0, builder.build());

	    }




	 public void keluar(){
	     new AlertDialog.Builder(this)
	  .setTitle("Menutup Aplikasi")
	  .setMessage("Terimakasih... Anda Telah Menggunakan Aplikasi Ini")
	  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
	   public void onClick(DialogInterface dlg, int sumthin) {
	   finish();
	   }}).show();
	    }

	     public void Logout(){
	     AlertDialog.Builder ad=new AlertDialog.Builder(Menu_utama.this);
	             ad.setTitle("Konfirmasi");
	             ad.setMessage("Apakah benar ingin Logout?");

	             ad.setPositiveButton("OK",new OnClickListener(){
	          @Override
	       public void onClick(DialogInterface dialog, int which) {
				  sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
				  startActivity(new Intent(Menu_utama.this, Login.class)
						  .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
				  finish();
	        }});

	             ad.setNegativeButton("No",new OnClickListener(){
	           public void onClick(DialogInterface arg0, int arg1) {
	           }});

	             ad.show();
	    }

	public void keluarYN(){
		AlertDialog.Builder ad=new AlertDialog.Builder(Menu_utama.this);
		ad.setTitle("Konfirmasi");
		ad.setMessage("Apakah benar ingin keluar?");

		ad.setPositiveButton("OK",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				keluar();
			}});

		ad.setNegativeButton("No",new OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1) {
			}});

		ad.show();
	}
	    
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				keluarYN();
			return true;
			}
			return super.onKeyDown(keyCode, event);
			}

	    class load extends AsyncTask<String, String, String> {
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }
	        protected String doInBackground(String... args) {
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            
	            params.add(new BasicNameValuePair("kode_anggota", kode_anggota));
	            JSONObject json = jParser.makeHttpRequest(ip+"cek.php", "GET", params);
	            Log.d("show: ", json.toString());
	            try {
	                adabaru= json.getInt("sukses");
	                if(adabaru==1){
	                	PESAN= json.getString("PESAN");
	                	pk= json.getString("kode_beli");
	                	
//	                    JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
//	                    final JSONObject myJSON = myObj.getJSONObject(0);
//	                    runOnUiThread(new Runnable() {
//	                        public void run() {
//	                            try {
//	                                PESAN=myJSON.getString("PESAN");
//	                                pk=myJSON.getString("kode_beli");
//	                            }
//	                            catch (JSONException e) {e.printStackTrace();}
//	                        }});

	                }

	            }
	            catch (JSONException e) {e.printStackTrace();}
	            return null;
	        }

	        protected void onPostExecute(String file_url) {
	            runOnUiThread(new Runnable() {
	                public void run() {
	                    if (adabaru==1){
	                        adabaru=0;

	                            addNotification1();

	                            MediaPlayer mp = MediaPlayer.create(Menu_utama.this, R.raw.chord);
	                            if(!mp.isPlaying()) {
	                                mp.start();
	                                //mp.setLooping(true);
	                            }
	                       

	                        Toast.makeText(Menu_utama.this,PESAN,Toast.LENGTH_SHORT).show();
	                    }

	                }
	            });}
	    }


	}
