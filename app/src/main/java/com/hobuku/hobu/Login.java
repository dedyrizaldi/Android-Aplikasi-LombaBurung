package com.hobuku.hobu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends Activity {

    SharedPrefManager sharedPrefManager;
     EditText txtusername,txtpassword;
     String ip="";
     int sukses;

     private ProgressDialog pDialog;
	    JSONParser jsonParser = new JSONParser();

	    private static final String TAG_SUKSES = "sukses";
	    private static final String TAG_record = "record";
	    
        String kode_anggota="",nama_anggota,email;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ip=jsonParser.getIP();

        sharedPrefManager = new SharedPrefManager(this);
        txtusername=(EditText)findViewById(R.id.txtusername);txtusername.setText("");
        txtpassword=(EditText)findViewById(R.id.txtpassword);txtpassword.setText("");

        Button btnLogin= (Button) findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txtusername.getText().toString();
                String pass=txtpassword.getText().toString();


                if(user.length()<1){lengkapi("Username");}
                else if(pass.length()<1){lengkapi("Password");}
                else{
                	new ceklogin().execute();
                         }
                       
                  }

        });

        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(Login.this, Menu_utama.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }


		Button btnDaftar=(Button)findViewById(R.id.btndaftar);
		btnDaftar.setOnClickListener(new View.OnClickListener() {
		public void onClick(View arg0) {
			Intent i = new Intent(Login.this,Daftar.class);
			startActivity(i);
		}});
		
		


    }
    public void gagal(){
        new AlertDialog.Builder(this)
                .setTitle("Gagal Login")
                .setMessage("Silakan Cek Account Anda Kembali")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }})
                .show();
    }
  
    public void sukses(String item,String ex){
        new AlertDialog.Builder(this)
                .setTitle("Sukses "+ex)
                .setMessage(ex+" data "+item+" Berhasil")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
//                        Intent i = new Intent(login.this,MainActivity.class);
//                        i.putExtra("xmlbio", xmlbio);
//                        startActivity(i);
                        // finish();
                    }})
                .show();
    }



    class ceklogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Proses Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
        }
        protected String doInBackground(String... params) {
          
            try {
            	String	username=txtusername.getText().toString().trim();
           		String	password=txtpassword.getText().toString().trim();
           		
                List<NameValuePair> myparams = new ArrayList<NameValuePair>();
                myparams.add(new BasicNameValuePair("username", username));
                myparams.add(new BasicNameValuePair("password", password));

                String url=ip+"anggota/anggota_login.php";
                Log.v("detail",url);
                JSONObject json = jsonParser.makeHttpRequest(url, "GET", myparams);
                Log.d("detail", json.toString());
                sukses = json.getInt(TAG_SUKSES);
                if (sukses == 1) {
                    JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
                    final JSONObject myJSON = myObj.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                            	kode_anggota=myJSON.getString("kode_anggota");
                            	nama_anggota=myJSON.getString("nama_anggota");
                            	email=myJSON.getString("email");
                                    }
                            catch (JSONException e) {e.printStackTrace();}
                        }});
                }
              
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("NewApi")
		protected void onPostExecute(String file_url) {
        	
        	pDialog.dismiss();
	        Log.v("SUKSES",kode_anggota);

        	if(sukses==1){
		        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Login.this);
		        SharedPreferences.Editor editor = sharedPref.edit();
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
		        editor.putBoolean("Registered", true);
		        editor.putString("kode_anggota", kode_anggota);
		        editor.putString("nama_anggota", nama_anggota);
		        editor.putString("email", email);
		        editor.apply();
		        Intent i = new Intent(getApplicationContext(),Menu_utama.class);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
		        finish();
        	}
        	else{
        		gagal("Login");
        	}
        }
    }

   

        public void lengkapi(String item){
	    	new AlertDialog.Builder(this)
			.setTitle("Lengkapi Data")
			.setMessage("Silakan lengkapi data "+item)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
				}})
			.show();
	    }

	

	  public void gagal(String item){
	    	new AlertDialog.Builder(this)
			.setTitle("Gagal Login")
			.setMessage("Login "+item+" ,, Gagal")
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
				}})
			.show();
	    }


	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	finish();
	                return true;
	        }
	    return super.onKeyDown(keyCode, event);
	}


        
	}  
