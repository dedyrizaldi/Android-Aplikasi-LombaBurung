package com.hobuku.hobu;


import java.util.ArrayList;
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
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Gantangan extends Activity {
	String posisi="0";
	String ip="",kode_lomba0="",nama_lomba,tanggal,kelas;
	int jd=0;
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29,btn30,btn31,btn32,btn33,btn34,btn35,btn36,btn37,btn38,btn39,btn40,btn41,btn42,btn43,btn44,btn45,btn46,btn47,btn48,btn49,btn50,btn51,btn52,btn53,btn54,btn55,btn56,btn57,btn58,btn59,btn60;
TextView textView1;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;

	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_tiket = "kode_tiket";
	private static final String TAG_kode_lomba = "kode_lomba";
	private static final String TAG_jenis_burung = "jenis_burung";
	
	int sukses;

int[]arNo;
	String kode_anggota, nama_anggota;
	String gambar;
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gantangan);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Gantangan.this);
		   Boolean Registered = sharedPref.getBoolean("Registered", false);
		         if (!Registered){
		             finish();
		         }else {
		        	 kode_anggota= sharedPref.getString("kode_anggota", "");
		        	 nama_anggota = sharedPref.getString("nama_anggota", "");
		         }
		ip=jParser.getIP();
		
		//callMarquee();
			Intent i = getIntent(); 
			kode_lomba0 = i.getStringExtra("kode_lomba");
			nama_lomba = i.getStringExtra("nama_lomba");
			kelas = i.getStringExtra("kelas");
			tanggal = i.getStringExtra("tanggal");
			
			textView1	= (TextView) findViewById(R.id.textView1);
			textView1.setText(nama_lomba+" Kelas:"+kelas+"\n"+tanggal);
			
			
			
			
	setawal();

		new load().execute();
	}
		

	
	 public void order(String pos){
		 posisi=pos;
	     AlertDialog.Builder ad=new AlertDialog.Builder(Gantangan.this);
	             ad.setTitle("Konfirmasi");
	             ad.setMessage("Apakah benar ingin Lomba "+nama_lomba+" diposisi "+pos+" ?");
	             
	             ad.setPositiveButton("YA",new OnClickListener(){
	          @Override
	       public void onClick(DialogInterface dialog, int which) {
	        	  new save().execute();
	        }});
	             
	             ad.setNegativeButton("TIDAK",new OnClickListener(){
	           public void onClick(DialogInterface arg0, int arg1) {
	        	   
	        	   finish();
	           }});
	             
	             ad.show();
	    }
	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Gantangan.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_lomba", kode_lomba0));
			JSONObject json = jParser.makeHttpRequest(ip+"tiket/tiket_showno.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					jd=myJSON.length();
					arNo=new int[jd];
					for (int i = 0; i < jd; i++) {
						JSONObject c = myJSON.getJSONObject(i);
//						String kode_tiket= c.getString(TAG_kode_tiket);
//						String kode_lomba = c.getString("nama_lomba");
						String no= c.getString("nomor_gantangan");
						Log.v("Baca","ADA#"+no);
						arNo[i]=Integer.parseInt(no);
					}
				} else {

				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
				
					for(int i=0;i<jd;i++){
						Log.v("Baca","#"+arNo[i]);
						setBtn(arNo[i]);
					}
				}
			});}
	}

	void setBtn(int no){
//		btn.setBackgroundColor(Color.WHITE);
//		btn.setTextColor(Color.BLACK);
		if(no==1){
			btn1.setBackgroundColor(Color.RED);	
			btn1.setEnabled(false);
		}
		else if(no==2){
			btn2.setBackgroundColor(Color.RED);	
			btn2.setEnabled(false);
		}
		else if(no==3){
			btn3.setBackgroundColor(Color.RED);	
			btn3.setEnabled(false);
		}
		else if(no==4){
			btn4.setBackgroundColor(Color.RED);	
			btn4.setEnabled(false);
		}
		
		else if(no==5){
			btn5.setBackgroundColor(Color.RED);	
			btn5.setEnabled(false);
		}
		else if(no==6){
			btn6.setBackgroundColor(Color.RED);	
			btn6.setEnabled(false);
		}
		else if(no==7){
			btn7.setBackgroundColor(Color.RED);	
			btn7.setEnabled(false);
		}
		else if(no==8){
		btn8.setBackgroundColor(Color.RED);	
			btn8.setEnabled(false);
		}
		else if(no==9){
			btn9.setBackgroundColor(Color.RED);	
			btn9.setEnabled(false);
		}

		else if(no==10){
			btn10.setBackgroundColor(Color.RED);	
			btn10.setEnabled(false);
		}
	
		else if(no==11){
		btn11.setBackgroundColor(Color.RED);	
		btn11.setEnabled(false);
		}

		else if(no==12){
			btn12.setBackgroundColor(Color.RED);	
			btn12.setEnabled(false);
		}
		else if(no==13){
			btn13.setBackgroundColor(Color.RED);	
			btn13.setEnabled(false);
		}
		else if(no==14){
			btn14.setBackgroundColor(Color.RED);	
			btn14.setEnabled(false);
		}
		
		else if(no==15){
			btn15.setBackgroundColor(Color.RED);	
			btn15.setEnabled(false);
		}
		else if(no==16){
			btn16.setBackgroundColor(Color.RED);	
			btn16.setEnabled(false);
		}
		else if(no==17){
			btn17.setBackgroundColor(Color.RED);	
			btn17.setEnabled(false);
		}
		else if(no==18){
			btn18.setBackgroundColor(Color.RED);	
			btn18.setEnabled(false);
		}
		else if(no==19){
			btn19.setBackgroundColor(Color.RED);	
			btn19.setEnabled(false);
		}

		else if(no==20){
			btn20.setBackgroundColor(Color.RED);	
			btn20.setEnabled(false);
		}
		else if(no==21){
			btn21.setBackgroundColor(Color.RED);	
			btn21.setEnabled(false);
		}
		
		else if(no==22){
			btn22.setBackgroundColor(Color.RED);	
			btn22.setEnabled(false);
		}
		else if(no==23){
			btn23.setBackgroundColor(Color.RED);	
			btn23.setEnabled(false);
		}
		else if(no==24){
			btn24.setBackgroundColor(Color.RED);	
			btn24.setEnabled(false);
		}
		
		else if(no==25){
			btn25.setBackgroundColor(Color.RED);	
			btn25.setEnabled(false);
		}
		else if(no==26){
			btn26.setBackgroundColor(Color.RED);	
			btn26.setEnabled(false);
		}
		else if(no==27){
			btn27.setBackgroundColor(Color.RED);	
			btn27.setEnabled(false);
		}
		else if(no==28){
			btn28.setBackgroundColor(Color.RED);	
			btn28.setEnabled(false);
		}
		else if(no==29){
			btn29.setBackgroundColor(Color.RED);	
			btn29.setEnabled(false);
		}

		else if(no==30){
			btn30.setBackgroundColor(Color.RED);	
			btn30.setEnabled(false);
		}
		
		else if(no==31){
			btn31.setBackgroundColor(Color.RED);	
			btn31.setEnabled(false);
		}
		else if(no==32){
			btn32.setBackgroundColor(Color.RED);	
			btn32.setEnabled(false);
		}
		else if(no==33){
			btn33.setBackgroundColor(Color.RED);	
			btn33.setEnabled(false);
		}
		else if(no==34){
			btn34.setBackgroundColor(Color.RED);	
			btn34.setEnabled(false);
		}
		
		else if(no==35){
			btn35.setBackgroundColor(Color.RED);	
			btn35.setEnabled(false);
		}
		else if(no==36){
			btn36.setBackgroundColor(Color.RED);	
			btn36.setEnabled(false);
		}
		else if(no==37){
			btn37.setBackgroundColor(Color.RED);	
			btn37.setEnabled(false);
		}
		else if(no==38){
			btn38.setBackgroundColor(Color.RED);	
			btn38.setEnabled(false);
		}
		else if(no==39){
			btn39.setBackgroundColor(Color.RED);	
			btn39.setEnabled(false);
		}

		else if(no==40){
			btn40.setBackgroundColor(Color.RED);	
			btn40.setEnabled(false);
		}
		
		else if(no==41){
			btn41.setBackgroundColor(Color.RED);	
			btn41.setEnabled(false);
		}
		else if(no==42){
			btn42.setBackgroundColor(Color.RED);	
			btn42.setEnabled(false);
		}
		else if(no==43){
			btn43.setBackgroundColor(Color.RED);	
			btn43.setEnabled(false);
		}
		else if(no==43){
			btn44.setBackgroundColor(Color.RED);	
			btn44.setEnabled(false);
		}
		
		else if(no==45){
			btn45.setBackgroundColor(Color.RED);	
			btn45.setEnabled(false);
		}
		else if(no==46){
			btn46.setBackgroundColor(Color.RED);	
			btn46.setEnabled(false);
		}
		else if(no==47){
			btn47.setBackgroundColor(Color.RED);	
			btn47.setEnabled(false);
		}
		else if(no==48){
			btn48.setBackgroundColor(Color.RED);	
			btn48.setEnabled(false);
		}
		else if(no==49){
			btn49.setBackgroundColor(Color.RED);	
			btn49.setEnabled(false);
		}
	
		else if(no==50){
			btn50.setBackgroundColor(Color.RED);	
			btn50.setEnabled(false);
		}
		
		else if(no==51){
			btn51.setBackgroundColor(Color.RED);	
			btn51.setEnabled(false);
		}
		else if(no==52){
			btn52.setBackgroundColor(Color.RED);	
			btn52.setEnabled(false);
		}
		else if(no==53){
			btn53.setBackgroundColor(Color.RED);	
			btn53.setEnabled(false);
		}
		else if(no==54){
			btn54.setBackgroundColor(Color.RED);	
			btn54.setEnabled(false);
		}
		
		else if(no==55){
			btn55.setBackgroundColor(Color.RED);	
			btn55.setEnabled(false);
		}
		else if(no==56){
			btn56.setBackgroundColor(Color.RED);	
			btn56.setEnabled(false);
		}
		else if(no==57){
			btn57.setBackgroundColor(Color.RED);	
			btn57.setEnabled(false);
		}
		else if(no==58){
			btn58.setBackgroundColor(Color.RED);	
			btn58.setEnabled(false);
		}
		else if(no==59){
			btn59.setBackgroundColor(Color.RED);	
			btn59.setEnabled(false);
		}

		else if(no==60){
			btn60.setBackgroundColor(Color.RED);	
			btn60.setEnabled(false);
		}



	}
	//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Gantangan.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_lomba", kode_lomba0));
				params.add(new BasicNameValuePair("posisi", posisi));
				params.add(new BasicNameValuePair("kode_anggota", kode_anggota));	
				
				String url=ip+"tiket/tiket_addno.php";
				Log.v("add",url);
				JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
				Log.d("add", json.toString());
				try {
					 sukses = json.getInt(TAG_SUKSES);
					if (sukses == 1) {
						Intent i = getIntent();
						setResult(100, i);
			//finish();
					} else {
						// gagal update data
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			return null;
		}

		protected void onPostExecute(String file_url) {
		pDialog.dismiss();
		
		sukses();
				
			
		}
	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	


	 public void mauLagi(){
	     AlertDialog.Builder ad=new AlertDialog.Builder(Gantangan.this);
	             ad.setTitle("Konfirmasi");
	             ad.setMessage("Apakah benar ingin membeli tiket  Lagi ?");
	             ad.setPositiveButton("YA",new OnClickListener(){
	          @Override
	       public void onClick(DialogInterface dialog, int which) {
	         finish();
	         
	   	  Intent i = new Intent(Gantangan.this,Gantangan.class);
	       i.putExtra("kode_lomba", kode_lomba0);
	       i.putExtra("nama_lomba", nama_lomba);
	       i.putExtra("kelas", kelas);
	       i.putExtra("tanggal", tanggal);
	           startActivity(i); 
	         
	        }});
	             
	             ad.setNegativeButton("TIDAK",new OnClickListener(){
	           public void onClick(DialogInterface arg0, int arg1) {

	        	   finish();
	           }});
	             
	             ad.show();
	    }
	 public void sukses(){
		  new AlertDialog.Builder(this)
		  .setTitle("Sukses Order Tiket "+posisi)
		  .setMessage("Anda Telah terdaftar DiLomba "+nama_lomba+" sudah Anda booking !")
		  .setNeutralButton("OK", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dlg, int sumthin) {
				 	mauLagi();
			   }}).show();
		 } 

	 
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
         String kata=jParser.getName();
       
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
	 
	void setawal(){
		btn1= (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View arg0) {
	        	order("1");
	        }});

		btn2= (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("2");
			}});
		
		btn3= (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("3");
			}});
		
		btn4= (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("4");
			}});
		
		btn5= (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("5");
			}});
		
		btn6= (Button) findViewById(R.id.btn6);
		btn6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("6");
			}});
		
		btn7= (Button) findViewById(R.id.btn7);
		btn7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("7");
			}});
		
		btn8= (Button) findViewById(R.id.btn8);
		btn8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("8");
			}});
		
		btn9= (Button) findViewById(R.id.btn9);
		btn9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("9");
			}});
		
		
		btn10= (Button) findViewById(R.id.btn10);
		btn10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("10");
			}});
		
		btn11= (Button) findViewById(R.id.btn11);
		btn11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("11");
			}});
		
		
		btn12= (Button) findViewById(R.id.btn12);
		btn12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("12");
			}});
		
		
		btn13= (Button) findViewById(R.id.btn13);
		btn13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("13");
			}});
		
		
		btn14= (Button) findViewById(R.id.btn14);
		btn14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("14");
			}});
		
		
		btn15= (Button) findViewById(R.id.btn15);
		btn15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("15");
			}});
		
		
		btn16= (Button) findViewById(R.id.btn16);
		btn16.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("16");
			}});
		
		
		btn17= (Button) findViewById(R.id.btn17);
		btn17.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("17");
			}});
		
		btn18= (Button) findViewById(R.id.btn18);
		btn18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("18");
			}});
		
		btn19= (Button) findViewById(R.id.btn19);
		btn19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("19");
			}});
		
		
		btn20= (Button) findViewById(R.id.btn20);
		btn20.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("20");
			}});
		
		btn21= (Button) findViewById(R.id.btn21);
		btn21.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("21");
			}});
	
		
		btn22= (Button) findViewById(R.id.btn22);
		btn22.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("22");
			}});
		
		btn23= (Button) findViewById(R.id.btn23);
		btn23.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("23");
			}});
		
		btn24= (Button) findViewById(R.id.btn24);
		btn24.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("24");
			}});
		
		btn25= (Button) findViewById(R.id.btn25);
		btn25.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("25");
			}});
		
	
		btn26= (Button) findViewById(R.id.btn26);
		btn26.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("26");
			}});
		
		btn27= (Button) findViewById(R.id.btn27);
		btn27.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("27");
			}});
		
		btn28= (Button) findViewById(R.id.btn28);
		btn28.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("28");
			}});
		
		btn29= (Button) findViewById(R.id.btn29);
		btn29.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("29");
			}});
		
		btn30= (Button) findViewById(R.id.btn30);
		btn30.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("30");
			}});
		
		
		btn31= (Button) findViewById(R.id.btn31);
		btn31.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("31");
			}});
		
		btn32= (Button) findViewById(R.id.btn32);
		btn32.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("32");
			}});
		
		btn33= (Button) findViewById(R.id.btn33);
		btn33.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("33");
			}});
		
		btn34= (Button) findViewById(R.id.btn34);
		btn34.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("34");
			}});
		
		btn35= (Button) findViewById(R.id.btn35);
		btn35.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("35");
			}});
		
		
		btn36= (Button) findViewById(R.id.btn36);
		btn36.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("36");
			}});
		
		btn37= (Button) findViewById(R.id.btn37);
		btn37.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("37");
			}});
		
		btn38= (Button) findViewById(R.id.btn38);
		btn38.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("38");
			}});
		
		btn39= (Button) findViewById(R.id.btn39);
		btn39.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("39");
			}});
		
		btn40= (Button) findViewById(R.id.btn40);
		btn40.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("40");
			}});
		
		
		btn41= (Button) findViewById(R.id.btn41);
		btn41.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("41");
			}});
		
		btn42= (Button) findViewById(R.id.btn42);
		btn42.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("42");
			}});
		
		btn43= (Button) findViewById(R.id.btn43);
		btn43.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("43");
			}});
		
		btn44= (Button) findViewById(R.id.btn44);
		btn44.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("44");
			}});
		
		btn45= (Button) findViewById(R.id.btn45);
		btn45.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("45");
			}});
		
		btn46= (Button) findViewById(R.id.btn46);
		btn46.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("46");
			}});
		
		btn47= (Button) findViewById(R.id.btn47);
		btn47.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("47");
			}});
		
		btn48= (Button) findViewById(R.id.btn48);
		btn48.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("48");
			}});
		
		btn49= (Button) findViewById(R.id.btn49);
		btn49.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("49");
			}});
		
		btn50= (Button) findViewById(R.id.btn50);
		btn50.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("50");
			}});
		
		btn51= (Button) findViewById(R.id.btn51);
		btn51.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("51");
			}});
		
		btn52= (Button) findViewById(R.id.btn52);
		btn52.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("52");
			}});
		
		btn53= (Button) findViewById(R.id.btn53);
		btn53.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("53");
			}});
		
		btn54= (Button) findViewById(R.id.btn54);
		btn54.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("54");
			}});
		
		btn55= (Button) findViewById(R.id.btn55);
		btn55.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("55");
			}});
		
		btn56= (Button) findViewById(R.id.btn56);
		btn56.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("56");
			}});
		
		btn57= (Button) findViewById(R.id.btn57);
		btn57.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("57");
			}});
		
		btn58= (Button) findViewById(R.id.btn58);
		btn58.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("58");
			}});
		
		btn59= (Button) findViewById(R.id.btn59);
		btn59.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("59");
			}});
		
		btn60= (Button) findViewById(R.id.btn60);
		btn60.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				order("60");
			}});
		
	} 
			 
}
