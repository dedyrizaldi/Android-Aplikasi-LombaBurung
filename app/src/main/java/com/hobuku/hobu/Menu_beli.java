package com.hobuku.hobu;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

public class Menu_beli extends Activity {
	ImageView btnlist, btnkeranjang, btnarsip, btnbeli;
	String kode_anggota, nama_anggota;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_beli);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menu_beli.this);
		   Boolean Registered = sharedPref.getBoolean("Registered", false);
		         if (!Registered){
		             finish();
		         }else {
		        	 kode_anggota= sharedPref.getString("kode_anggota", "");
		        	 nama_anggota = sharedPref.getString("nama_anggota", "");
		         }
		
		btnlist=(ImageView)findViewById(R.id.btnlist); 
		btnlist.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_beli.this,Cari_beli.class);
            i.putExtra("kode_anggota", kode_anggota);
            startActivity(i); 
        }});
		
		btnkeranjang=(ImageView)findViewById(R.id.btnkeranjang); 
		btnkeranjang.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_beli.this,List_keranjangbeli.class);
                startActivity(i); 
        }});
		
		btnarsip=(ImageView)findViewById(R.id.btnarsip); 
		btnarsip.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
            Intent i = new Intent(Menu_beli.this,List_beli.class);//ARSIP
                startActivity(i); 
        }});
		
		
	}
	
	
	
	 public void keluar(){
	     new AlertDialog.Builder(this)
	  .setTitle("Menutup Aplikasi")
	  .setMessage("Terimakasih... Anda Telah Menggunakan Aplikasi Ini")
	  .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
	   public void onClick(DialogInterface dlg, int sumthin) {
	   finish();
	   }})
	  .show();
	    }
	    public void keluarYN(){
	     AlertDialog.Builder ad=new AlertDialog.Builder(Menu_beli.this);
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

}
